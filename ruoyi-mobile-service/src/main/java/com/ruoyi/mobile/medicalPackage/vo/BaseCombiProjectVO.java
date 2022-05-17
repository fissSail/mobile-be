package com.ruoyi.mobile.medicalPackage.vo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.ruoyi.common.annotation.Excel;

/**
 * 组合项目对象 base_combi_project
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BaseCombiProjectVO
{

    /** ID */
    private String id;

    /** 科室 */
    @Excel(name = "科室")
    private String departmentId;

    /** 项目类型 */
    @Excel(name = "项目类型")
    private String projectType;

    /** 项目名称 */
    @Excel(name = "项目名称")
    private String projectName;

    /** 项目编码 */
    @Excel(name = "项目编码")
    private String projectCode;

    /** 是否收费 */
    @Excel(name = "是否收费")
    private Integer isCharge;

    /** 收费类型 */
    @Excel(name = "收费类型")
    private String chargeType;

    /** 输入码 */
    private String inputCode;

    /** 折扣 */
    @Excel(name = "折扣")
    private BigDecimal discount;

    /** 价格 */
    @Excel(name = "价格")
    private BigDecimal price;

    /** 成本价 */
    @Excel(name = "成本价")
    private BigDecimal costPrice;

    /** 样本类型 */
    private String sampleType;

    /** 部位 */
    private String position;

    /** 试管类型 */
    private String tubeType;

    /** 人数 */
    private Integer number;

    /** 年龄上限 */
    private Integer upperAge;

    /** 年龄下限 */
    private Integer lowerAge;

    /** 项目标志 */
    private String projectMark;

    /** 是否外送 */
    private Integer isDelivery;

    /** 是否生成申请号 */
    private Integer isApply;

    /** 适用性别 */
    @Excel(name = "适用性别")
    private String sex;

    /** 是否妇检 */
    private Integer isWomanExamine;

    /** 是否餐前 */
    private Integer isBeforeMeal;

    /** 是否抽血 */
    private Integer isDrawBlood;

    /** 医院编码 */
    private String hospitalCode;

    /** 紧急备注 */
    private String criticalRemark;

    /** 注意事项 */
    private String matter;

    /** 排序 */
    @Excel(name = "排序")
    private Integer sort;

    /** 删除标志 */
    private String delFlag;

    /** 主户 */
    @Excel(name = "主户")
    private String residentId;

    /** 版本号 */
    private Integer version;
    /** 搜索值 */
    private String searchValue;

    /** 创建者 */
    private String createBy;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /** 更新者 */
    private String updateBy;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    /** 备注 */
    private String remark;

    @ApiModelProperty("体检项目的体检项")
    private List<ExamineProjectNameDetailsVO> examineProjectNameDetailsVOS;
}
