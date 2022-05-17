package com.ruoyi.common.mobile.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @description 体检记录申请表关联表
 * @author zhengkai.blog.csdn.net
 * @date 2022-03-28
 */
@Data
@TableName("base_examine_record_application")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BaseExamineRecordApplicationDO implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
    * id
    */
    private String id;

    /**
    * 体检记录id
    */
    private String examineRecordId;

    /**
    * 条码号
    */
    private String barcode;

    /**
    * 体检项目id
    */
    private String projectId;

    /**
    * 体检项目编码
    */
    private String projectCode;

    /**
    * 体检项目名称
    */
    private String projectName;

    /**
    * 是否采集 1是0否
    */
    private Integer isGather;

    /**
    * 采集者
    */
    private String gatherBy;

    /**
    * 采集时间
    */
    private Date gatherTime;

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
    * 组合项目id
    */
    private String combiProjectId;

    /**
    * 组合项目名
    */
    private String combiProjectName;

    /**
    * 是否打印体检项目的条码
    */
    private Integer isPrint;

    /**
    * 条码是否已经打印，1是，0否
    */
    private Integer whetherPrint;

    /**
    * 打印时间
    */
    private Date barcodePrintTime;

    /**
    * 打印人id
    */
    private Long barcodePrintId;

    /**
    * 打印人
    */
    private String barcodePrintBy;


}