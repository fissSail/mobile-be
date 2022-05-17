package com.ruoyi.mobile.reserveExamine.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.mobile.medicalPackage.vo.ExamineProjectNameDetailsVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

@ApiModel("用户体检列表")
@Data
public class ExamineRecordVO {

    @ApiModelProperty("套餐名称")
    private String examinePackageName;
    @ApiModelProperty("组合项目名称")
    private String combiProjectName;
    @ApiModelProperty("组合项目id")
    private String combiProjectId;
    @ApiModelProperty("体检记录id")
    private String examineRecordId;
    @ApiModelProperty("是否交表 1是0否   判断是否过期（到了日期没有交表就是过期）")
    private String isForm;
    @ApiModelProperty("预约体检日期")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date examineDate;
    @ApiModelProperty("是否过期  1是 0否")
    private String isExpired;
    @ApiModelProperty("是否取消预约  1是 0否")
    private Integer isDelete;
    @ApiModelProperty("科室名称")
    private String deptName;
    @ApiModelProperty("是否加急  1是0否")
    private String isExpedited;
    @ApiModelProperty("是否复检  1是0否")
    private String isRecheck;
    @ApiModelProperty("体检类型")
    private String examineType;
    @ApiModelProperty("体检套餐id")
    private String baseExaminePackageId;
    @ApiModelProperty("体检项目的体检项")
    private List<ExamineProjectNameDetailsVO> examineProjectNameDetailsVOS;

}
