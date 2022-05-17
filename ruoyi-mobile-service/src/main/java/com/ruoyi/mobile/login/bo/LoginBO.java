package com.ruoyi.mobile.login.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * @author yanfeifan
 * @Package com.ruoyi.mobile.bo
 * @Description
 * @date 2022/4/7 17:07
 */
@Data
public class LoginBO {

    /**
     * 手机号码
     */
    @ApiModelProperty(value = "手机号码")
    @NotBlank(message = "手机号码不能为空")
    @Pattern(regexp = "^[1][3,4,5,6,7,8,9][0-9]{9}$", message = "手机号格式有误")
    String mobilePhone;

    /**
     * 短信验证码
     */
    @ApiModelProperty(value = "短信验证码")
    @NotBlank(message = "手机号码不能为空")
    String code;
}
