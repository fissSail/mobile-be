package com.ruoyi.common.mobile.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("base_set_meal_tag")
@ApiModel("套餐绑定标签详情")
public class BaseSetMealTagDO {
    @ApiModelProperty("套餐id")
    private String baseSetMealId;
    @ApiModelProperty("标签id")
    private String tagId;
}
