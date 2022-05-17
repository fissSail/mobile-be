package com.ruoyi.system.mapper;

import java.util.List;

public interface SysTemplateMapper {
    /**
     * @Author Administrator
     * @Description 根据模板类型查询报告模板的存储url
     * @Date 17:29 2022/3/4
     * @Param [templateType]
     * @return java.util.List<java.lang.String>
     **/
    public List<String> selectUrlByTemplateType(String templateType);

}
