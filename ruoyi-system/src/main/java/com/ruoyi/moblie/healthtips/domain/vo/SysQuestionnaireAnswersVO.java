package com.ruoyi.moblie.healthtips.domain.vo;

import lombok.Data;

/**
 * @author yanfeifan
 * @Package com.ruoyi.system.domain.bo
 * @Description 问卷题目选项传输实体
 * @date 2022/3/25 11:29
 */
@Data
public class SysQuestionnaireAnswersVO {

    /**
     * 选项id
     */
    private String id;

    /**
     * 选项
     */
    private String questionnaireOption;

    /**
     * 套餐id
     */
    private String setMealId;

    /**
     * 问题表id
     */
    private String questionnaireBankId;

    /**
     * 排序
     */
    private Integer sort;
}
