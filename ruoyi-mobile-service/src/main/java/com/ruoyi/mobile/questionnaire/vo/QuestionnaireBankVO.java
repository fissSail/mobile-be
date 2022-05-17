package com.ruoyi.mobile.questionnaire.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author yanfeifan
 * @Package com.ruoyi.mobile.vo
 * @Description 题库表返回实体
 * @date 2022/3/24 10:45
 */
@Data
public class QuestionnaireBankVO {

    /**
     * 问题表id
     */
    @ApiModelProperty(value = "问题表id")
    private String id;

    /**
     * 问题标题
     */
    @ApiModelProperty(value = "问题标题")
    private String questionTitle;

    /**
     * 题目类型 0单选，1多选
     */
    @ApiModelProperty(value = "题目类型 0单选，1多选")
    private String questionType;

    /**
     * 是否必选 0必填，1选填
     */
    @ApiModelProperty(value = "是否必选 0必填，1选填")
    private String requiredFlag;

    /**
     * 问卷选项list
     */
    @ApiModelProperty(value = "问卷选项list")
    private List<QuestionnaireAnswersVO> questionnaireAnswersVOList;
}
