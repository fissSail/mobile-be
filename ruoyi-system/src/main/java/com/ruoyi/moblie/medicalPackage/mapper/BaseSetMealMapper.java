package com.ruoyi.moblie.medicalPackage.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.mobile.entity.BaseSetMealDO;
import com.ruoyi.common.mobile.entity.BaseTagDO;
import com.ruoyi.moblie.medicalPackage.domain.vo.BaseSetMealVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BaseSetMealMapper extends BaseMapper<BaseSetMealDO> {

    /**
     * 获取体检套餐
     *
     * @param wrapper
     * @param objectPage
     * @return
     */

    Page<BaseSetMealVO> findByList(@Param("ew") QueryWrapper<BaseSetMealVO> wrapper, Page<Object> objectPage);
   /*
      根据套餐查询标签
    */

    List<BaseTagDO> findBySetMealIdBaseTagList(@Param("SetMealId") String setMealId);

}
