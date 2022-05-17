package com.ruoyi.mobile.reserveExamine.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@ApiModel("用户预约体检")
@Data
public class ReserveExamineDTO {

    @ApiModelProperty("体检类型id")
    @NotBlank(message = "体检类型不能为空")
    private String examineTypeId;
    @ApiModelProperty("体检时间")
    @NotBlank(message = "体检时间不能为空")
    private String examineDate;
    @ApiModelProperty("体检套餐")
    @NotBlank(message = "体检套餐不能为空")
    private String baseExaminePackageId;
    @ApiModelProperty("是否加急  1是0否")
    private String isExpedited;
    @ApiModelProperty("是否复检  1是0否")
    private String isRecheck;
    @ApiModelProperty("是否忽律部分项目已满  1是0否")
    private Integer isFull;
}
