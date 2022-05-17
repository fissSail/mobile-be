package com.ruoyi.web.controller.moblie.healthtips;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.mobile.entity.BaseHealthTipsDO;
import com.ruoyi.moblie.healthtips.domain.bo.BaseHealthTipsBO;
import com.ruoyi.moblie.healthtips.service.BaseHealthTipsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * 健康小常识表
 *
 * @author yff
 * @date 2022-03-29 15:23:08
 */
@RestController
@RequestMapping("health/tips")
public class BaseHealthTipsController extends BaseController {

    @Autowired
    private BaseHealthTipsService baseHealthTipsService;

    /**
     * 列表
     */
    @GetMapping("/list")
    public TableDataInfo list(@RequestParam(value = "tipsTitle", required = false) String tipsTitle,
                              @RequestParam(value = "tipsType", required = false) String tipsType) {
        startPage();
        List<BaseHealthTipsDO> list = baseHealthTipsService.queryPage(tipsTitle,tipsType);
        return getDataTable(list);
    }


    /**
     * 信息
     */
    @GetMapping("/{id}")
    public AjaxResult info(@PathVariable("id") String id) {
        BaseHealthTipsDO baseHealthTips = baseHealthTipsService.getById(id);

        return AjaxResult.success(baseHealthTips);
    }

    /**
     * 保存
     */
    @PostMapping("")
    public AjaxResult save(@RequestBody BaseHealthTipsBO baseHealthTipsBO) {
        baseHealthTipsService.saveHealthTips(baseHealthTipsBO);

        return AjaxResult.success();
    }

    /**
     * 修改
     */
    @PutMapping("")
    public AjaxResult update(@RequestBody BaseHealthTipsBO baseHealthTipsBO) {
        baseHealthTipsService.updateHealthTipsById(baseHealthTipsBO);

        return AjaxResult.success();
    }

    /**
     * 删除
     */
    @DeleteMapping("/{ids}")
    public AjaxResult delete(@PathVariable("ids") String[] ids) {
        baseHealthTipsService.removeByIds(Arrays.asList(ids));

        return AjaxResult.success();
    }

}
