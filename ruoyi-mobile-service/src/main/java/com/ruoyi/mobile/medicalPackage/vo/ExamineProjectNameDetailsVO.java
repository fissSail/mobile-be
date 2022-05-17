package com.ruoyi.mobile.medicalPackage.vo;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("项目下面的体检项")
public class ExamineProjectNameDetailsVO {
    private String combiProjectId;
    private String projectName;
    private String projectId;
    private String projectUnit;
    private String projectCode;
    private String isPrint;

}
