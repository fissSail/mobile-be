package com.ruoyi.common.mobile.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @description 弃检记录表
 * @author zhengkai.blog.csdn.net
 * @date 2022-04-01
 */
@Data
@TableName("base_abandon_check_record")
public class BaseAbandonCheckRecordDO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
    * id
    */
    private String id;

    /**
    * 体检编号
    */
    private String examineCode;

    /**
    * 组合项目id
    */
    private String combiProjectId;

    /**
    * 弃检类型
    */
    private String abandonType;

    /**
    * 弃检原因
    */
    private String abandonReason;

    /**
    * 创建者
    */
    private String createBy;

    /**
    * 创建人id
    */
    private Long createId;

    /**
    * 创建时间
    */
    private Date createTime;

    /**
    * 更新者
    */
    private String updateBy;

    /**
    * 修改人id
    */
    private Long updateId;

    /**
    * 更新时间
    */
    private Date updateTime;

    /**
    * 排序
    */
    private Integer sort;

    /**
    * 删除标志 0存在 1删除
    */
    private String delFlag;

    /**
    * 驻户id
    */
    private String residentId;

    /**
    * 版本号
    */
    private Integer version;

    /**
    * 组合项目名称
    */
    private String combiProjectName;

    /**
    * 延检时间
    */
    private Date delayTime;


}