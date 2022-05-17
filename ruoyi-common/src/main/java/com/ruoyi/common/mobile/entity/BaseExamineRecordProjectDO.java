package com.ruoyi.common.mobile.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 体检记录项目关联对象 base_examine_record_project
 * 

 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("base_examine_record_project")
public class BaseExamineRecordProjectDO

{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private String id;

    /** 体检记录id */
    @Excel(name = "体检记录id")
    private String examineRecordId;

    /** 科室id */
    @Excel(name = "科室id")
    private String departmentId;

    /** 套餐id */
    @Excel(name = "套餐id")
    private String examinePackageId;

    /** 组合项目id */
    @Excel(name = "组合项目id")
    private String combiProjectId;

    /** 组合项目名称 */
    @Excel(name = "组合项目名称")
    private String combiProjectName;

    /** 体检项目id */
    @Excel(name = "体检项目id")
    private String projectId;

    /** 体检项目名称 */
    @Excel(name = "体检项目名称")
    private String projectName;

    /** 项目单位 */
    @Excel(name = "项目单位")
    private String projectUnit;

    /** 是否检查 1是0否 */
    @Excel(name = "是否检查 1是0否")
    private Integer isInspect;

    /** 检查人id */
    @Excel(name = "检查人id")
    private String inspectId;

    /** 检查人 */
    @Excel(name = "检查人")
    private String inspectBy;

    /** 检查时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "检查时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date inspectTime;

    /** 是否弃检 1是0否 */
    @Excel(name = "是否弃检 1是0否")
    private Integer isAbandon;

    /** 弃检人 */
    @Excel(name = "弃检人")
    private String abandonBy;

    /** 弃检时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "弃检时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date abandonTime;

    /** 是否异常 1是0否 */
    @Excel(name = "是否异常 1是0否")
    private Integer isAbnormal;

    /** 是否危急 1是0否 */
    @Excel(name = "是否危急 1是0否")
    private Integer isCritical;

    /** 体检结果 */
    @Excel(name = "体检结果")
    private String examineResult;

    /** 排序 */
    @Excel(name = "排序")
    private Integer sort;

    /** 删除标志 0存在 1删除 */
    private String delFlag;

    /** 驻户id */
    @Excel(name = "驻户id")
    private String residentId;

    /** 版本号 */
    @Excel(name = "版本号")
    private Integer version;
    /** 创建者 */
    private String createBy;
    private String createId;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /** 更新者 */
    private String updateBy;
    private String updateId;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;


}
