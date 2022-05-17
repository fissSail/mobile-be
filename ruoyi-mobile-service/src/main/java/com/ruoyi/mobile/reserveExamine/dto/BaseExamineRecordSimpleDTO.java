package com.ruoyi.mobile.reserveExamine.dto;

import lombok.Data;

import java.util.Date;

/**
 * @ClassName BaseExamineRecordSimpleDto
 * @Description TODO
 * @Author Administrator
 * @Date 2022/4/15 14:12
 * @Version 1.0
 **/
@Data
public class BaseExamineRecordSimpleDTO {
    // 体检编号
    private String examineCode;
    // 人员类型
    private String personType;
    // 姓名
    private String name;
    // 公司名称
    private String enterprise;
    // 体检日期
    private Date examineDate;
    // 体检类型
    private String examineType;


}
