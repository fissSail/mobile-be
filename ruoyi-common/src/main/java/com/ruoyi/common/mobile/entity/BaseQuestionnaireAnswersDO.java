package com.ruoyi.common.mobile.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 问卷选项表
 *
 * @author yff
 * @date 2022-03-24 10:29:06
 */
@Data
@TableName("base_questionnaire_answers")
public class BaseQuestionnaireAnswersDO {

	/**
	 * id
	 */
	@TableId
	private String id;
	/**
	 * 选项
	 */
	private String questionnaireOption;
	/**
	 * 额外内容
	 */
	private String extra;
	/**
	 * 问题表id
	 */
	private String questionnaireBankId;
	/**
	 * 标签id
	 */
	private String tagId;
	/**
	 * 排序
	 */
	private Integer sort;

}
