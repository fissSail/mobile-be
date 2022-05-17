package com.ruoyi.mobile.controller.report;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ruoyi.common.mobile.entity.BaseExamineReportUrlDO;
import com.ruoyi.common.utils.DateUtil;
import com.ruoyi.mobile.reserveExamine.mapper.BaseExamineReportUrlMapper;
import com.ruoyi.system.service.ISysTemplateService;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.util.JRLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.*;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @ClassName GenericReport
 * @Description 生成报告的通用类
 * @Author Administrator
 * @Date 2022/4/14 13:36
 * @Version 1.0
 **/
public abstract class GenericReport implements GenerateFile {
    protected static final Logger log = LoggerFactory.getLogger(GenericReport.class);
    protected static final String SEPARATOR = "/";

    @Autowired
    private DataSource dataSource;

    @Autowired
    private ISysTemplateService templateService;
    @Autowired
    private BaseExamineReportUrlMapper examineReportUrlMapper;

    // 报告模板在服务器上的路径
    @Value("${ruoyi.profile}")
    private String basicFilePath;

    // 报告是否存储到服务器上
    @Value("${ruoyi.report.save-to-disk}")
    private boolean isSaveToDisk;

    @Value("${ruoyi.report.tempDir}")
    private String tempDir;

    /*@Autowired
    private MinioBucketUtils reports;*/

    public void setSaveToDisk(boolean saveToDisk) {
        isSaveToDisk = saveToDisk;
    }

    /**
     * @return void
     * @Author wxx
     * @Description 从MinIO服务器上读取相应的文件返回到前端
     * @Date 13:58 2022/4/15
     * @Param [reportUrl：需要读取的文件地址
     * response]
     **/
    private void readFileFromMinIO(String reportUrl, HttpServletResponse response) throws IOException {
        OutputStream outputStream = null;
        try {
            // 在线预览pdf
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "inline;");

            outputStream = response.getOutputStream();
            //设置导出的字符编码
            response.setContentType("application/pdf");
            response.setHeader("content-disposition", "attachment:filename=" + new String(reportUrl.substring(reportUrl.lastIndexOf(SEPARATOR) + 1).getBytes(), "ISO-8859-1"));
            //读取文件内容，并返回给前端
            InputStream inputStream = null;// reports.getFileStream(reportUrl);
            byte[] bytes = new byte[1024];
            int length = -1;
            while ((length = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, length);
            }
            outputStream.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (outputStream != null) {
                outputStream.close();
            }
        }
    }


    /**
     * @return void
     * @Author wxx
     * @Description 预览报告，如果报告已经存在，则直接从MinIO服务器上读取报告，否则需要生成报告，并进行存储
     * @Date 14:00 2022/4/15
     * @Param [params：生成报告需要用到的参数
     * response：HttpServletResponse对象
     * templateType：模板类型
     * reportName：报告名称
     * reportPath：报告在MinIO上的地址
     * examineNo： 体检记录ID或者导入批号]
     **/
    public void previewPdfReport(Map<String, Object> params, HttpServletResponse response, String templateType, String reportFilePath, String examineNo) throws IOException {
        // 查询体检编号或者导入批号是否已经存在报告，如果存在则直接将读取该报告
        BaseExamineReportUrlDO reportUrl = examineReportUrlMapper.selectOne(new QueryWrapper<BaseExamineReportUrlDO>()
                .eq("examine_record_id", examineNo));
        if (reportUrl == null) {
            // 生成JasperPrint对象
            JasperPrint jasperPrint = generateJasperPrint(params, templateType);
            // 将JasperPrint对象转换为PDF文件，并返回前端，可以存储到MinIO服务器上
            if (null != jasperPrint) {
                toPdfReport(jasperPrint, response, reportFilePath, examineNo);
            }
        } else {
            // 如果存在报告，则直接从MinIO服务器上读取报告，并且将其返回给前端
            readFileFromMinIO(reportUrl.getExamineReportUrl(), response);
        }
    }


    /**
     * @return void
     * @Author wxx
     * @Description 将JasperPrint对象转为为PDF流，并返回给前端，如果需要保存到MinIO服务器上，则将报告保存到MinIO服务器上
     * @Date 11:47 2022/4/15
     * @Param [
     * jasperPrint： JapserPrint对象
     * response：HttpServletResponse对象，将JapserPrint对象转换后的PDF文件一流的形式传输回前端
     * reportName: 报告存储在MinIO服务器上的名称
     * reportPath：报告存储在MinIO服务器上的路径
     * examineNo：报告对应的体检记录ID(个人报告)或者导入批号(团体报告)
     **/
    private void toPdfReport(JasperPrint jasperPrint, HttpServletResponse response, String reportFilePath, String examineNo) throws IOException {
        OutputStream outputStream = null;
        try {
            // 在线预览pdf
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "inline;");

            outputStream = response.getOutputStream();
//            //设置导出的字符编码
            response.setContentType("application/pdf");
            String reportName = null;
            if (null == reportFilePath) {
                reportName = "";
            } else {
                reportName = reportFilePath.substring(reportFilePath.lastIndexOf(SEPARATOR) + 1);
            }
            response.setHeader("content-disposition", "attachment:filename=" + new String(reportName.getBytes(), "ISO-8859-1"));
            //3.打印
            JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
            outputStream.flush();

            // 将报告存储到服务器
            if (isSaveToDisk) {
                // 将报告保存到MinIO服务器上
                saveReportToDisk(jasperPrint, reportFilePath);

                // 将报告信息存储到数据库
                BaseExamineReportUrlDO examineReportUrlVo = new BaseExamineReportUrlDO();
                examineReportUrlVo.setExamineRecordId(examineNo);
                examineReportUrlVo.setExamineReportUrl(reportFilePath);
                examineReportUrlMapper.insert(examineReportUrlVo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (outputStream != null) {
                outputStream.close();
            }
        }
    }

    /**
     * @return void
     * @Author wxx
     * @Description 将报告保存到本地磁盘
     * @Date 8:58 2022/4/15
     * @Param [jasperPrint, reportName]
     **/
    private void saveReportToDisk(JasperPrint jasperPrint, String reportFilePath) throws Exception {
        // 临时存储报告的路径
        File tempFileDir = new File(tempDir);
        if (!tempFileDir.exists()) {
            tempFileDir.mkdirs();
        }
        String reportName = reportFilePath.substring(reportFilePath.lastIndexOf(SEPARATOR) + 1);
        String tempFileName = tempDir + SEPARATOR + reportName;

        // 将JasperPrint对象转换为pdf文件并存储到本地
        JRExporter exporter = new JRPdfExporter();
        exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, tempFileName);
        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
        exporter.exportReport();

        // 将本地的pdf报告上传到MinIO服务器上
        InputStream inputStream = new FileInputStream(tempFileName);
        //reports.putFile(reportFilePath, inputStream);

        new File(tempFileName).delete();
    }

    /**
     * @return net.sf.jasperreports.engine.JasperPrint
     * @Author wxx
     * @Description 生成JasperPrint的通用方法
     * @Date 14:54 2022/4/14
     * @Param [params, templateType]
     **/
    private JasperPrint generateJasperPrint(Map<String, Object> params, String templateType) throws IOException {
        // 传递参数打印时间(大写的表示形式)
        params.put("printDate", DateUtil.dataToUpper(new Date()));
        // 传递的头像的上传目录
        params.put("profilePicUrl", basicFilePath + File.separator);
        JasperPrint jasperPrint = null;
        InputStream inputStream = null;
        try {
            // 查询报告模板
            List<String> urls = templateService.selectUrlByTemplateType(templateType);
            StringBuffer urlBuffer = new StringBuffer(urls.get(0));
            int index = urlBuffer.indexOf("/profile");
            String url = urlBuffer.substring(index + "/profile".length());
            String reportFileName = basicFilePath + url;

            // 获取打印报告的输入流
            inputStream = new FileInputStream(reportFileName);
            // 加载模板
            JasperReport jasperReport = (JasperReport) JRLoader.loadObject(inputStream);
            // 如果是通过Java程序传参到模板中，则需要使用new JREmptyDataSource()
            // 如果模板中的数据是通过DataSet连接数据库的则需要使用dataSource.getConnection()
            jasperPrint = JasperFillManager.fillReport(jasperReport, params, dataSource.getConnection());

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }

        return jasperPrint;
    }

}
