package com.ruoyi.mobile.medicalPackage.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.common.mobile.entity.BaseExamineRecordCombiProjectDO;
import com.ruoyi.mobile.reserveExamine.vo.PortfolioProjectInfoVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

@Mapper
public interface BaseExamineRecordCombiProjectMapper extends BaseMapper<BaseExamineRecordCombiProjectDO> {

    /**
     * 获取项目预约数
     * @param baseExaminePackageId
     * @return
     */
    List<PortfolioProjectInfoVO> portfolioProjectInfo(@Param("baseExaminePackageId") String baseExaminePackageId, @Param("examineDate") Date examineDate);

    /**
     * 批量添加项目
     * @param combiProjectList
     * @return
     */
    Integer insertCombiProjectList(@Param("combiProjectList") List<BaseExamineRecordCombiProjectDO> combiProjectList);
}
