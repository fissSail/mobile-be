package com.ruoyi.web.controller.moblie.baseTag;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.mobile.entity.BaseTagDO;
import com.ruoyi.moblie.baseTag.domain.dto.BaseTagDTO;
import com.ruoyi.moblie.baseTag.service.BaseTagService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping("tag")
@RestController
@Api(tags = "后台套餐便签管理")
public class BaseTagController {


    @Autowired
    private BaseTagService baseTagService;

    @PostMapping("list")
    @ApiOperation("获取便签集合")
    public AjaxResult list(@RequestBody BaseTagDTO baseTagDTO) {
        return AjaxResult.success(baseTagService.list(baseTagDTO));
    }


    @PostMapping("updateAndAdd")
    @ApiOperation("添加或更新标签")
    public AjaxResult updateAndAdd(@Valid @RequestBody BaseTagDO baseTagDO) {
        return AjaxResult.success(baseTagService.updateAndAdd(baseTagDO));
    }
}
