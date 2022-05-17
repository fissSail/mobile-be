package com.ruoyi.mobile.healthtips.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author yanfeifan
 * @Package com.ruoyi.mobile.vo
 * @Description 移动端健康小常识返回实体
 * @date 2022/3/30 14:14
 */
@Data
public class HealthTipsVO {

    @ApiModelProperty(value = "健康小常识/通知公告id")
    private String id;
    /**
     * 健康小常识标题
     */
    @ApiModelProperty(value = "健康小常识/通知公告标题")
    private String tipsTitle;
    /**
     * 健康小常识内容
     */
    @ApiModelProperty(value = "健康小常识/通知公告内容")
    private String tipsContent;
    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createTime;
}
