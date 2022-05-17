package com.ruoyi.web.controller.questionnaire;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.mobile.entity.BaseQuestionnaireAnswersheetDO;
import com.ruoyi.moblie.healthtips.domain.vo.QuestionnaireAnswersOptionRelationVO;
import com.ruoyi.moblie.questionnaire.service.BaseQuestionnaireAnswersheetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 用户答卷controller
 *
 * @author yff
 * @date 2022-04-12 10:29:06
 */
@RestController
@RequestMapping("questionnaire/answersheet")
public class BaseQuestionnaireAnswersheetController extends BaseController {
    @Autowired
    private BaseQuestionnaireAnswersheetService baseQuestionnaireAnswersheetService;

    /**
     * 列表
     */
    @GetMapping("/list")
    public TableDataInfo list(@RequestParam(value = "userName", required = false) String userName) {
        startPage();
        List<BaseQuestionnaireAnswersheetDO> list = baseQuestionnaireAnswersheetService.queryPage(userName);

        return getDataTable(list);
    }


    /**
     * 查询用户答卷详情
     */
    @GetMapping("/{id}")
    public AjaxResult info(@PathVariable("id") String id) {
        List<QuestionnaireAnswersOptionRelationVO> questionnaireAnswersOptionRelationVO =
                baseQuestionnaireAnswersheetService.getQuestionnaireAnswersOptionRelationByAnswersheetId(id);

        return AjaxResult.success(questionnaireAnswersOptionRelationVO);
    }

}
