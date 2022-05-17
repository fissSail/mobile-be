package com.ruoyi.moblie.medicalPackage.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @date: 2022/4/7  9:20
 * @author: 1623394432@qq.com
 */
@Data
@ApiModel("删除套餐")
public class DeleteComboDTO {
    @ApiModelProperty("套餐id")
    private List<String> setMealIds;
}
