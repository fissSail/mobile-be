package com.ruoyi.common.mobile.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.util.Date;

@ApiModel("体检记录表")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("base_examine_record")
public class BaseExamineRecordDO
{
    private static final long serialVersionUID = 1L;

    /** id */
    private String id;

    /** 人员档案id */
    @Excel(name = "人员档案id")
    private String personRecordId;

    /** 体检编号 */
    @Excel(name = "体检编号")
    private String examineCode;

    /** 体检类型id */
    @Excel(name = "体检类型id")
    private String examineTypeId;

    /** 登记时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "登记时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date registerTime;

    /** 体检日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "体检日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date examineDate;

    /** 到检时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "到检时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date arrivalTime;

    /** 结束时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "结束时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date endTime;

    /** 企业 */
    @Excel(name = "企业")
    private String enterprise;

    /** 企业id */
    @Excel(name = "企业id")
    private String enterpriseId;

    /** 人员类别 */
    @Excel(name = "人员类别")
    private String personType;

    /** 体检状态 1:登记,2:体检中,3:总检,4:审核 */
    @Excel(name = "体检状态 1:登记,2:体检中,3:总检,4:审核")
    private String examineStatus;

    /** 是否交表 1是0否 */
    @Excel(name = "是否交表 1是0否")
    private Integer isForm;

    /** 是否删除 1是0否 */
    @Excel(name = "是否删除 1是0否")
    private Integer isDelete;

    /** 是否复检 1是0否 */
    @Excel(name = "是否复检 1是0否")
    private Integer isRecheck;

    /** 是否完成体检 1是0否 */
    @Excel(name = "是否完成体检 1是0否")
    private Integer isEndExamine;

    /** 是否体检 1是0否 */
    @Excel(name = "是否体检 1是0否")
    private Integer isExamine;

    /** 是否收费 1是0否 */
    @Excel(name = "是否收费 1是0否")
    private Integer isCharge;

    /** 是否问诊 1是0否 */
    @Excel(name = "是否问诊 1是0否")
    private Integer isConsultation;

    /** 上次体检编号 */
    @Excel(name = "上次体检编号")
    private String oldExamineCode;

    /** 是否取报告 1是0否 */
    @Excel(name = "是否取报告 1是0否")
    private Integer isReport;

    /** 安排表打印时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "安排表打印时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date schedulePrintTime;

    /** 安排表打印人 */
    @Excel(name = "安排表打印人")
    private String schedulePrintBy;

    /** 安排表打印人id */
    @Excel(name = "安排表打印人id")
    private Long schedulePrintId;

    /** 条码打印时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "条码打印时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date barcodePrintTime;

    /** 条码打印人 */
    @Excel(name = "条码打印人")
    private String barcodePrintBy;

    /** 个人报告打印时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "个人报告打印时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date reportPrintTime;

    /** 条码打印人id */
    @Excel(name = "条码打印人id")
    private Long barcodePrintId;

    /** 主检时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "主检时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date mainInspectTime;

    /** 体检结果 */
    @Excel(name = "体检结果")
    private String examineResult;

    /** 本次职业体检定性结论 */
    @Excel(name = "本次职业体检定性结论")
    private String qualitativeConclusion;

    /** 职业体检结论 */
    @Excel(name = "职业体检结论")
    private String conclusion;

    /** 保健建议 */
    @Excel(name = "保健建议")
    private String healthcare;

    /** 职业体检建议 */
    @Excel(name = "职业体检建议")
    private String proposal;

    /** 岗位状态 */
    @Excel(name = "岗位状态")
    private String postType;

    /** 诊断内容 */
    @Excel(name = "诊断内容")
    private String diagnosis;

    /** 合计金额 */
    @Excel(name = "合计金额")
    private BigDecimal amountTotal;

    /** 实收金额 */
    @Excel(name = "实收金额")
    private BigDecimal amountPaid;

    /** 折扣 */
    @Excel(name = "折扣")
    private BigDecimal discount;

    /** 折扣审核人id */
    @Excel(name = "折扣审核人id")
    private String discountId;

    /** 折扣审核人 */
    @Excel(name = "折扣审核人")
    private String discountBy;

    /** 是否加急 1是0否 */
    @Excel(name = "是否加急 1是0否")
    private Integer isUrgent;

    /** 收费类型 */
    @Excel(name = "收费类型")
    private String chargeType;

    /** 工种 */
    @Excel(name = "工种")
    private String workType;

    /** 工种id */
    @Excel(name = "工种id")
    private String workTypeId;

    /** 接害工龄年 */
    @Excel(name = "接害工龄年")
    private Integer expWorkYear;

    /** 接害工龄月 */
    @Excel(name = "接害工龄月")
    private Integer expWorkMonth;

    /** 总工龄年 */
    @Excel(name = "总工龄年")
    private Integer totalWorkYear;

    /** 总工龄月 */
    @Excel(name = "总工龄月")
    private Integer totalWorkMonth;

    /** 危害因素 */
    @Excel(name = "危害因素")
    private String hazardFactors;

    /** 导入批号 */
    @Excel(name = "导入批号")
    private String importNo;

    /** 从业人员体检结果是否合格 1是0否 */
    @Excel(name = "从业人员体检结果是否合格 1是0否")
    private Integer isQualified;

    /** 从业人员体检结果时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "从业人员体检结果时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date qualifiedTime;

    /** 排序 */
    @Excel(name = "排序")
    private Integer sort;

    /** 删除标志 0存在 1删除 */
    private String delFlag;

    /** 创建人id */
    @Excel(name = "创建人id")
    private Long createId;

    /** 驻户id */
    @Excel(name = "驻户id")
    private String residentId;

    /** 版本号 */
    @Excel(name = "版本号")
    private Integer version;

    /** 修改人id */
    @Excel(name = "修改人id")
    private Long updateId;

    /** 来检人员在公司所属的部门 */
    @Excel(name = "来检人员在公司所属的部门")
    private String dept;

    private String createBy;

    private Date  createTime;
    private Date  updateTime;

}
