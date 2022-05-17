package com.ruoyi.mobile.userInfo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.common.mobile.entity.BasePersonRecordDO;
import com.ruoyi.mobile.vo.SysDictDataVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BasePersonRecordMapper extends BaseMapper<BasePersonRecordDO> {




    /**
     * 查看字段数据
     * @param type
     * @return
     */
    List<SysDictDataVO> dictionary(@Param("type") String type);
}
