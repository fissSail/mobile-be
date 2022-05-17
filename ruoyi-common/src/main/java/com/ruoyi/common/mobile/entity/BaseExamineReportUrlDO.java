package com.ruoyi.common.mobile.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @date: 2022/4/7  15:36
 * @author: 1623394432@qq.com
 */
@Data
@ApiModel("体检预览报告地址")
@TableName("base_examine_report_url")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BaseExamineReportUrlDO {
    @ApiModelProperty("体检记录id")
    private String  examineRecordId;
    @ApiModelProperty("预览报告地址")
    private String   examineReportUrl;

}
