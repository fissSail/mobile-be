package com.ruoyi.system.service;


import java.util.List;

/**
 * 模板Service接口
 * 
 * @author zengtao
 * @date 2022-01-20
 */
public interface ISysTemplateService 
{
    /**
     * @Author Administrator
     * @Description 根据模板类型查询报告模板的存储url
     * @Date 17:29 2022/3/4
     * @Param [templateType]
     * @return java.util.List<java.lang.String>
     **/
    public List<String> selectUrlByTemplateType(String templateType);
    

}
