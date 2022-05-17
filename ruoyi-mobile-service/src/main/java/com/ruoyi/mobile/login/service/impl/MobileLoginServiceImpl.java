package com.ruoyi.mobile.login.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ruoyi.common.config.SendSmsConfig;
import com.ruoyi.common.config.TokenConfig;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.core.domain.model.MobileLoginUser;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.common.enums.LoginStatusEnum;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.handle.HandleBranchService;
import com.ruoyi.common.handle.HandleUtil;
import com.ruoyi.common.mobile.entity.BasePersonRecordDO;
import com.ruoyi.common.mobile.entity.BasePersonRecordRelevanceWxDO;
import com.ruoyi.common.mobile.login.WxUserInfoVO;
import com.ruoyi.common.utils.AuthUtil;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.mobile.auth.MobilePhoneAuthenticationToken;
import com.ruoyi.mobile.login.bo.LoginBO;
import com.ruoyi.mobile.login.service.MobileLoginService;
import com.ruoyi.mobile.login.vo.LoginReturnVO;
import com.ruoyi.mobile.login.vo.PersonRecordBindingWxVO;
import com.ruoyi.mobile.userInfo.mapper.BasePersonRecordMapper;
import com.ruoyi.mobile.userInfo.mapper.BasePersonRecordRelevanceWxMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.io.UnsupportedEncodingException;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @author yanfeifan
 * @Package com.ruoyi.mobile.service.impl
 * @Description 登录实现
 * @date 2022/3/23 15:07
 */
@Service
@RequiredArgsConstructor
public class MobileLoginServiceImpl implements MobileLoginService {

    private final BasePersonRecordMapper basePersonRecordMapper;

    private final BasePersonRecordRelevanceWxMapper basePersonRecordRelevanceWxMapper;

    private final AuthenticationManager authenticationManager;

    private final SendSmsConfig sendSmsConfig;

    private final RedisCache redisCache;

    private final TokenConfig tokenConfig;

    private final AuthUtil authUtil;

    @Value("${sms.code.timeout}")
    private Integer timeout = 5;

    @Value("${sms.code.sendInterval}")
    private Integer sendInterval = 1;

    // 微信昵称有效期（默认30分钟）与登录令牌有效期一致
    @Value("${token.expireTime}")
    private int expireTime;

    protected static final long MILLIS_SECOND = 1000;

    protected static final long MILLIS_MINUTE = 60 * MILLIS_SECOND;

    /**
     * 微信openid
     */
    private static final String OPENID_FLAG = "openid";

    /**
     * access_token
     */
    private static final String ACCESS_TOKEN = "access_token";

    /**
     * 微信授权昵称
     */
    private static final String NICK_NAME = "nickname";

    /**
     * 微信授权用户头像
     */
    private static final String HEADIMG_URL = "headimgurl";

    /**
     * 登录发送短信
     *
     * @param mobilePhone
     * @return
     */
    @Override
    public String verificationCode(String mobilePhone) {
        //取出redis中存入的上次发送短信验证码的毫秒数 + 接口防刷
        String redisCodeMillis = redisCache.getCacheObject(Constants.SMS_CODE_MILLIS_PREFIX + mobilePhone);
        if (StringUtils.hasText(redisCodeMillis)) {
            long l = Long.parseLong(redisCodeMillis);
            //当前毫秒数-上次发送诗句毫秒数<60秒，不允许再次发送
            HandleUtil.serviceExceptionAssert(System.currentTimeMillis() - l < sendInterval * MILLIS_MINUTE, LoginStatusEnum.CODE_ACCESS_NUMBER.getMsg(), LoginStatusEnum.CODE_ACCESS_NUMBER.getCode());
        }
        //随机验证码
        String code = String.format("%04d", new Random().nextInt(9999));
        String sandSmsMsg;
        try {
            //发送短信验证码
            sandSmsMsg = sendSmsConfig.sandSms(mobilePhone, code);

            //验证码校验 存入redis
            redisCache.setCacheObject(Constants.SMS_CODE_PREFIX + mobilePhone, code, timeout, TimeUnit.MINUTES);

            //redis存入短信发送间隔
            redisCache.setCacheObject(Constants.SMS_CODE_MILLIS_PREFIX + mobilePhone, System.currentTimeMillis() + "", timeout, TimeUnit.MINUTES);
        } catch (Exception e) {
            e.printStackTrace();
            //判断捕获的异常是手动抛出的异常
            if(e instanceof ServiceException){
                ServiceException serviceException = (ServiceException) e;
                throw new ServiceException(serviceException.getMessage(), serviceException.getCode());
            }
            throw new ServiceException(LoginStatusEnum.SAND_SMS_ERR.getMsg(), LoginStatusEnum.SAND_SMS_ERR.getCode());
        }
        return sandSmsMsg;
    }

    @Override
    public LoginReturnVO login(LoginBO loginBO) {
        //校验验证码
        this.validateCaptcha(loginBO);
        // 用户验证
        Authentication authentication = authenticationManager.authenticate(new MobilePhoneAuthenticationToken(loginBO.getMobilePhone()));
        //转换成移动端登录用户
        MobileLoginUser mobileLoginUser = (MobileLoginUser) authentication.getPrincipal();
        //返回登录信息
        return Optional.ofNullable(mobileLoginUser).map(data -> {
            if (StringUtils.hasText(data.getUserId())) {
                //注册的手机登录返回完善的登录信息
                return this.getLoginReturnPerfectVo(data);
            }
            //未注册的手机登录返回不完善的登录信息
            return this.getLoginReturnNotPerfectVo(data);
        }).get();
    }

    @Override
    public LoginReturnVO wxLogin(String code) {
        //根据code获取 access_token信息
        JSONObject jsonObject = authUtil.getAccessToken(code);
        //授权登录返回的结果没有openid抛出异常
        HandleUtil.serviceExceptionAssert(!jsonObject.containsKey(OPENID_FLAG), LoginStatusEnum.WECHAT_AUTHORIZATION_ERR.getMsg(), LoginStatusEnum.WECHAT_AUTHORIZATION_ERR.getCode());
        //用户唯一标识
        String openId = jsonObject.getString(OPENID_FLAG);
        //access_token
        String accessToken = jsonObject.getString(ACCESS_TOKEN);
        //调用微信接口获取微信用户昵称并存入缓存
        this.getUserNickNameAndSaveRedisCache(openId, accessToken);
        //根据openid查询绑定的用户id
        BasePersonRecordRelevanceWxDO basePersonRecordRelevanceWxDO = basePersonRecordRelevanceWxMapper
                .selectOne(new LambdaQueryWrapper<BasePersonRecordRelevanceWxDO>()
                        .eq(BasePersonRecordRelevanceWxDO::getOpenId, openId));
        //判断是否绑定了用户id
        return Optional.ofNullable(basePersonRecordRelevanceWxDO)
                //绑定了用户信息，返回完善的登录信息
                .map(wxDO -> this.getLoginReturnVoByPersonRecord(wxDO))
                //未绑定用户信息，根据openid返回不完善的登录信息
                .orElseGet(() -> this.getLoginReturnNotPerfectVoByOpenId(openId));
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public LoginReturnVO bindingWx(PersonRecordBindingWxVO personRecordBindingWxVO) {
        BasePersonRecordRelevanceWxDO personRecordRelevanceWxDO = new BasePersonRecordRelevanceWxDO();
        BeanUtils.copyProperties(personRecordBindingWxVO, personRecordRelevanceWxDO);
        personRecordRelevanceWxDO.setCreateTime(DateUtils.getNowDate());
        // 判断微信绑定的用户是否有openid，没有则不进行绑定，直接返回更新后的登录信息
        if (StringUtils.hasText(personRecordRelevanceWxDO.getOpenId())) {
            HandleUtil.serviceExceptionAssert(basePersonRecordRelevanceWxMapper.insert(personRecordRelevanceWxDO) <= 0, LoginStatusEnum.BINDING_ERR.getMsg(), LoginStatusEnum.BINDING_ERR.getCode());
        }
        //删除redis中缓存的用户登录信息
        this.deleteRedisMobileLoginUserCache();
        //绑定成功后更新用户信息与token
        return this.getLoginReturnVoByPersonRecord(personRecordRelevanceWxDO);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public LoginReturnVO unboundWx(String openId, String personRecordId) {
        //生成处理分支对象
        HandleBranchService<LoginReturnVO> handleBranchService = HandleUtil.handleBranch(StringUtils.hasText(openId));

        return handleBranchService.handleBranch(() ->
                        //微信登录用户解绑
                        this.getUnboundWxLoginReturnVo(openId, personRecordId, true)
                , () ->
                        //手机号登录解绑
                        this.getUnboundWxLoginReturnVo(openId, personRecordId, false)
        );
    }

    /**
     * 微信/手机号登录解绑
     *
     * @param openId         微信用户标识
     * @param personRecordId 用户id
     * @param isWx           是否微信登录，true微信登录 false手机登录
     * @return
     */
    private LoginReturnVO getUnboundWxLoginReturnVo(String openId, String personRecordId, boolean isWx) {
        int i = basePersonRecordRelevanceWxMapper
                .delete(new LambdaQueryWrapper<BasePersonRecordRelevanceWxDO>()
                        .eq(isWx, BasePersonRecordRelevanceWxDO::getOpenId, openId)
                        .eq(BasePersonRecordRelevanceWxDO::getPersonRecordId, personRecordId));
        //有openid和用户id，还是解绑失败，则是数据问题，提示失败
        HandleUtil.serviceExceptionAssert(i <= 0, LoginStatusEnum.UNBOUND_ERR.getMsg(), LoginStatusEnum.UNBOUND_ERR.getCode());
        //删除redis中缓存的用户登录信息
        this.deleteRedisMobileLoginUserCache();
        if (isWx) {
            //微信登录用户解绑
            //根据openid返回不完善的登录信息
            return this.getLoginReturnNotPerfectVoByOpenId(openId);
        } else {
            //手机号登录解绑
            BasePersonRecordRelevanceWxDO personRecordRelevanceWxDO = new BasePersonRecordRelevanceWxDO();
            personRecordRelevanceWxDO.setOpenId(openId);
            personRecordRelevanceWxDO.setPersonRecordId(personRecordId);
            //解绑成功后更新用户信息与token
            return this.getLoginReturnVoByPersonRecord(personRecordRelevanceWxDO);
        }
    }

    /**
     * 判断用户信息返回登录实体
     *
     * @param wxDO
     * @return
     */
    private LoginReturnVO getLoginReturnVoByPersonRecord(BasePersonRecordRelevanceWxDO wxDO) {
        //根据用户id查询用户信息
        BasePersonRecordDO basePersonRecordDO = basePersonRecordMapper.selectById(wxDO.getPersonRecordId());
        return Optional.ofNullable(basePersonRecordDO).map(recordDO -> {
            //已经授权且已完善用户信息，则生成token
            //设置移动端登录用户身份主体用于生成token
            MobileLoginUser mobileLoginUser = this.getMobileLoginUser(recordDO, wxDO.getOpenId());
            //返回完善用户登录信息
            return this.getLoginReturnPerfectVo(mobileLoginUser);
        }).orElseGet(() -> {
            //如果用户档案表id不为空还未查询出信息，则抛出异常
            HandleUtil.serviceExceptionAssert(StringUtils.hasText(wxDO.getPersonRecordId()), LoginStatusEnum.USER_INFO_ERR.getMsg(), LoginStatusEnum.USER_INFO_ERR.getCode());
            //根据openid返回不完善的登录信息
            return this.getLoginReturnNotPerfectVoByOpenId(wxDO.getOpenId());
        });
    }

    /**
     * 设置移动端登录用户身份主体用于生成token
     *
     * @param recordDO
     * @return
     */
    private MobileLoginUser getMobileLoginUser(BasePersonRecordDO recordDO, String openId) {
        MobileLoginUser mobileLoginUser = new MobileLoginUser();
        mobileLoginUser.setUserId(recordDO.getId());
        mobileLoginUser.setPersonName(recordDO.getName());
        mobileLoginUser.setBasePersonRecordDO(recordDO);
        mobileLoginUser.setOpenId(openId);
        return mobileLoginUser;
    }

    /**
     * 微信授权登录未绑定用户，则根据openid返回不完善的登录信息
     *
     * @param openid
     * @return
     */
    private LoginReturnVO getLoginReturnNotPerfectVoByOpenId(String openid) {
        LoginReturnVO vo = new LoginReturnVO();
        //设置移动端登录用户身份用于生成token
        MobileLoginUser mobileLoginUser = new MobileLoginUser();
        //设置信息未完善用户
        mobileLoginUser.setIsPerfectInfo(false);
        mobileLoginUser.setOpenId(openid);
        vo.setOpenId(openid);
        vo.setWxUserInfoVO(redisCache.getCacheObject(Constants.NICK_NAME_KEY + openid));
        //设置信息未完善用户
        vo.setIsPerfectInfo(mobileLoginUser.getIsPerfectInfo());
        // 生成token
        vo.setToken(tokenConfig.createToken(mobileLoginUser));
        return vo;
    }

    /**
     * 未注册的手机登录返回不完善的登录信息
     *
     * @return
     */
    private LoginReturnVO getLoginReturnNotPerfectVo(MobileLoginUser mobileLoginUser) {
        LoginReturnVO vo = new LoginReturnVO();
        mobileLoginUser.setIsPerfectInfo(false);
        // 标识信息未完善用户
        vo.setIsPerfectInfo(mobileLoginUser.getIsPerfectInfo());
        vo.setPhone(mobileLoginUser.getBasePersonRecordDO().getPhone());
        // 生成token
        vo.setToken(tokenConfig.createToken(mobileLoginUser));
        return vo;
    }

    /**
     * 手机号登录查询到用户信息或用户信息完善后，返回完善的登录信息
     *
     * @param mobileLoginUser
     * @return
     */
    private LoginReturnVO getLoginReturnPerfectVo(MobileLoginUser mobileLoginUser) {
        LoginReturnVO vo = new LoginReturnVO();
        vo.setUserId(mobileLoginUser.getUserId());
        vo.setUserName(mobileLoginUser.getPersonName());
        vo.setPhone(mobileLoginUser.getBasePersonRecordDO().getPhone());
        vo.setOpenId(mobileLoginUser.getOpenId());
        // 生成token
        vo.setToken(tokenConfig.createToken(mobileLoginUser));
        return vo;
    }

    /**
     * 删除redis中缓存的用户登录信息
     */
    private void deleteRedisMobileLoginUserCache() {
        MobileLoginUser mobileLoginUser = SecurityUtils.getMobileLoginUser();
        redisCache.deleteObject(Constants.MOBILE_LOGIN_TOKEN_KEY + mobileLoginUser.getToken());
    }

    /**
     * 获取微信授权后用户昵称并存入缓存
     *
     * @param openId
     * @param accessToken
     */
    private void getUserNickNameAndSaveRedisCache(String openId, String accessToken) {
        JSONObject userInfo = authUtil.getUserInfo(accessToken, openId);
        //查询微信用户信息没有用户昵称直接抛出异常
        HandleUtil.serviceExceptionAssert(!userInfo.containsKey(NICK_NAME), LoginStatusEnum.WECHAT_AUTHORIZATION_INFO_ERR.getMsg(), LoginStatusEnum.WECHAT_AUTHORIZATION_INFO_ERR.getCode());
        //用户昵称
        String nickname = userInfo.getString(NICK_NAME);
        //用户头像
        String headimgurl = userInfo.getString(HEADIMG_URL);
        try {
            // 昵称乱码
            nickname = new String(nickname.getBytes("ISO-8859-1"), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //将用户昵称和头像存入缓存
        WxUserInfoVO wxUserInfoVO = new WxUserInfoVO();
        wxUserInfoVO.setNickname(nickname);
        wxUserInfoVO.setHeadimgurl(headimgurl);
        //redis缓存
        redisCache.setCacheObject(Constants.NICK_NAME_KEY + openId, wxUserInfoVO, expireTime, TimeUnit.MINUTES);
    }

    /**
     * 校验验证码
     *
     * @param loginBO
     * @return 结果
     */
    public void validateCaptcha(LoginBO loginBO) {
        //缓存key
        String verifyKey = Constants.SMS_CODE_PREFIX + loginBO.getMobilePhone();
        //获取redis缓存中的验证码
        String captcha = redisCache.getCacheObject(verifyKey);
        //验证码失效抛出异常
        HandleUtil.serviceExceptionAssert(!StringUtils.hasText(captcha), LoginStatusEnum.JCAPTCHA_EXPIRE_ERR.getMsg(), LoginStatusEnum.JCAPTCHA_EXPIRE_ERR.getCode());
        //验证码错误抛出异常
        HandleUtil.serviceExceptionAssert(!loginBO.getCode().equalsIgnoreCase(captcha), LoginStatusEnum.JCAPTCHA_ERR.getMsg(), LoginStatusEnum.JCAPTCHA_ERR.getCode());
        //验证成功删除redis中的验证码
        redisCache.deleteObject(verifyKey);
    }
}
