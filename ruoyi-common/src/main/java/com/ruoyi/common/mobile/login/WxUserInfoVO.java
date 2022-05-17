package com.ruoyi.common.mobile.login;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Description：微信授权登录获取微信用户信息
 * @Author：yff
 * @Date：2022/5/6 14:22
 */
@Data
public class WxUserInfoVO {

    /**
     * 用户昵称
     */
    @ApiModelProperty("用户昵称")
    private String nickname;

    /**
     * 用户头像
     */
    @ApiModelProperty("用户头像")
    private String headimgurl;
}
