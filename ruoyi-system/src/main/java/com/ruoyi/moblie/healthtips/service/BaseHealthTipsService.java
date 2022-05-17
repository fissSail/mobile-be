package com.ruoyi.moblie.healthtips.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.common.mobile.entity.BaseHealthTipsDO;
import com.ruoyi.moblie.healthtips.domain.bo.BaseHealthTipsBO;

import java.util.List;

/**
 * 健康小常识表
 *
 * @author yff
 * @date 2022-03-29 15:23:08
 */
public interface BaseHealthTipsService extends IService<BaseHealthTipsDO> {

    /**
     * 查询小常识列表
     * @param tipsTitle 公告标题
     * @return
     */
    List<BaseHealthTipsDO> queryPage(String tipsTitle,String tipsType);

    /**
     * 保存健康小常识
     * @param baseHealthTipsBO
     */
    Boolean saveHealthTips(BaseHealthTipsBO baseHealthTipsBO);

    /**
     * 修改健康小常识
     * @param baseHealthTipsBO
     */
    Boolean updateHealthTipsById(BaseHealthTipsBO baseHealthTipsBO);
}

