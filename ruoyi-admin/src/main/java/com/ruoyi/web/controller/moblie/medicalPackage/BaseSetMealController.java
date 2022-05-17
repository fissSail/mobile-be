package com.ruoyi.web.controller.moblie.medicalPackage;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.mobile.entity.BaseSetMealDO;
import com.ruoyi.common.mobile.enums.CommonlyUsedEnum;
import com.ruoyi.moblie.medicalPackage.domain.dto.BaseSetMealAddAndUpdateDTO;
import com.ruoyi.moblie.medicalPackage.domain.dto.BaseSetMealDTO;
import com.ruoyi.moblie.medicalPackage.domain.dto.DeleteComboDTO;
import com.ruoyi.moblie.medicalPackage.service.BaseSetMealService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("setMeal")
@Api(tags = "后台移动端套餐管理")
public class BaseSetMealController {

    @Autowired
    private BaseSetMealService baseSetMealService;


    @GetMapping("list")
    @ApiOperation("获取套餐")
    public AjaxResult list(BaseSetMealDTO baseSetMealDTO) {
        return AjaxResult.success(baseSetMealService.list(baseSetMealDTO));
    }

    @PostMapping("delete")
    @ApiOperation("删除套餐")
    public AjaxResult delete(@RequestBody DeleteComboDTO deleteComboDTO) {
        AjaxResult success = AjaxResult.success();
        if (deleteComboDTO.getSetMealIds().isEmpty()) {
            return success;
        }
        baseSetMealService.update(new UpdateWrapper<BaseSetMealDO>()
                .set("del_flag", CommonlyUsedEnum.DELETE.val())
                .in("id", deleteComboDTO.getSetMealIds()));

        return success;
    }


    @PostMapping("updateCombo")
    @ApiOperation("添加套餐或更新套餐")
    public AjaxResult update(@RequestBody BaseSetMealAddAndUpdateDTO baseSetMealAddAndUpdateDTO) {
        return baseSetMealService.updateCombo(baseSetMealAddAndUpdateDTO);
    }

    @PostMapping("testPackageSales")
    @ApiOperation("测试定时器")
    public AjaxResult update() {
        baseSetMealService.packageSales();
        return AjaxResult.success();
    }
}
