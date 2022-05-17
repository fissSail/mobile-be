package com.ruoyi.common.mobile.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@ApiModel("标签表")
@TableName("base_tag")
@Data
public class BaseTagDO {
    @ApiModelProperty("id")
    private String id;
    @ApiModelProperty(" '适用性别 男：1 女：2  通用：3'")
    @NotNull(message = "适用性别不能为空")
    private Integer applySex;
    @ApiModelProperty("适用最小年龄")
    @NotNull(message = "适用最小年龄不能为空")
    private Integer minApplyAge;
    @ApiModelProperty("适用最大年龄")
    @NotNull(message = "适用最大年龄不能为空")
    private Integer maxApplyAge;
    @ApiModelProperty("'内容'")
    @NotBlank(message = "内容不能为空")
    private String content;


}
