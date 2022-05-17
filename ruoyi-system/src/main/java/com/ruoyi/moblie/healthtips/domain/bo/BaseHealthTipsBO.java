package com.ruoyi.moblie.healthtips.domain.bo;

import lombok.Data;

/**
 * @author yanfeifan
 * @Package com.ruoyi.moblie.domain.bo
 * @Description
 * @date 2022/3/29 16:22
 */
@Data
public class BaseHealthTipsBO {

    /**
     * 公告ID
     */
    private String id;
    /**
     * 公告标题
     */
    private String tipsTitle;
    /**
     * 公告内容
     */
    private String tipsContent;
    /**
     * 公告类型（0通知，1健康小常识）
     */
    private String tipsType;
    /**
     * 公告状态（0正常 1关闭）
     */
    private String status;
    /**
     * 套餐id
     */
    private String setMealId;
}
