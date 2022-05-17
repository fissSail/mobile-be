package com.ruoyi.mobile.medicalPackage.vo;

import com.ruoyi.common.mobile.entity.BaseTagDO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@ApiModel("体检套餐列表返回数据")
public class BaseSetMealVO {

    @ApiModelProperty("套餐id")
    private String id;
    @ApiModelProperty("套餐标题")
    private String title;
    @ApiModelProperty("套餐名字")
    private String examinePackageName;
    @ApiModelProperty("体检套餐id")
    private String baseExaminePackageId;
    @ApiModelProperty("标签id")
    private String tagId;
    @ApiModelProperty("销量（redis定更新时器）")
    private Integer saleNumber;
    @ApiModelProperty("套餐详情")
    private String content;
    @ApiModelProperty("套餐价格")
    private BigDecimal amountReceivable;
    @ApiModelProperty("套餐图片")
    private String image;
    @ApiModelProperty
    private List<BaseTagDO> baseTagVOList;
}
