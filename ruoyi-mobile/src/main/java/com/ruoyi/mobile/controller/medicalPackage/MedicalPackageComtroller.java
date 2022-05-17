package com.ruoyi.mobile.controller.medicalPackage;

import com.ruoyi.common.core.domain.AjaxResult;

import com.ruoyi.mobile.medicalPackage.dto.MedicalPackageDTO;
import com.ruoyi.mobile.medicalPackage.service.MedicalPackageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("medicalPackage")
@Api(tags = "体检套餐")
public class MedicalPackageComtroller {


    @Autowired
    private MedicalPackageService medicalPackageService;


    @GetMapping("list")
    @ApiOperation("获取体检套餐")
    public AjaxResult list(MedicalPackageDTO medicalPackageDto) {
        return AjaxResult.success(medicalPackageService.findByList(medicalPackageDto));

    }
    @GetMapping("details/{id}")
    @ApiOperation("获取体检套餐详情")
    public AjaxResult packageDetails(@PathVariable("id") String id) {
        return AjaxResult.success(medicalPackageService.packageDetails(id));

    }

    @GetMapping("recommendation")
    @ApiOperation("根据标签id获取套餐推荐")
    public AjaxResult packageRecommendation(@RequestParam("tagId") List<String> tagId
            , @RequestParam("pageSize") Integer pageSize
            , @RequestParam("pageNum") Integer pageNum
    ) {
        return AjaxResult.success(medicalPackageService.packageRecommendation(tagId, pageSize, pageNum));

    }

    @GetMapping("examineType")
    @ApiOperation("获取体检类型")
    public AjaxResult examineType(@RequestParam("pageSize") Integer pageSize
            , @RequestParam("pageNum") Integer pageNum) {
        return AjaxResult.success(medicalPackageService.findByExamineType(pageSize, pageNum));

    }

}
