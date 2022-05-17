package com.ruoyi.mobile.controller.report;


import com.ruoyi.common.mobile.entity.BaseExamineRecordDO;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.mobile.reserveExamine.mapper.BaseExamineRecordMapper;
import com.ruoyi.common.mobile.enums.ReportTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * @ClassName OldPeopleReport
 * @Description TODO
 * @Author Administrator
 * @Date 2022/4/14 18:16
 * @Version 1.0
 **/
@Component
public class OldPeopleReport extends PersonalReport {
    @Autowired
    private BaseExamineRecordMapper examineRecordMapper;

    @Override
    public void printReport(Map<String, Object> params, HttpServletResponse response) {
        try {

            previewPdfReport(params, response);

            // 需要修改体检记录表中的是否打印报告、报告打印时间等字段
            BaseExamineRecordDO examineRecordVo = new BaseExamineRecordDO();

            // 报告打印信息
            examineRecordVo.setId((String)params.get("examineRecordId"));
            examineRecordVo.setIsReport(1);
            examineRecordVo.setReportPrintTime(DateUtils.getNowDate());

            // 修改安排表或者报告的打印信息
            examineRecordMapper.updateById(examineRecordVo);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void previewPdfReport(Map<String, Object> params, HttpServletResponse response) throws IOException {

            String examineRecordId = (String) params.get("examineRecordId");

            // 生成报告在MinIO服务器上报告名(带路径)
            String reportFilePath = getReportFilePath(examineRecordId);

            // 预览报告
            previewPdfReport(params,response,
                    ReportTypeEnum.OLD_PEOPLE_REPORT.templateType(),
                    reportFilePath,
                    examineRecordId);

    }
}
