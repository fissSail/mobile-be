package com.ruoyi.mobile.userInfo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @date: 2022/4/8  9:52
 * @author: 1623394432@qq.com
 */
@Data
@ApiModel("人员信息")
public class PersonInfoVo {
    @ApiModelProperty("体检编号")
    private String examineCode;
    @ApiModelProperty("人员类别")
    private String personType;
    /** 名称 */

    @ApiModelProperty("名字")
    private String name;
    @ApiModelProperty("公司名字")
    private String enterprise;
    @ApiModelProperty("体检日期")
    private Date examineDate;
}
