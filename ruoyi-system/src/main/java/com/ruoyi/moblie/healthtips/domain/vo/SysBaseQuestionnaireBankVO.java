package com.ruoyi.moblie.healthtips.domain.vo;

import lombok.Data;

import java.util.List;

/**
 * @author yanfeifan
 * @Package com.ruoyi.system.domain.vo
 * @Description
 * @date 2022/3/25 13:39
 */
@Data
public class SysBaseQuestionnaireBankVO {

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
     * 修改选项标记 true 修改/false 不修改
     */
    private Boolean updateOptionsFlag = true;
}
