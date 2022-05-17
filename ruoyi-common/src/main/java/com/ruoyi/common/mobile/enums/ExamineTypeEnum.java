package com.ruoyi.common.mobile.enums;

public enum ExamineTypeEnum {


    HEALTH_CHECKUP("004", "健康体检"),
    GERIATRIC_MEDICAL_EXAMINATION("003", "老年体检"),
    EMPLOYEE_PHYSICAL_EXAMINATION("001", "从业人员体检"),
    OCCUPATIONAL_HEALTH_CHECK("002", "职业健康检查");

    private String val;
    private String msg;

    ExamineTypeEnum(String val, String msg) {
        this.msg = msg;
        this.val = val;
    }

    public String getMsg() {
        return msg;
    }

    public String getVal() {
        return val;
    }
}
