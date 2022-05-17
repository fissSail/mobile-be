package com.ruoyi.mobile.login.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ruoyi.common.core.domain.model.MobileLoginUser;
import com.ruoyi.common.enums.LoginStatusEnum;
import com.ruoyi.common.handle.HandleUtil;
import com.ruoyi.common.mobile.entity.BasePersonRecordDO;
import com.ruoyi.mobile.userInfo.mapper.BasePersonRecordMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;

/**
 * @author yanfeifan
 * @Package com.ruoyi.mobile.service.impl
 * @Description 移动端手机号登录验证
 * @date 2022/4/8 10:01
 */
@Service
public class MobilePhoneLoginServiceImpl implements UserDetailsService {

    @Autowired
    private BasePersonRecordMapper basePersonRecordMapper;

    @Override
    public UserDetails loadUserByUsername(String phone) throws UsernameNotFoundException {
        //根据手机号查询用户信息
        List<BasePersonRecordDO> basePersonRecordDOList = basePersonRecordMapper
                .selectList(new LambdaQueryWrapper<BasePersonRecordDO>()
                        .eq(BasePersonRecordDO::getPhone, phone));

        if (CollectionUtils.isEmpty(basePersonRecordDOList)) {
            //第一次登录系统
            //throw new ServiceException("手机号不存在，请重新输入", HttpStatus.HTTP_INTERNAL_ERROR);
            BasePersonRecordDO basePersonRecordDO = new BasePersonRecordDO();
            basePersonRecordDO.setPhone(phone);
            return this.getMobileLoginUser(basePersonRecordDO);
        }

        //手机号绑定的用户大于1抛出异常
        HandleUtil.serviceExceptionAssert(basePersonRecordDOList.size() > 1, LoginStatusEnum.PHONE_MULTI.getMsg(), LoginStatusEnum.PHONE_MULTI.getCode());

        return this.getMobileLoginUser(basePersonRecordDOList.get(0));
    }

    /**
     * 返回移动端登录用户身份信息
     *
     * @param basePersonRecordDO
     * @return
     */
    private MobileLoginUser getMobileLoginUser(BasePersonRecordDO basePersonRecordDO) {
        return Optional
                .ofNullable(basePersonRecordDO)
                .map(data -> {
                    MobileLoginUser mobileLoginUser = new MobileLoginUser();
                    mobileLoginUser.setUserId(data.getId());
                    mobileLoginUser.setPersonName(data.getName());
                    mobileLoginUser.setBasePersonRecordDO(data);
                    return mobileLoginUser;
                }).get();
    }

}
