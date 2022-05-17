package com.ruoyi.common.mobile.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @ClassName BaseCoding
 * @Description
 **/
@Data
@TableName("base_coding")
public class BaseCodingDO {

    private String codeType;
    private Integer createId;
    private Long orderNum;
    private Date createTime;

}
