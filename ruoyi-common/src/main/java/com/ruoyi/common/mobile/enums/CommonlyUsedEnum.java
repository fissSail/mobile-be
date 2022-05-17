package com.ruoyi.common.mobile.enums;

/**
 * @date: 2022/3/10  9:07
 * @author: 1623394432@qq.com
 */
public enum CommonlyUsedEnum {
    /**
     * 支付类型
     */
    AT_OWN_EXPENSE(1, "自费"), FREE(2, "免费"), GROUP(3, "团体"),
    /**
     * 是否收费
     */
    CHARGED(1, "已收费"), NOT_CHARGED(0, "未收费"),
    /**
     * 删除标识
     */
    DELETE(1, "删除"), NOT_DELETE(0, "存在"),
    /**
     * 是否退款
     */
    REFUND(1, "已退款"), NOT_REFUND(0, "未退款"),

    /**
     * 男女
     *
     * @param value
     * @param msg
     */
    MALE(1, "男"), FEMALE(2, "女"), UNIVERSAL(3, "通用"),
    /**
     * 降序和升序
     */
    DROP(1, "降"), RISE(2, "升"),
    /**
     * 状态
     */
    ENABLE(1, "启用"), DISABLED(0, "禁用"),

    /**
     * 问卷发布状态
     */
    QUESTIONNAIRE_ISSUE(0, "发布"), QUESTIONNAIRE_NOT_ISSUE(1, "暂存"),

    /**
     * 问卷题目类型
     */
    QUESTIONNAIRE_MULTIPLE(1,"多选"),QUESTIONNAIRE_SINGLE(0,"单选"),

    /**
     * 问题是否必选
     */
    QUESTIONNAIRE_REQUIRED(0,"必选"),QUESTIONNAIRE_NO_REQUIRED(0,"不必选"),

    /**
     * 通知公告类型
     */
    NOTICE(0,"通知公告"),HEALTH_TIPS(1,"健康小常识"),

    /**
     * 体检
     */
    PENDING_MEDICAL_EXAMINATION(1,"待体检") ,  EXPIRED(2,"已过期"),
    /**
     * 超过和未超过
     */
    EXCEED(1,"超过"),NOT_EXCEED(0,"未超过"),
    /**
     * 项目预约人数满
     */
    NOT_FULL(1,"忽律满"), FULL(0,"满"),
    /**
     * 体检转态
     */
    END_EXAMINE(1,"完成体检"),NOT_END_EXAMINE(0,"未完成"),
    /**
     * 删除套餐选项
     */
    DELETE_ALL(1,"全删"), DELETE_PART(0,"部分");

    CommonlyUsedEnum(Integer value, String msg) {
        this.val = value;
        this.msg = msg;
    }

    public Integer val() {
        return val;
    }

    public String msg() {
        return msg;
    }

    private Integer val;
    private String msg;
}
