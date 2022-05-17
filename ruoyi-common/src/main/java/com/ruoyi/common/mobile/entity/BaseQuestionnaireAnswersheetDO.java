package com.ruoyi.common.mobile.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * 问卷答卷表
 *
 * @author yff
 * @date 2022-03-24 10:29:06
 */
@Data
@TableName("base_questionnaire_answersheet")
public class BaseQuestionnaireAnswersheetDO {

    /**
     *
     */
    @TableId
    private String id;

    /**
     * 答卷人
     */
    private String userName;

    /**
     * 答卷人id
     */
    private String userId;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createTime;

    /**
     * 备注
     */
    private String remark;

    /**
     * 删除标志 0存在 1删除
     */
    @TableLogic
    private String delFlag;

}
