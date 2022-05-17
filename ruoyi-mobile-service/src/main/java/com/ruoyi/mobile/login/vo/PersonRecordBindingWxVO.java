package com.ruoyi.mobile.login.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author yanfeifan
 * @Package com.ruoyi.mobile.vo
 * @Description 用户id与微信用户标识绑定vo
 * @date 2022/4/12 16:25
 */
@Data
@ApiModel("用户id与微信用户标识绑定")
public class PersonRecordBindingWxVO {

    /**
     * 微信授权后用户标识
     */
    @ApiModelProperty("微信授权后用户标识")
    @NotBlank(message="微信用户标识不能为空")
    private String openId;

    /**
     * 人员档案表id
     */
    @ApiModelProperty("用户id")
    @NotBlank(message="用户id不能为空")
    private String personRecordId;
}
