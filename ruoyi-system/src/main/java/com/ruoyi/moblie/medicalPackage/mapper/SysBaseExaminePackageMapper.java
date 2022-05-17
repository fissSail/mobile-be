package com.ruoyi.moblie.medicalPackage.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.moblie.medicalPackage.domain.dto.BaseExaminePackageDTO;
import com.ruoyi.moblie.medicalPackage.domain.vo.BaseExaminePackageOptionVO;
import org.apache.ibatis.annotations.Param;

/**
 * 体检套餐Mapper接口
 * 
 * @author ruoyi
 * @date 2022-01-19
 */
public interface SysBaseExaminePackageMapper
{

    /**
     * 查询体检套餐列表Option
     *
     * @param baseExaminePackageQuery 体检套餐
     * @return 体检套餐集合
     */
    public Page<BaseExaminePackageOptionVO> selectPackageListOption(Page page,@Param("baseExaminePackageQuery") BaseExaminePackageDTO baseExaminePackageQuery);



}
