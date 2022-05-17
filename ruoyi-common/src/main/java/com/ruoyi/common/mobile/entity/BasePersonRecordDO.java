package com.ruoyi.common.mobile.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 人员档案对象 base_person_record
 *
 * @author zengtao
 * @date 2022-01-27
 */
@ApiModel("人员档案表")
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("base_person_record")
public class BasePersonRecordDO
{
    private static final long serialVersionUID = 1L;

    /** id */

    private String id;

    /** 档案编号 */
    @Excel(name = "档案编号")
    private String code;

    /** 名称 */
    @Excel(name = "名称")
    private String name;

    /** 性别 */
    @Excel(name = "性别")
    private String sex;

    /** 年龄 */
    @Excel(name = "年龄")
    private Integer age;

    /** 婚姻状态 */
    @Excel(name = "婚姻状态")
    private String marryType;

    /** 孕育状态 */
    @Excel(name = "孕育状态")
    private String inoculationType;

    /** 证件类型 */
    @Excel(name = "证件类型")
    private String certificateType;

    /** 证件号 */
    @Excel(name = "证件号")
    private String certificateNo;

    /** 手机号码 */
    @Excel(name = "手机号码")
    private String phone;

    /** 出生日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "出生日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date birthday;

    /** 通信地址 */
    @Excel(name = "通信地址")
    private String address;

    /** 企业 */
    @Excel(name = "企业")
    private String enterprise;

    /** 企业id */
    @Excel(name = "企业id")
    private String enterpriseId;

    /** 部门 */
    @Excel(name = "部门")
    private String department;

    /** 数据来源 */
    @Excel(name = "数据来源")
    private String dataSource;

    /** 预检日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "预检日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date preInspectionDate;

    /** 工种 */
    @Excel(name = "工种")
    private String workType;

    /** 工种id */
    @Excel(name = "工种id")
    private String workTypeId;

    /** 国家 */
    @Excel(name = "国家")
    private String country;

    /** 民族 */
    @Excel(name = "民族")
    private String nation;

    /** 人员类别 */
    @Excel(name = "人员类别")
    private String personType;

    /** 头像地址 */
    @Excel(name = "头像地址")
    private String photoUrl;

    /** 创建人id */
    @Excel(name = "创建人id")
    private Long createId;

    /** 排序 */
    @Excel(name = "排序")
    private Integer sort;

    /** 修改人id */
    @Excel(name = "修改人id")
    private Long updateId;

    /** 删除标志 0存在 1删除 */
    @TableLogic
    private String delFlag;

    /** 驻户id */
    @Excel(name = "驻户id")
    private String residentId;

    /** 版本号 */
    @Excel(name = "版本号")
    private Integer version;

    private Date createTime;
    private String createBy;


}
