package com.ruoyi.common.mobile.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 问卷问题表
 *
 * @author yff
 * @date 2022-03-24 10:29:06
 */
@Data
@TableName("base_questionnaire_bank")
public class BaseQuestionnaireBankDO {

    /**
     * id
     */
    @TableId
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
     * 删除标志 0存在 1删除
     */
    @TableLogic
    private String delFlag;

    /**
     * 创建者
     */
    private String createBy;

    /**
     * 创建人id
     */
    private Long createId;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新者
     */
    private String updateBy;

    /**
     * 修改人id
     */
    private Long updateId;

    /**
     * 更新时间
     */
    private Date updateTime;

}
