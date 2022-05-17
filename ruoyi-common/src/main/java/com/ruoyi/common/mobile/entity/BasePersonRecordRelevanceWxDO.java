package com.ruoyi.common.mobile.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 *
 *  用户档案与微信授权关联表
 * @author yff
 * @date 2022-04-12 11:02:38
 */
@Data
@TableName("base_person_record_relevance_wx")
public class BasePersonRecordRelevanceWxDO {

	/**
	 * 微信授权后用户标识
	 */
	private String openId;

	/**
	 * 人员档案表id
	 */
	private String personRecordId;

	/**
	 * 删除标志 0存在 1删除
	 */
	@TableLogic
	private String delFlag;

	/**
	 * 绑定时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date createTime;

}
