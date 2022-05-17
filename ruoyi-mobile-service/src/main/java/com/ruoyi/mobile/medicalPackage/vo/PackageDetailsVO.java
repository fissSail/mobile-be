package com.ruoyi.mobile.medicalPackage.vo;

import com.ruoyi.common.mobile.entity.BaseTagDO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@ApiModel("套餐详情")
@Data
public class PackageDetailsVO {

    @ApiModelProperty("套餐标题")
    private String title;
    @ApiModelProperty("套餐名字")
    private String examinePackageName;
    @ApiModelProperty("体检套餐id")
    private String baseExaminePackageId;
    @ApiModelProperty("销量（redis定更新时器）")
    private Integer saleNumber;
    @ApiModelProperty("套餐详情")
    private String content;
    @ApiModelProperty("套餐价格")
    private BigDecimal amountReceivable;
    @ApiModelProperty("套餐图片")
    private String image;
    @ApiModelProperty("温馨提示")
    private String matter;
    @ApiModelProperty("适用性别")
    private Integer sex;
    @ApiModelProperty("适用人群")
    private String examineType;
    @ApiModelProperty("套餐名字")
    private String comboName;
    @ApiModelProperty("标签id集合")
    private List<BaseTagDO> baseTagVOList;

}
