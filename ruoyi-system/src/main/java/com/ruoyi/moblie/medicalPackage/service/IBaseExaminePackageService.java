package com.ruoyi.moblie.medicalPackage.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.moblie.medicalPackage.domain.dto.BaseExaminePackageDTO;
import com.ruoyi.moblie.medicalPackage.domain.vo.BaseExaminePackageOptionVO;

/**
 * 体检套餐Service接口
 * 
 * @author ruoyi
 * @date 2022-01-19
 */
public interface IBaseExaminePackageService 
{

    /**
     * 查询体检套餐列表Option
     *
     * @param baseExaminePackageQuery 体检套餐
     * @return 体检套餐集合
     */
     Page<BaseExaminePackageOptionVO> selectPackageListOption(BaseExaminePackageDTO baseExaminePackageQuery);

}
