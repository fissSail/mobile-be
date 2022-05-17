package com.ruoyi.common.mobile.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;

@ApiModel("体检套餐对象")
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("base_examine_package")
public class BaseExaminePackageDO{
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    private String id;

    /**
     * 体检类型
     */
    @Excel(name = "体检类型")
    private String examineTypeId;

    /**
     * 危害因素
     */
    @Excel(name = "危害因素")
    private String hazardFactorsId;

    /**
     * 是否为危害因素套餐
     */
    @Excel(name = "是否为危害因素套餐 ")
    private Integer isHazard;

    /**
     * 在岗状态
     */
    @Excel(name = "在岗状态")
    private String postType;

    /**
     * 套餐名称
     */
    @Excel(name = "套餐名称")
    private String name;

    /**
     * 输入码
     */
    private String inputCode;

    /**
     * 应收金额
     */
    @Excel(name = "应收金额")
    private BigDecimal amountReceivable;

    /**
     * 折扣
     */
    @Excel(name = "折扣")
    private BigDecimal discount;

    /**
     * 适用性别
     */
    private String sex;

    /**
     * 适用婚别
     */
    private String marryType;

    /**
     * 适用最大年龄
     */
    private Integer maxAge;

    /**
     * 适用最小年龄
     */
    private Integer minAge;

    /**
     * 注意事项
     */
    private String matter;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 删除标志
     */
    private String delFlag;

    /**
     * 驻户
     */
    private String residentId;

    /**
     * 版本号
     */
    private Integer version;


}
