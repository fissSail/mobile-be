package com.ruoyi.mobile.controller.questionnaire;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.mobile.questionnaire.bo.QuestionnaireBO;
import com.ruoyi.mobile.questionnaire.service.MobileQuestionnairesService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author yanfeifan
 * @Package com.ruoyi.mobile.controller
 * @Description 问卷controller
 * @date 2022/3/23 15:04
 */
@RequestMapping("mobile/questionnaire")
@RestController
@Api(tags = "调查问卷")
public class MobileQuestionnaireController {

    @Autowired
    private MobileQuestionnairesService mobileQuestionnairesService;

    /**
     * 列表
     */
    @GetMapping("/list")
    @ApiOperation("调查问卷列表")
    public AjaxResult list() {
        return AjaxResult.success(mobileQuestionnairesService.list());
    }

    /**
     * 提交问卷
     */
    @PostMapping("/add")
    @ApiOperation("提交问卷")
    public AjaxResult add(@Valid @RequestBody QuestionnaireBO questionnaireBO) {
        mobileQuestionnairesService.add(questionnaireBO);
        return AjaxResult.success();
    }


}
