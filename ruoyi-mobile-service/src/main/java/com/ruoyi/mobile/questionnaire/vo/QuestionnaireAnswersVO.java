package com.ruoyi.mobile.questionnaire.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author yanfeifan
 * @Package com.ruoyi.mobile.vo
 * @Description 问题选项返回实体
 * @date 2022/3/23 15:24
 */
@Data
public class QuestionnaireAnswersVO {

    /**
     * 选项id
     */
    @ApiModelProperty(value = "选项id")
    private String id;

    /**
     * 选项
     */
    @ApiModelProperty(value = "选项内容")
    private String questionnaireOption;

    /**
     * 问题表id
     */
    @ApiModelProperty(value = "问卷问题id")
    private String questionnaireBankId;

    /**
     * 排序
     */
    @ApiModelProperty(value = "排序")
    private Integer sort;

}
