package com.ruoyi.mobile.login.service;

import com.ruoyi.mobile.login.bo.LoginBO;
import com.ruoyi.mobile.login.vo.LoginReturnVO;
import com.ruoyi.mobile.login.vo.PersonRecordBindingWxVO;

public interface MobileLoginService {
    /**
     * 登录发送短信
     *
     * @param mobilePhone
     * @return
     */
    String verificationCode(String mobilePhone);

    /**
     * 手机号登录
     *
     * @param loginBO
     * @return
     */
    LoginReturnVO login(LoginBO loginBO);

    /**
     * 微信登录
     *
     * @param code
     * @return
     */
    LoginReturnVO wxLogin(String code);

    /**
     * 移动端用户id绑定微信授权后的用户标识apenid
     *
     * @param personRecordBindingWxVO
     */
    LoginReturnVO bindingWx(PersonRecordBindingWxVO personRecordBindingWxVO);

    /**
     * 解绑微信
     * @param openId
     * @param personRecordId
     * @return
     */
    LoginReturnVO unboundWx(String openId, String personRecordId);
}

