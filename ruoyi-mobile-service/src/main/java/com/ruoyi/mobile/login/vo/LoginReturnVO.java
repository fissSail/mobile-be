package com.ruoyi.mobile.login.vo;

import com.ruoyi.common.mobile.login.WxUserInfoVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author yanfeifan
 * @Package com.ruoyi.mobile.vo
 * @Description
 * @date 2022/4/7 16:35
 */
@Data
public class LoginReturnVO {

    /**
     * userId
     */
    @ApiModelProperty("用户id")
    private String userId;

    /**
     * 用户名称
     */
    @ApiModelProperty("用户名称")
    private String userName;

    /**
     * 手机号
     */
    @ApiModelProperty("手机号登录用户返回手机号")
    private String phone;

    /**
     * 令牌
     */
    @ApiModelProperty("token")
    private String token;

    /**
     * 微信授权后用户标识
     */
    @ApiModelProperty("微信授权后用户标识")
    private String openId;

    /**
     * 是否为信息完善用户
     */
    @ApiModelProperty("是否为信息完善用户，true信息完善的用户，false信息不完善的用户，无法进行预约等操作")
    private Boolean isPerfectInfo = Boolean.TRUE;

    /**
     * 微信授权登录用户信息
     */
    @ApiModelProperty("微信授权登录用户信息")
    private WxUserInfoVO wxUserInfoVO;
}
