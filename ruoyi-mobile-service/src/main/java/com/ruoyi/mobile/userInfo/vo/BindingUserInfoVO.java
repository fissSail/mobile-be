package com.ruoyi.mobile.userInfo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author yanfeifan
 * @Package com.ruoyi.mobile.vo
 * @Description 绑定用户信息实体
 * @date 2022/4/13 11:22
 */
@Data
@ApiModel("绑定用户信息实体")
public class BindingUserInfoVO {

    /** id */
    @ApiModelProperty("用户id")
    private String userId;

    /** 名称 */
    @ApiModelProperty("名称")
    private String name;

    /** 手机号码 */
    @ApiModelProperty("手机号码")
    private String phone;

    /** 头像地址 */
    @ApiModelProperty("头像地址")
    private String photoUrl;

    /** 证件号 */
    @ApiModelProperty("证件号")
    private String certificateNo;
}
