package com.ruoyi.common.mobile.entity;

import java.math.BigDecimal;
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
 * 体检记录组合项目关联对象 base_examine_record_combi_project
 * 
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("base_examine_record_combi_project")
@Builder
public class BaseExamineRecordCombiProjectDO
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

    /** 套餐名称 */
    @Excel(name = "套餐名称")
    private String examinePackageName;

    /** 组合项目id */
    @Excel(name = "组合项目id")
    private String combiProjectId;

    /** 组合项目名称 */
    @Excel(name = "组合项目名称")
    private String combiProjectName;

    /** 折扣 */
    @Excel(name = "折扣")
    private BigDecimal discount;

    /** 价格 */
    @Excel(name = "价格")
    private BigDecimal price;

    /** 实收金额 */
    @Excel(name = "实收金额")
    private BigDecimal amountPaid;

    /** 收费类型 */
    @Excel(name = "收费类型")
    private String chargeType;

    /** 收费时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "收费时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date chargeTime;

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
    /**
     * 是否弃检 1是0否'
     */
    private Integer isAbandon;
    /**
     * 退款标志 1是0否'
     */
    private Integer isRefund;

    /** 创建者 */
    private String createBy;

    private String createId;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /** 更新者 */
    private String updateBy;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
}
