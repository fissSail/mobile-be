package com.ruoyi.moblie.medicalPackage.domain.vo;

import com.ruoyi.common.annotation.Excel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;
import java.util.List;

/**
 * 体检套餐对象 BaseExaminePackageOption
 * 
 * @author ruoyi
 * @date 2022-01-19
 */
@Data
public class BaseExaminePackageOptionVO
{
    private static final long serialVersionUID = 1L;

    /** ID */
    @ApiModelProperty("pc端套餐id")
    private String id;

    /** 体检套餐名称 */
    @ApiModelProperty("pc端套餐名字")
    private String name;

    /** 输入码 */
    @ApiModelProperty("pc端套餐名字首字母")
    private String inputCode;
    /**
     * 是否关联
     */
    @ApiModelProperty("pc端套餐是否关联移动端套餐   1 ：已关联  0：未关联")
    private String isAassociation;


}
