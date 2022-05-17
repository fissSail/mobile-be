package com.ruoyi.mobile.medicalPackage.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.mobile.entity.BaseSetMealDO;
import com.ruoyi.mobile.medicalPackage.vo.BaseSetMealVO;
import com.ruoyi.mobile.medicalPackage.vo.PackageDetailsVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MedicalPackageMapper extends BaseMapper<BaseSetMealDO> {

    /**
     * 获取套餐
     * @param condition
     * @param objectPage
     * @return
     */
    Page<BaseSetMealVO> findByList(@Param("ew") QueryWrapper condition, Page<Object> objectPage);

    /**
     * 获取套餐详情
     * @param id
     * @return
     */
    PackageDetailsVO packageDetails(@Param("id") String id);

    /**
     * 获取套餐推荐
     * @param objectPage
     * @param wrapper
     * @return
     */
    Page<BaseSetMealVO> selectPackageRecommendation(Page<Object> objectPage, @Param("ew") QueryWrapper<Object> wrapper);

    /**
     * 根据套餐销量选择推荐套餐
     * @return
     */
    Page<BaseSetMealVO> selectSetMealSaleNumber(Page<Object> objectPage);

    /**
     * 查找套餐id
     * @param tagId
     * @return
     */
    List<String> findBaseSetMealId(@Param("tagIds")  List<String> tagId);
}
