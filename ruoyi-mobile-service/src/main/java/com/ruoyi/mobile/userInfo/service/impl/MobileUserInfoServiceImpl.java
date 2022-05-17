package com.ruoyi.mobile.userInfo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.mobile.entity.BasePersonRecordDO;
import com.ruoyi.common.mobile.enums.CertificateTypeEnum;
import com.ruoyi.common.mobile.enums.CommonlyUsedEnum;
import com.ruoyi.common.mobile.enums.IDCardSexEnum;
import com.ruoyi.common.utils.*;
import com.ruoyi.common.utils.uuid.IdUtils;
import com.ruoyi.mobile.login.vo.LoginReturnVO;
import com.ruoyi.mobile.login.vo.PersonRecordBindingWxVO;
import com.ruoyi.mobile.userInfo.mapper.BasePersonRecordMapper;
import com.ruoyi.mobile.generateCode.service.IBaseCodingService;
import com.ruoyi.mobile.login.service.MobileLoginService;
import com.ruoyi.mobile.userInfo.service.MobileUserInfoService;
import com.ruoyi.mobile.userInfo.vo.BasePersonRecordAndOpenIdVO;
import com.ruoyi.mobile.userInfo.vo.BasePersonRecordVO;
import com.ruoyi.mobile.userInfo.vo.WXUserInfoVO;
import com.ruoyi.mobile.vo.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MobileUserInfoServiceImpl implements MobileUserInfoService {


    private final BasePersonRecordMapper basePersonRecordMapper;

    private final IBaseCodingService iBaseCodingService;
    private final MobileLoginService mobileLoginService;

    /**
     * 获取用户信息
     *
     * @param userId
     * @return
     */
    @Override
    public BasePersonRecordVO userInfo(String userId) {
        BasePersonRecordDO basePersonRecordDO = basePersonRecordMapper.selectOne(new QueryWrapper<BasePersonRecordDO>()
                .eq("id", userId).eq("del_flag", CommonlyUsedEnum.NOT_DELETE.val()));
        BasePersonRecordVO basePersonRecordVO = new BasePersonRecordVO();
        if (basePersonRecordDO == null) {
            return basePersonRecordVO;
        }
        BeanUtils.copyProperties(basePersonRecordDO, basePersonRecordVO);
        return basePersonRecordVO;
    }

    /**
     * 更新或添加用户
     *
     * @param basePersonRecordVO
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public BasePersonRecordAndOpenIdVO addAndUpdate(BasePersonRecordVO basePersonRecordVO) {
        //身份证用户

        BasePersonRecordDO basePersonRecordDO = new BasePersonRecordDO();
        LoginReturnVO loginReturnVo = new LoginReturnVO();
        if (CertificateTypeEnum.ID_CARD.getVal().equals(basePersonRecordVO.getCertificateType())) {
            if (!ValidateUtil.validateCardNo(basePersonRecordVO.getCertificateNo(), ValidateUtil.CARD_TYPE_IDCARD)) {
                throw new RuntimeException("身份证号输入有误!");
            }
            extracteInfo(basePersonRecordVO, basePersonRecordDO);
            if (StringUtils.isNotEmpty(basePersonRecordVO.getId())) {
                loginReturnVo = updatePerson(basePersonRecordVO, basePersonRecordDO);
            } else {
                loginReturnVo = addPerson(basePersonRecordVO, basePersonRecordDO, loginReturnVo);
            }
        } else {
            checkInfo(basePersonRecordVO);
            //校验信息不能为空
            if (StringUtils.isNotEmpty(basePersonRecordVO.getId())) {
                loginReturnVo =  updatePerson(basePersonRecordVO, basePersonRecordDO);
            } else {
                loginReturnVo = addPerson(basePersonRecordVO, basePersonRecordDO, loginReturnVo);
            }
        }
        return BasePersonRecordAndOpenIdVO.builder()
                .loginReturnVo(loginReturnVo)
                .basePersonRecordDO(basePersonRecordDO)
                .build();
    }

    /**
     * 添加人员
     *
     * @param basePersonRecordVO 前端传递的值
     * @param basePersonRecordDO 人员记录
     */
    private LoginReturnVO addPerson(BasePersonRecordVO basePersonRecordVO, BasePersonRecordDO basePersonRecordDO, LoginReturnVO loginReturnVo) {
        BeanUtils.copyProperties(basePersonRecordVO, basePersonRecordDO);
        long maxId = iBaseCodingService.getLastOrderNumByCodeType("2");
        basePersonRecordDO.setCode(CodeGenerateUtil.genderationCode("DA", 12, maxId, false));
        basePersonRecordDO.setId(IdUtils.simpleUUID());
        basePersonRecordDO.setCreateBy(SecurityUtils.getMobileUsername());
        basePersonRecordDO.setCreateTime(DateUtils.getNowDate());
        if (basePersonRecordMapper.insert(basePersonRecordDO) != 1) {
            throw new RuntimeException("保存失败！");
        }
        return getLoginReturnVo(basePersonRecordVO, basePersonRecordDO);
    }

    /**
     * 返回登录信息
     * @param basePersonRecordVO
     * @param basePersonRecordDO
     * @return
     */
    private LoginReturnVO getLoginReturnVo(BasePersonRecordVO basePersonRecordVO, BasePersonRecordDO basePersonRecordDO) {
        PersonRecordBindingWxVO vo = new PersonRecordBindingWxVO();
        vo.setOpenId(basePersonRecordVO.getOpenId());
        vo.setPersonRecordId(basePersonRecordDO.getId());
        return  mobileLoginService.bindingWx(vo);
    }

    /**
     * 更新用户信息
     *
     * @param basePersonRecordVO 前端传递的值
     * @param basePersonRecordDO 人员记录
     */
    private LoginReturnVO updatePerson(BasePersonRecordVO basePersonRecordVO, BasePersonRecordDO basePersonRecordDO) {
        BeanUtils.copyProperties(basePersonRecordVO, basePersonRecordDO);
        if (basePersonRecordMapper.updateById(basePersonRecordDO) != 1) {
            throw new RuntimeException("更新失败！");
        }
        return getLoginReturnVo(basePersonRecordVO, basePersonRecordDO);
    }

    /**
     * 不是身份证类型就要校验信息是否传
     *
     * @param basePersonRecordVO
     */
    private void checkInfo(BasePersonRecordVO basePersonRecordVO) {
        if (StringUtils.isEmpty(basePersonRecordVO.getCertificateNo())) {
            throw new RuntimeException("证件号不能为空");
        }
        if (null == basePersonRecordVO.getAge()) {
            throw new RuntimeException("年龄不能为空");
        }
        if (null == basePersonRecordVO.getBirthday()) {
            throw new RuntimeException("出生日期不能为空");
        }
        if (StringUtils.isEmpty(basePersonRecordVO.getSex())) {
            throw new RuntimeException("证件号不能为空");
        }
    }

    /**
     * 根据身份证号设置人员信息
     *
     * @param basePersonRecordVO
     * @param basePersonRecordDO
     */
    private void extracteInfo(BasePersonRecordVO basePersonRecordVO, BasePersonRecordDO basePersonRecordDO) {
        String certificateNo = basePersonRecordVO.getCertificateNo();
        basePersonRecordVO.setBirthday(DateUtils.parseDate(IDCardUtil.getYearByIdCard(certificateNo) + "-" + IDCardUtil.getMonthByIdCard(certificateNo) + "-" + IDCardUtil.getDateByIdCard(certificateNo)));
        basePersonRecordVO.setAge(IDCardUtil.getAgeByIdCard(certificateNo));
        String sex = IDCardUtil.getGenderByIdCard(certificateNo);
        if (IDCardSexEnum.M.getVal().equals(sex)) {
            basePersonRecordVO.setSex(IDCardSexEnum.MAN.getVal());
        } else if ("F".equals(sex)) {
            basePersonRecordVO.setSex(IDCardSexEnum.FEMALE.getVal());
        } else {
            basePersonRecordVO.setSex(IDCardSexEnum.UNKNOWN.getVal());
        }
    }

    /**
     * 获取字典类型
     *
     * @param type
     * @return
     */
    @Override
    public List<SysDictDataVO> dictionary(String type) {
        return basePersonRecordMapper.dictionary(type);
    }

    @Override
    public WXUserInfoVO getUserInfoByPhoneOrCertificateNo(String phoneOrCertificateNo) {
        //根据手机号或者证件号查询用户信息
        BasePersonRecordDO basePersonRecordDO = null;
        try {
            basePersonRecordDO = basePersonRecordMapper
                    .selectOne(new LambdaQueryWrapper<BasePersonRecordDO>()
                            .eq(BasePersonRecordDO::getPhone, phoneOrCertificateNo)
                            .or(data -> data.eq(BasePersonRecordDO::getCertificateNo, phoneOrCertificateNo)));
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException("查询用户失败，请重新输入");
        }

        return Optional.ofNullable(basePersonRecordDO).map(data -> {
            WXUserInfoVO vo = new WXUserInfoVO();
            BeanUtils.copyProperties(data, vo);
            return vo;
        }).orElse(new WXUserInfoVO());
    }
}
