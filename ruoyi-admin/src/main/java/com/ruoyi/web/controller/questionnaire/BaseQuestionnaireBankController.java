package com.ruoyi.web.controller.questionnaire;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.mobile.entity.BaseQuestionnaireBankDO;
import com.ruoyi.moblie.healthtips.domain.vo.SysBaseQuestionnaireBankVO;
import com.ruoyi.moblie.questionnaire.service.BaseQuestionnaireBankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;


/**
 * 问卷问题controller
 *
 * @author yff
 * @date 2022-03-24 10:29:06
 */
@RestController
@RequestMapping("questionnaire/bank")
public class BaseQuestionnaireBankController extends BaseController {
    @Autowired
    private BaseQuestionnaireBankService baseQuestionnaireBankService;

    /**
     * 列表
     */
    @GetMapping("/list")
    public TableDataInfo list(@RequestParam(value = "questionTitle", required = false) String questionTitle){
        startPage();
        List<BaseQuestionnaireBankDO> list = baseQuestionnaireBankService.queryPage(questionTitle);

        return getDataTable(list);
    }


    /**
     * 信息
     */
    @GetMapping("/{id}")
    public AjaxResult info(@PathVariable("id") String id){
        SysBaseQuestionnaireBankVO baseQuestionnaireBank = baseQuestionnaireBankService.getQuestionnaireBankAndOptionsById(id);

        return AjaxResult.success( baseQuestionnaireBank);
    }

    /**
     * 保存
     */
    @PostMapping("")
    public AjaxResult save(@RequestBody SysBaseQuestionnaireBankVO sysBaseQuestionnaireBankVO){
		baseQuestionnaireBankService.saveQuestionnaireBankAndOptions(sysBaseQuestionnaireBankVO);

        return AjaxResult.success();
    }

    /**
     * 修改
     */
    @PutMapping("")
    public AjaxResult update(@RequestBody SysBaseQuestionnaireBankVO sysBaseQuestionnaireBankVO){
		baseQuestionnaireBankService.updateQuestionnaireBankAndOptionsById(sysBaseQuestionnaireBankVO);

        return AjaxResult.success();
    }

    /**
     * 删除
     */
    @DeleteMapping("/{ids}")
    public AjaxResult delete(@PathVariable("ids") String[] ids){
		baseQuestionnaireBankService.removeByIds(Arrays.asList(ids));

        return AjaxResult.success();
    }

}
