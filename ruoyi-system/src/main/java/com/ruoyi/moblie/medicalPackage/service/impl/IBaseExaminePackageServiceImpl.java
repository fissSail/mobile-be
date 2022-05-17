package com.ruoyi.moblie.medicalPackage.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.moblie.medicalPackage.domain.dto.BaseExaminePackageDTO;
import com.ruoyi.moblie.medicalPackage.domain.vo.BaseExaminePackageOptionVO;
import com.ruoyi.moblie.medicalPackage.mapper.SysBaseExaminePackageMapper;
import com.ruoyi.moblie.medicalPackage.service.IBaseExaminePackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IBaseExaminePackageServiceImpl implements IBaseExaminePackageService {
    @Autowired
    private SysBaseExaminePackageMapper sysBaseExaminePackageMapper;

    /**
     * 查询体检套餐列表Option
     *
     * @param baseExaminePackageQuery 体检套餐
     * @return 体检套餐集合
     */
    @Override
    public Page<BaseExaminePackageOptionVO> selectPackageListOption(BaseExaminePackageDTO baseExaminePackageQuery) {
        return  sysBaseExaminePackageMapper.selectPackageListOption(new Page<>(baseExaminePackageQuery.getPageNum(),baseExaminePackageQuery.getPageSize()),baseExaminePackageQuery);

    }
}
