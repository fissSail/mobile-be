package com.ruoyi.common.mobile.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel("移动端套餐表")
@TableName("base_set_meal")
public class BaseSetMealDO {
    @ApiModelProperty("id")
    private String id;
    @ApiModelProperty("套餐标题")
    private String title;
    @ApiModelProperty("体检套餐id")
    private String baseExaminePackageId;
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

}