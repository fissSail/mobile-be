package com.ruoyi.moblie.medicalPackage.domain.vo;

import com.ruoyi.common.mobile.entity.BaseTagDO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

@ApiModel("套餐列表")
@Data
public class BaseSetMealVO {
    @ApiModelProperty("id")
    private String id;
    @ApiModelProperty("套餐标题")
    private String title;
    @ApiModelProperty("体检套餐名字")
    private String baseExaminePackageName;
    @ApiModelProperty("体检套餐ID")
    private String baseExaminePackageId;
    @ApiModelProperty("标签id")
    private String tagId;
    @ApiModelProperty("创建时间")
    private Date createdTime;
    @ApiModelProperty("更新时间")
    private Date updateTime;
    @ApiModelProperty("套餐缩略图,由,号隔开")
    private String image;
    @ApiModelProperty("销量（redis定更新时器）")
    private Integer saleNumber;
    @ApiModelProperty("详情轮播图")
    private String banner;
    @ApiModelProperty("套餐详情")
    private String content;
    @ApiModelProperty("删除标识")
    private String delFlag;
    @ApiModelProperty
    private List<BaseTagDO> baseTagVOList;



}
