package com.ruoyi.common.mobile.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 问卷答卷选项答案表
 *
 * @author yff
 * @date 2022-03-24 10:29:06
 */
@Data
@TableName("base_questionnaire_answers_option_relation")
public class BaseQuestionnaireAnswersOptionRelationDO {

    /**
     *
     */
    @TableId
    private String id;

    /**
     * 问题表id
     */
    private String questionnaireBankId;

    /**
     * 问题选项id
     */
    private String questionnaireAnswersId;

    /**
     * 答题表id
     */
    private String questionnaireAnswersheetId;

}
