package com.ruoyi.mobile.controller.report;


import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;


/**
 * @Author wxx
 * @Description 生成报告的通用接口
 * @Date 13:32 2022/4/14
 * @Param
 * @return
 **/
public interface GenerateFile {
    
    /**
     * @Author wxx
     * @Description 生成报告
     * @Date 13:55 2022/4/14
     * @Param [params]
     * @return void
     **/
    public default void generateReportFile(Map<String,Object> params, HttpServletResponse response){};

    /**
     * @Author wxx
     * @Description 打印报告
     * @Date 15:51 2022/4/14
     * @Param [params, response]
     * @return void
     **/
    void printReport(Map<String,Object> params, HttpServletResponse response);

    /**
     * @Author wxx
     * @Description 以PDF的格式预览报告
     * @Date 15:52 2022/4/14
     * @Param [params, response]
     * @return void
     **/
    void previewPdfReport(Map<String,Object> params, HttpServletResponse response) throws IOException;

}
