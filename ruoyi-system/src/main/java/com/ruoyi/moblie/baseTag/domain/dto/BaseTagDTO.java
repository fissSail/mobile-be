package com.ruoyi.moblie.baseTag.domain.dto;

import com.ruoyi.common.mobile.common.Page;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;

@Data
@ApiModel("获取标签集合")
public class BaseTagDTO extends Page {
    @ApiModelProperty("标签内容")
    private String content;
    @ApiModelProperty("最小年龄")
    private Integer minAge;
    @ApiModelProperty("最大年龄")
    private Integer maxAge;
    @ApiModelProperty("使用性别")
    private String sex;

}
