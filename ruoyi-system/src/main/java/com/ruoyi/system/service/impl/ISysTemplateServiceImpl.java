package com.ruoyi.system.service.impl;

import com.ruoyi.system.mapper.SysTemplateMapper;
import com.ruoyi.system.service.ISysTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ISysTemplateServiceImpl implements ISysTemplateService {

    @Autowired
    private SysTemplateMapper sysTemplateMapper;
    /**
     * @param templateType
     * @return java.util.List<java.lang.String>
     * @Author Administrator
     * @Description 根据模板类型查询报告模板的存储url
     * @Date 17:29 2022/3/4
     * @Param [templateType]
     */
    @Override
    public List<String> selectUrlByTemplateType(String templateType) {
        return sysTemplateMapper.selectUrlByTemplateType(templateType);
    }
}
