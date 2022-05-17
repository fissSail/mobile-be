package com.ruoyi.mobile.medicalPackage.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.mobile.entity.BaseExamineTypeDO;
import com.ruoyi.mobile.medicalPackage.dto.MedicalPackageDTO;
import com.ruoyi.mobile.medicalPackage.vo.BaseSetMealVO;
import com.ruoyi.mobile.medicalPackage.vo.PackageDetailsVO;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public interface MedicalPackageService {
    /**
     * 获取套餐
     * @param medicalPackageDto
     * @return
     */
    Page<BaseSetMealVO> findByList(MedicalPackageDTO medicalPackageDto);

    /**
     * 获取体检套餐详情
     * @param id
     * @return
     */
    PackageDetailsVO packageDetails(String id);

    /**
     * 根据标签id获取套餐推荐
     * @param tagId
     * @return
     */
    List<BaseSetMealVO> packageRecommendation( List<String> tagId, Integer pageSize, Integer pageNum);

    /**
     * 分页获取类型
     * @param pageSize
     * @param pageNum
     * @return
     */
    Page<BaseExamineTypeDO> findByExamineType(Integer pageSize, Integer pageNum);

    /**
     * 获取标题图片
     * @param baseSetMealId
     * @param response
     */
    void titleImage(String baseSetMealId, HttpServletResponse response) throws IOException;

}
