package com.ruoyi.mobile.reserveExamine.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.common.mobile.entity.BaseExamineRecordProjectDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BaseExamineRecordProjectMapper extends BaseMapper<BaseExamineRecordProjectDO> {
    Integer insertProjectList(@Param("projectList") List<BaseExamineRecordProjectDO> projectList);
}
