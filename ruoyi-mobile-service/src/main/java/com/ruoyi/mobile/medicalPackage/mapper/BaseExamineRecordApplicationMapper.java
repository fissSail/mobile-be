package com.ruoyi.mobile.medicalPackage.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.common.mobile.entity.BaseExamineRecordApplicationDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BaseExamineRecordApplicationMapper extends BaseMapper<BaseExamineRecordApplicationDO> {
    /**
     * @Author Administrator
     * @Description 根据体检记录ID和组合项目ID查询是否已经生成了条码
     * @Date 15:27 2022/2/17
     * @Param [examineRecordId, combiProjectId]
     * @return java.lang.String
     **/
    public String selectBarCodeByExamineRecordIdAndCombiProjectId(@Param("examineRecordId") String examineRecordId, @Param("combiProjectId") String combiProjectId);

    /**
     * 批量添加申请码添加
     * @param examineRecordApplicationVos
     * @return
     */
    Integer insertBaseExamineRecordApplicationByBatch(@Param("examineRecordApplicationVos") List<BaseExamineRecordApplicationDO> examineRecordApplicationVos);
}
