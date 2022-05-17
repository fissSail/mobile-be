package com.ruoyi.mobile.userInfo.service;

import com.ruoyi.common.mobile.entity.BasePersonRecordDO;
import com.ruoyi.mobile.userInfo.vo.BasePersonRecordAndOpenIdVO;
import com.ruoyi.mobile.userInfo.vo.BasePersonRecordVO;
import com.ruoyi.mobile.vo.SysDictDataVO;

import java.util.List;

public interface MobileUserInfoService {

    /**
     * 获取用户信息
     *
     * @param userId
     * @return
     */
    BasePersonRecordVO userInfo(String userId);

    /**
     * 更新或添加用户
     *
     * @param basePersonRecordVO
     */
    BasePersonRecordAndOpenIdVO addAndUpdate(BasePersonRecordVO basePersonRecordVO);

    /**
     * 获取字典类型
     *
     * @param type
     * @return
     */
    List<SysDictDataVO> dictionary(String type);

    /**
     * 根据手机号或证件号查询用户信息
     *
     * @param phoneOrCertificateNo
     * @return
     */
    Object getUserInfoByPhoneOrCertificateNo(String phoneOrCertificateNo);
}
