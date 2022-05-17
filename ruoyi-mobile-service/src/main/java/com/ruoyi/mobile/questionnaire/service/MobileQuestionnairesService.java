package com.ruoyi.mobile.questionnaire.service;

import com.ruoyi.common.mobile.entity.BaseQuestionnaireAnswersOptionRelationDO;
import com.ruoyi.mobile.questionnaire.bo.QuestionnaireBO;
import com.ruoyi.mobile.questionnaire.vo.QuestionnaireBankVO;

import java.util.List;
import java.util.Set;

/**
 * @author yanfeifan
 * @Package com.ruoyi.mobile.service
 * @Description 问卷service
 * @date 2022/3/23 15:07
 */
public interface MobileQuestionnairesService {

    /**
     * 问卷列表
     * @return
     */
    List<QuestionnaireBankVO> list();

    /**
     * 提交问卷
     * @param questionnaireBO
     */
    void add(QuestionnaireBO questionnaireBO);

    /**
     * 根据问卷答卷选项绑定用户标签
     * @param questionnaireAnswersOptionRelationDOList
     * @param userId
     * @return
     */
    void addPersonTagByAnswerSheet(String userId, List<BaseQuestionnaireAnswersOptionRelationDO> questionnaireAnswersOptionRelationDOList);
}
