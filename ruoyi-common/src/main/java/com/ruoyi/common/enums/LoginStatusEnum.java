package com.ruoyi.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Description：登录方法异常处理返回提示枚举
 * @Author：yff
 * @Date：2022/4/28 10:34
 */
@Getter
@AllArgsConstructor
public enum LoginStatusEnum {
    PHONE_MULTI("手机号匹配多个用户，请联系管理员",1001),
    CODE_ACCESS_NUMBER("验证码获取频率太高，请稍后再试",1002),
    WECHAT_AUTHORIZATION_ERR("微信授权失败",1003),
    WECHAT_AUTHORIZATION_INFO_ERR("获取微信授权用户信息失败",1004),
    BINDING_ERR("绑定失败，openid或用户id错误",1005),
    UNBOUND_ERR("解绑失败，未进行微信绑定",1006),
    USER_INFO_ERR("用户信息异常，请联系管理员",1007),
    SAND_SMS_ERR("短信发送异常，请联系管理员",1008),
    JCAPTCHA_EXPIRE_ERR("验证码已失效",1009),
    JCAPTCHA_ERR("验证码错误",1010);

    private String msg;

    private int code;
}
