package com.ruoyi.mobile.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("字典类型")
@Data
public class SysDictDataVO {
    @ApiModelProperty("字典标签")
    private String dictLabel;
    @ApiModelProperty("字典键值")
    private String dictValue;
    @ApiModelProperty("字典类型")
    private String dictType;
}
