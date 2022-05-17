package com.ruoyi.moblie.healthtips.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.mobile.entity.BaseHealthTipsDO;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.uuid.IdUtils;
import com.ruoyi.moblie.healthtips.domain.bo.BaseHealthTipsBO;
import com.ruoyi.moblie.healthtips.mapper.BaseHealthTipsMapper;
import com.ruoyi.moblie.healthtips.service.BaseHealthTipsService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;


@Service
public class BaseHealthTipsServiceImpl extends ServiceImpl<BaseHealthTipsMapper, BaseHealthTipsDO> implements BaseHealthTipsService {

    @Override
    public List<BaseHealthTipsDO> queryPage(String tipsTitle,String tipsType) {
        return this.list(new QueryWrapper<BaseHealthTipsDO>()
                .like(StringUtils.hasText(tipsTitle), "tips_title", tipsTitle)
                .eq(StringUtils.hasText(tipsType), "tips_type", tipsType)
                .orderByDesc("create_time")
        );
    }

    @Override
    public Boolean saveHealthTips(BaseHealthTipsBO baseHealthTipsBO) {
        BaseHealthTipsDO healthTipsDO = new BaseHealthTipsDO();
        BeanUtils.copyProperties(baseHealthTipsBO,healthTipsDO);
        healthTipsDO.setCreateTime(DateUtils.getNowDate());
        healthTipsDO.setId(IdUtils.fastSimpleUUID());
        healthTipsDO.setCreateBy(SecurityUtils.getLoginUser().getUsername());
        return this.save(healthTipsDO);
    }

    @Override
    public Boolean updateHealthTipsById(BaseHealthTipsBO baseHealthTipsBO) {
        BaseHealthTipsDO healthTipsDO = new BaseHealthTipsDO();
        BeanUtils.copyProperties(baseHealthTipsBO,healthTipsDO);
        healthTipsDO.setUpdateTime(DateUtils.getNowDate());
        healthTipsDO.setUpdateBy(SecurityUtils.getLoginUser().getUsername());
        return this.updateById(healthTipsDO);
    }
}
