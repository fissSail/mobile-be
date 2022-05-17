package com.ruoyi.common.mobile.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 体检类型对象
*/
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("base_examine_type")
public class BaseExamineTypeDO
{
    private static final long serialVersionUID = 1L;

    /** id */
    private String id;

    /** 名称 */
    @Excel(name = "名称")
    private String name;

    /** 编码 */
    @Excel(name = "编码")
    private String code;

    /** 输入码 */
    @Excel(name = "输入码")
    private String inputCode;

    /** 状态 1启用0禁用 */
    private Integer status;

    /** 报告模板id */
    private String reportTemplateId;

    /** 创建人id */
    private Long createId;

    /** 修改人id */
    private Long updateId;

    /** 排序 */
    private Integer sort;

    /** 删除标志 0存在 1删除 */
    private String delFlag;

    /** 驻户id */
    private String residentId;

    /** 版本号 */
    private Integer version;

}
