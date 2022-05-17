package com.ruoyi.moblie.medicalPackage.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

@ApiModel("套餐添加或更新")
@Data
public class BaseSetMealAddAndUpdateDTO {

    @ApiModelProperty("id")
    private String id;
    @ApiModelProperty("套餐标题")
    private String title;
    @ApiModelProperty("体检套餐id")
    private String baseExaminePackageId;
    @ApiModelProperty("标签id")
    private List<String> tagIdList;
    @ApiModelProperty("套餐缩略图,由,号隔开")
    private String image;
    @ApiModelProperty("销量（redis定更新时器）")
    private Integer saleNumber;
    @ApiModelProperty("详情轮播图")
    private String banner;
    @ApiModelProperty("套餐详情")
    private String content;
}
