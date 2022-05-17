package com.ruoyi.common.mobile.enums;

/**
 * @Author Administrator
 * @Description 报告模板枚举
 *  GUIDANCE_SHEET：安排指引单
 *  OCCUPATION_GROUP_REPORT：职业健康检查团检报告
 *  OCCUPATION_PERSONAL_REPORT：职业健康检查个人报告
 *  HEALTH_REPORT：健康检查报告
 *  OLD_PEOPLE_REPORT：老年人体检报告
 *  JOBHOLDER_PHYSICAL_REPORT：从业人员体检报告
 *
 *  目前没有做健康检查、老年人体检、从业人员体检报告模板，暂时都以职业健康检查个人报告的模板显示，后续会改回来
 * @Date 15:14 2022/4/14
 * @Param
 * @return
 **/
public enum ReportTypeEnum {
    GUIDANCE_SHEET("1","guidSheetReport","1"),
    OCCUPATION_GROUP_REPORT("2","occupationGroupReport","2"),
    OCCUPATION_PERSONAL_REPORT("3","occupationPersonalReport","3"),
    HEALTH_REPORT("4","healthReport","3"),
    OLD_PEOPLE_REPORT("5","oldPeopleReport","3"),
    JOBHOLDER_PHYSICAL_REPORT("6","jobholderPhysicalReport","3");

    ReportTypeEnum(){};


    ReportTypeEnum(String reportType, String beanName, String templateType) {
        this.reportType = reportType;
        this.beanName = beanName;
        this.templateType = templateType;
    }

    public String reportType() {
        return reportType;
    }

    public String beanName() {
        return beanName;
    }

    public String templateType(){
        return templateType;
    }

    public static ReportTypeEnum getReportType(String reportType){
        for (ReportTypeEnum reportTypeEnum : ReportTypeEnum.values()) {
            if (reportTypeEnum.reportType.equals(reportType)) {
                return reportTypeEnum;
            }
        }

        return null;
    }


    // 报告类型的代码
    private String reportType;
    // 报告对应的类bean的name
    private String beanName;
    // 报告对应的模板类型
    private String templateType;
}
