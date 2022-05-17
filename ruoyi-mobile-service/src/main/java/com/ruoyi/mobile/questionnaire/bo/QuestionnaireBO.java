package com.ruoyi.mobile.questionnaire.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author yanfeifan
 * @Package com.ruoyi.mobile.bo
 * @Description 提交问卷实体
 * @date 2022/3/24 10:00
 */
@Data
public class QuestionnaireBO {

    /**
     * 答卷人
     */
    @ApiModelProperty(value = "答卷人名称", required = true)
    private String userName;

    /**
     * 答卷人id
     */
    @ApiModelProperty(value = "答卷人id", required = true)
    @NotNull(message = "答卷人id不能为空")
    private String userId;

    /**
     * 答题选项
     */
    @ApiModelProperty(value = "答题选项list", required = true)
    private List<QuestionnaireBankBO> questionnaireBankBOList;

}
