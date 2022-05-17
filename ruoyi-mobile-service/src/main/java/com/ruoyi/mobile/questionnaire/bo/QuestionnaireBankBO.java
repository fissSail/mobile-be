package com.ruoyi.mobile.questionnaire.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * @author yanfeifan
 * @Package com.ruoyi.mobile.bo
 * @Description 答题选项实体
 * @date 2022/3/24 19:42
 */
@Data
public class QuestionnaireBankBO {

    /**
     * 问题表id
     */
    @ApiModelProperty(value = "问卷题目id", required = true)
    @NotBlank(message = "问卷题目id不能为空")
    private String questionnaireBankId;

    /**
     * 问题选项id
     */
    @ApiModelProperty(value = "问题选项id集合", required = true)
    private List<String> questionnaireAnswersIdList;
}
