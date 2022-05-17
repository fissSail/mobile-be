package com.ruoyi.mobile.generateCode.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
//import com.ruoyi.business.basicdata.domain.request.BaseCodingVo;
import com.ruoyi.common.mobile.entity.BaseCodingDO;

/**
 * @Author Administrator
 * @Description 生成编码使用到的序号表的Mapper
 * @Date 18:05 2022/1/26
 * @Param
 * @return
 **/
public interface BaseCodingMapper extends BaseMapper<BaseCodingDO> {
    


    /**
     * @Author Administrator
     * @Description 获取操作员插入的最后一个序号
     * @Date 18:11 2022/1/26
     * @Param [createId]
     * @return int
     **/
    public Long getLastOrderNumByCodeType(String codeType);
}
