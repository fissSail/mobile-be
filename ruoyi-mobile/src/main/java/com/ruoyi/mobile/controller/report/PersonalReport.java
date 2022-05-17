package com.ruoyi.mobile.controller.report;

import com.ruoyi.common.mobile.enums.PersonTypeEnum;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.mobile.reserveExamine.dto.BaseExamineRecordSimpleDTO;
import com.ruoyi.mobile.reserveExamine.mapper.BaseExamineRecordMapper;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @ClassName PersonalReport
 * @Description TODO
 * @Author Administrator
 * @Date 2022/4/15 14:43
 * @Version 1.0
 **/
public abstract class PersonalReport extends GenericReport {

    @Autowired
//    private IBaseExamineRecordService examineRecordService;
    private BaseExamineRecordMapper baseExamineRecordMapper;

    protected String getReportFilePath(String examineRecordId){
        // 根据体检记录查询体检记录信息
        BaseExamineRecordSimpleDTO examineRecordSimpleDto = baseExamineRecordMapper.selectBaseExamineRecordSimpleById(examineRecordId);

        // 设置报告在MinIO上存储的报告名，报告名为
        String reportName = new StringBuffer(examineRecordSimpleDto.getName())
                .append("-")
                .append(examineRecordSimpleDto.getExamineCode())
                .append("-")
                .append(examineRecordSimpleDto.getExamineType())
                .append("报告.pdf").toString();
        // 设置报告在MinIO上存储的路径，路径格式 个人体检为/personal/yyyy/MM/dd，团体体检为/group/yyyy/MM/dd/公司名
        StringBuffer reportPath = new StringBuffer(SEPARATOR);

        if (PersonTypeEnum.PERSONAL.getVal().equals(examineRecordSimpleDto.getPersonType())) {
            reportPath.append("personal");
            reportPath.append(SEPARATOR);
            reportPath.append(DateUtils.datePath(examineRecordSimpleDto.getExamineDate()));
        } else {
            reportPath.append("group");
            reportPath.append(SEPARATOR);
            reportPath.append(DateUtils.datePath(examineRecordSimpleDto.getExamineDate()));
            reportPath.append(SEPARATOR);
            reportPath.append(examineRecordSimpleDto.getEnterprise());
        }

        return reportPath.append(SEPARATOR).append(reportName).toString();
    }
}
