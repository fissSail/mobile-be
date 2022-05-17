package com.ruoyi.mobile.reserveExamine.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.mobile.entity.BaseExamineRecordDO;
import com.ruoyi.mobile.medicalPackage.vo.ExamineProjectNameDetailsVO;
import com.ruoyi.mobile.reserveExamine.dto.BaseExamineRecordSimpleDTO;
import com.ruoyi.mobile.reserveExamine.dto.ExamineFinishListDTO;
import com.ruoyi.mobile.reserveExamine.vo.ExamineFinishListVO;
import com.ruoyi.mobile.reserveExamine.vo.ExamineRecordVO;
import com.ruoyi.mobile.userInfo.vo.PersonInfoVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BaseExamineRecordMapper extends BaseMapper<BaseExamineRecordDO> {
    /**
     * 获取体检项目
     * @param wrapper
     * @return
     */
    Page<ExamineRecordVO> findByList(@Param("ew")QueryWrapper wrapper, Page page);

    /**
     * 获取体检项目详情
     * @param objectPage
     * @param examineRecordId
     * @return
     */
    Page<ExamineRecordVO> packageDetails(Page<Object> objectPage, @Param("examineRecordId") String examineRecordId);

    /**
     * 根据id查询项目的体检项目
     * @param combiProjectId
     * @return
     */
    List<ExamineProjectNameDetailsVO> findbyCombiProjectIdsName(@Param("combiProjectId") String combiProjectId);

    /**
     * 根据人员档案id查询套餐id
     * @param personRecordId 人员档案id
     * @return
     */
    List<String> selectExaminePackageIdByPersonRecordId(@Param("personRecordId")String personRecordId);

    /**
     * 获取体检完成列表
     * @param wrapper
     * @return
     */
    Page<ExamineFinishListVO> selectExamineFinishList(Page page, @Param("ew") QueryWrapper wrapper);

    /**
     * 获取人员信息
     * @param examineRecordId
     * @return
     */
    PersonInfoVo selectPersonInfo(@Param("examineRecordId") String examineRecordId);

    BaseExamineRecordSimpleDTO selectBaseExamineRecordSimpleById(@Param("examineRecordId") String examineRecordId);
}
