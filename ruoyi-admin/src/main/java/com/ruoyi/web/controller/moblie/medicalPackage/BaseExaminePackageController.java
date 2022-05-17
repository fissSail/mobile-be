package com.ruoyi.web.controller.moblie.medicalPackage;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.moblie.medicalPackage.domain.dto.BaseExaminePackageDTO;
import com.ruoyi.moblie.medicalPackage.service.IBaseExaminePackageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 体检套餐Controller
 * 
 * @author ruoyi
 * @date 2022-01-19
 */
@RestController
@RequestMapping("/examinePackage")
@Api(tags = "后台pc端套餐")
public class BaseExaminePackageController
{
    @Autowired
    private IBaseExaminePackageService baseExaminePackageService;



    /**
     * 查询非危害因素体检套餐列表option
     */
    @GetMapping("/optionList")
    @ApiOperation("获取pc端套餐")
    public AjaxResult optionList(BaseExaminePackageDTO baseExaminePackageQuery)
    {

        return  AjaxResult.success(baseExaminePackageService.selectPackageListOption(baseExaminePackageQuery));

    }

}
