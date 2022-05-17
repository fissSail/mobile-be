package com.ruoyi.common.mobile.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * 健康小常识表
 *
 * @author yff
 * @email sunlightcs@gmail.com
 * @date 2022-03-29 15:23:08
 */
@Data
@TableName("base_health_tips")
public class BaseHealthTipsDO {

	/**
	 * 公告ID
	 */
	@TableId
	private String id;
	/**
	 * 公告标题
	 */
	private String tipsTitle;
	/**
	 * 公告内容
	 */
	private String tipsContent;
	/**
	 * 公告类型（0通知，1健康小常识）
	 */
	private String tipsType;
	/**
	 * 公告状态（0正常 1关闭）
	 */
	private String status;
	/**
	 * 创建者
	 */
	private String createBy;
	/**
	 * 创建时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date createTime;
	/**
	 * 更新者
	 */
	private String updateBy;
	/**
	 * 更新时间
	 */
	private Date updateTime;
	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 标签id
	 */
	private String tagId;
	/**
	 * 删除标志 0存在 1删除
	 */
	@TableLogic
	private String delFlag;

}
