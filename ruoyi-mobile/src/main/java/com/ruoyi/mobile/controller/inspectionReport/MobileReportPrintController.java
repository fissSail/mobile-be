package com.ruoyi.mobile.controller.inspectionReport;


import com.ruoyi.common.mobile.enums.ReportTypeEnum;
import com.ruoyi.mobile.controller.report.GenerateFile;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @ClassName PrintController
 * @Description TODO
 * @Author Administrator
 * @Date 2022/2/18 16:24
 * @Version 1.0
 **/
@RequestMapping("/print")
@RestController
@Api(tags = "获取用户检查报告")
@RequiredArgsConstructor
@Slf4j
public class MobileReportPrintController implements InitializingBean {


    private final ListableBeanFactory listableBeanFactory;

    private Map<String, GenerateFile> beans;


    /**
     * @return void
     * @Author Administrator
     * @Description 预览报告
     * @Date 9:48 2022/2/25
     * @Param [params, reportType]
     **/
    @PostMapping("/previewReport/{reportType}")
    @ApiOperation("查看体检报告")
    public void previewReport(@RequestBody Map<String, Object> params, @PathVariable("reportType") String reportType, HttpServletResponse response) {
        try {

            // 根据报告类型获取到报告对应的枚举实例
            ReportTypeEnum reportTypeEnum = ReportTypeEnum.getReportType(reportType);
            log.debug("报告对应的枚举实例为{}", reportTypeEnum);
            log.debug("报告对应的实例为{}", beans);
            // 通过报告对应的实例名获取到报告的bean
            GenerateFile generateFile = beans.get(reportTypeEnum.beanName());
            // 预览报告
            generateFile.previewPdfReport(params, response);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public void afterPropertiesSet() {
        beans = listableBeanFactory.getBeansOfType(GenerateFile.class);
    }

}
