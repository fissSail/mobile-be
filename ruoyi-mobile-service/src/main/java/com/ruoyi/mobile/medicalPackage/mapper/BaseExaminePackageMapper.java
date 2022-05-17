package com.ruoyi.mobile.medicalPackage.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.common.mobile.entity.BaseExaminePackageDO;
import com.ruoyi.mobile.medicalPackage.vo.BaseCombiProjectVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BaseExaminePackageMapper extends BaseMapper<BaseExaminePackageDO> {
    /**
     * 获取套餐的项目
     * @param baseExaminePackageId
     * @return
     */
    List<BaseCombiProjectVO> findByCombiProject(@Param("baseExaminePackageId") String baseExaminePackageId);



}
