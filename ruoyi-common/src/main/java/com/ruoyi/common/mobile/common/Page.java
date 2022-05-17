package com.ruoyi.common.mobile.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @date: 2022/4/8  13:54
 * @author: 1623394432@qq.com
 */
@ApiModel("查询分页")
@Data
public class Page {
    @ApiModelProperty("页")
    private Integer pageNum=1;
    @ApiModelProperty("大小")
    private Integer pageSize=10;
}
