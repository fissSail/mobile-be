package com.ruoyi.moblie.baseTag.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.common.mobile.entity.BaseSetMealTagDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BaseSetMealTagMapper extends BaseMapper<BaseSetMealTagDO> {

    Integer insertList(@Param("tagIdList") List<String> tagIdList, @Param("setMealId") String setMealId);
}
