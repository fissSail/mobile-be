package com.ruoyi.moblie.healthtips.domain.vo;

import lombok.Data;

import java.util.List;

/**
 * @author yanfeifan
 * @Package com.ruoyi.moblie.domain.vo
 * @Description
 * @date 2022/4/12 15:28
 */
@Data
public class QuestionnaireAnswersOptionRelationVO {

    /**
     * 问卷题目id
     */
    private String id;

    /**
     * 问题标题
     */
    private String questionTitle;

    /**
     * 题目类型 0单选，1多选
     */
    private String questionType;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 是否必选 0必填，1选填
     */
    private String requiredFlag;

    /**
     * 问题选项
     */
    private List<SysQuestionnaireAnswersVO> questionnaireAnswersVOList;

    /**
     * 答案ids
     */
    private List<String> questionnaireAnswersIdList;

}
