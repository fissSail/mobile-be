package com.ruoyi.moblie.baseTag.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.mobile.entity.BaseTagDO;
import com.ruoyi.moblie.baseTag.domain.dto.BaseTagDTO;

public interface BaseTagService {
    /**
     * 获取便签
     * @param
     * @param baseTagDTO
     * @return
     */
    Page<BaseTagDO> list(BaseTagDTO baseTagDTO);

    /**
     * 添加或更新标签
     * @param baseTagDO
     * @return
     */
    AjaxResult updateAndAdd(BaseTagDO baseTagDO);
}
