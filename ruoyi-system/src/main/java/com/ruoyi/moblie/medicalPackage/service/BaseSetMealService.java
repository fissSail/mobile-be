package com.ruoyi.moblie.medicalPackage.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.mobile.entity.BaseSetMealDO;
import com.ruoyi.moblie.medicalPackage.domain.dto.BaseSetMealAddAndUpdateDTO;
import com.ruoyi.moblie.medicalPackage.domain.dto.BaseSetMealDTO;
import com.ruoyi.moblie.medicalPackage.domain.vo.BaseSetMealVO;

public interface BaseSetMealService extends IService<BaseSetMealDO> {
    /**
     获取套餐
     * @param baseSetMealDTO
     * @return
     */
    Page<BaseSetMealVO> list(BaseSetMealDTO baseSetMealDTO);



    /**更新或添加移动套餐
     *
     * @param baseSetMealAddAndUpdateDTO
     * @return
     */
    AjaxResult updateCombo(BaseSetMealAddAndUpdateDTO baseSetMealAddAndUpdateDTO);

    /**
     * 更新套餐销量
     */
    void packageSales();
}
