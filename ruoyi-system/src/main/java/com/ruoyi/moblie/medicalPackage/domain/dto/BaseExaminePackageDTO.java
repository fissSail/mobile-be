package com.ruoyi.moblie.medicalPackage.domain.dto;


import com.ruoyi.common.mobile.common.Page;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 体检套餐对象 base_examine_package
 *
 * @author ruoyi
 * @date 2022-01-19
 */
@Data
@ApiModel("pc端套餐")
public class BaseExaminePackageDTO extends Page {
    /**
     * 套餐名称
     */
    @ApiModelProperty("套餐名称")
    private String name;
    @ApiModelProperty("体检类型")
    private String examineType;




}
