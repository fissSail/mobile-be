package com.ruoyi.moblie.questionnaire.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.common.mobile.entity.BaseQuestionnaireAnswersheetDO;
import com.ruoyi.moblie.healthtips.domain.vo.QuestionnaireAnswersOptionRelationVO;

import java.util.List;

/**
 * 问卷答卷表
 *
 * @author yff
 * @email sunlightcs@gmail.com
 * @date 2022-03-24 10:29:06
 */
public interface BaseQuestionnaireAnswersheetService extends IService<BaseQuestionnaireAnswersheetDO> {

    /**
     * 问卷答卷列表
     * @param userName
     * @return
     */
    List<BaseQuestionnaireAnswersheetDO> queryPage(String userName);

    /**
     * 根据用户答卷id查询问卷答卷详情
     * @param id
     * @return
     */
    List<QuestionnaireAnswersOptionRelationVO> getQuestionnaireAnswersOptionRelationByAnswersheetId(String id);
}

