package com.ruoyi.mobile.userInfo.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.Date;
@Data
public class BasePersonRecordVO {

    /** id */
    private String id;

    /** 名称 */
    @NotBlank(message = "姓名不能为空")
    private String name;

    /** 性别 */

    private String sex;

    /** 年龄 */
    private Integer age;

    /** 婚姻状态 */
    private String marryType;


    /** 证件类型 */
    @NotBlank(message = "证件类型不能为空")
    private String certificateType;

    /** 证件号 */
    @NotBlank(message = "证件号不能为空")
    private String certificateNo;

    /** 手机号码 */
    @NotBlank(message = "手机号不能为空")
    @Pattern(regexp = "^(13[0-9]|14[01456879]|15[0-35-9]|16[2567]|17[0-8]|18[0-9]|19[0-35-9])\\d{8}$" ,message = "手机号格式错误")
    private String phone;

    /** 出生日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date birthday;

    /** 通信地址 */
    private String address;

    /** 民族 */
    private String nation;

    /** 头像地址 */
    private String photoUrl;

    /**
     * 微信授权后用户标识
     */
    @ApiModelProperty("微信授权后用户标识")
    private String openId;






}
