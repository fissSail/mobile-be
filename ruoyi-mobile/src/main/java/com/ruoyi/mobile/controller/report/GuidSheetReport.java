package com.ruoyi.mobile.controller.report;


import com.ruoyi.common.mobile.entity.BaseExamineRecordDO;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.mobile.reserveExamine.mapper.BaseExamineRecordMapper;
import com.ruoyi.common.mobile.enums.ReportTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * @ClassName GuidSheetReport
 * @Description TODO
 * @Author Administrator
 * @Date 2022/4/14 16:14
 * @Version 1.0
 **/
@Component
public class GuidSheetReport extends GenericReport {

    @Autowired
    private BaseExamineRecordMapper baseExamineRecordMapper;

    /**
     * @Author 打印安排表
     * @Description //TODO
     * @Date 16:19 2022/4/14
     * @Param [params, response]
     * @return void
     **/
    @Override
    public void printReport(Map<String, Object> params, HttpServletResponse response) {
        try {
            // 指引单不需要存储到MinIO服务器上
            setSaveToDisk(false);
            previewPdfReport(params,response, ReportTypeEnum.GUIDANCE_SHEET.templateType(), null, null);

            // 需要修改体检记录表中的是否打印报告、报告打印时间等字段
            BaseExamineRecordDO examineRecordVo = new BaseExamineRecordDO();

            // 安排表打印信息
            examineRecordVo.setId((String)params.get("examineRecordId"));
            examineRecordVo.setSchedulePrintBy(SecurityUtils.getUsername());
            examineRecordVo.setSchedulePrintId(SecurityUtils.getUserId());
            examineRecordVo.setSchedulePrintTime(DateUtils.getNowDate());

            // 修改安排表或者报告的打印信息
            baseExamineRecordMapper.updateById(examineRecordVo);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void previewPdfReport(Map<String, Object> params, HttpServletResponse response) {

    }
}
