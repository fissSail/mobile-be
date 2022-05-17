package com.ruoyi.mobile.reserveExamine.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("项目预约人数详情")
public class PortfolioProjectInfoVO {
    @ApiModelProperty("已预约数")
    private Integer number;
    @ApiModelProperty("项目id")
    private String combiProjectId;
    @ApiModelProperty("项目最大数")
    private Integer projectNumber;
    @ApiModelProperty("是否超过  1超过  0未超过")
    private Integer isExceed;

}
