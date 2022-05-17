package com.ruoyi.mobile.questionnaire.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ruoyi.common.mobile.entity.*;
import com.ruoyi.mobile.questionnaire.bo.QuestionnaireBO;
import com.ruoyi.common.utils.uuid.IdUtils;
import com.ruoyi.mobile.questionnaire.bo.QuestionnaireBankBO;
import com.ruoyi.mobile.userInfo.mapper.BasePersonTagMapper;
import com.ruoyi.mobile.userInfo.service.BasePersonTagService;
import com.ruoyi.moblie.questionnaire.mapper.BaseQuestionnaireAnswersMapper;
import com.ruoyi.moblie.questionnaire.mapper.BaseQuestionnaireAnswersheetMapper;
import com.ruoyi.moblie.questionnaire.mapper.BaseQuestionnaireBankMapper;
import com.ruoyi.moblie.questionnaire.service.BaseQuestionnaireAnswersOptionRelationService;
import com.ruoyi.mobile.questionnaire.service.MobileQuestionnairesService;
import com.ruoyi.mobile.questionnaire.vo.QuestionnaireAnswersVO;
import com.ruoyi.mobile.questionnaire.vo.QuestionnaireBankVO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author yanfeifan
 * @Package com.ruoyi.mobile.service.impl
 * @Description 问卷实现层
 * @date 2022/3/23 15:07
 */
@Service
@RequiredArgsConstructor
public class MobileQuestionnairesServiceImpl implements MobileQuestionnairesService {

    private final BaseQuestionnaireBankMapper questionnaireBankMapper;

    private final BaseQuestionnaireAnswersMapper questionnaireAnswersMapper;

    private final BaseQuestionnaireAnswersheetMapper questionnaireAnswersheetMapper;

    private final BaseQuestionnaireAnswersOptionRelationService questionnaireAnswersOptionRelationService;

    private final BasePersonTagService personTagService;

    /**
     * 问卷列表
     *
     * @return
     */
    @Override
    public List<QuestionnaireBankVO> list() {
        //查询问卷关联的题目
        List<BaseQuestionnaireBankDO> questionnaireBankDOList = questionnaireBankMapper.selectList(new LambdaQueryWrapper<BaseQuestionnaireBankDO>()
                .orderByAsc(BaseQuestionnaireBankDO::getRequiredFlag)
                .orderByAsc(BaseQuestionnaireBankDO::getSort)
                .orderByDesc(BaseQuestionnaireBankDO::getCreateTime));

        //问卷选项list
        List<BaseQuestionnaireAnswersDO> questionnaireAnswersDOList = questionnaireAnswersMapper
                .selectList(new LambdaQueryWrapper<BaseQuestionnaireAnswersDO>().orderByAsc(BaseQuestionnaireAnswersDO::getSort));

        //收集为List<QuestionnaireVO>
        List<QuestionnaireBankVO> questionnaireBankVOList = questionnaireBankDOList
                .stream()
                .map(questionnaireData -> {
                    QuestionnaireBankVO questionnaireBankVO = new QuestionnaireBankVO();
                    BeanUtils.copyProperties(questionnaireData, questionnaireBankVO);

                    //过滤出问卷中每题的选项并设置到对应问题
                    questionnaireBankVO.setQuestionnaireAnswersVOList(this.getQuestionnaireAnswersVOList(questionnaireAnswersDOList, questionnaireBankVO));
                    return questionnaireBankVO;
                }).collect(Collectors.toList());

        return questionnaireBankVOList;
    }

    /**
     * 提交问卷
     *
     * @param questionnaireBO
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void add(QuestionnaireBO questionnaireBO) {
        //保存用户答卷信息
        BaseQuestionnaireAnswersheetDO questionnaireAnswersheetDO = new BaseQuestionnaireAnswersheetDO();
        String questionnaireAnswersheetId = IdUtils.fastSimpleUUID();
        questionnaireAnswersheetDO.setId(questionnaireAnswersheetId);
        questionnaireAnswersheetDO.setCreateTime(new Date());
        questionnaireAnswersheetDO.setUserId(questionnaireBO.getUserId());
        questionnaireAnswersheetDO.setUserName(questionnaireBO.getUserName());
        questionnaireAnswersheetMapper.insert(questionnaireAnswersheetDO);

        //将问卷问题集与问卷答卷选项答案集组合成List<BaseQuestionnaireAnswersOptionRelationDO>
        List<BaseQuestionnaireAnswersOptionRelationDO> questionnaireAnswersOptionRelationDOList =
                this.getBaseQuestionnaireAnswersOptionRelationDOS(questionnaireBO, questionnaireAnswersheetId);

        //批量保存问卷答卷选项答案
        questionnaireAnswersOptionRelationService.saveBatch(questionnaireAnswersOptionRelationDOList);

        //根据问卷答卷选项绑定用户标签
        this.addPersonTagByAnswerSheet(questionnaireBO.getUserId(), questionnaireAnswersOptionRelationDOList);
    }

    @Override
    public void addPersonTagByAnswerSheet(String userId, List<BaseQuestionnaireAnswersOptionRelationDO> questionnaireAnswersOptionRelationDOList) {

        //将问卷问题集与问卷答卷选项答案集转换为id集
        List<String> questionnaireAnswersIdList = questionnaireAnswersOptionRelationDOList
                .stream()
                .map(BaseQuestionnaireAnswersOptionRelationDO::getQuestionnaireAnswersId)
                .collect(Collectors.toList());

        //查询选项绑定的标签
        List<String> tagIdList = questionnaireAnswersMapper
                .selectBatchIds(questionnaireAnswersIdList)
                .stream()
                .filter(data-> StringUtils.hasText(data.getTagId()))
                .map(BaseQuestionnaireAnswersDO::getTagId)
                .distinct()
                .collect(Collectors.toList());

        List<BasePersonTagDO> basePersonTagDOS = tagIdList.stream().map(tagId -> {
            BasePersonTagDO personTagDO = new BasePersonTagDO();
            personTagDO.setTagId(tagId);
            personTagDO.setPersonRecordId(userId);
            return personTagDO;
        }).collect(Collectors.toList());

        //批量保存用户绑定标签
        personTagService.saveBatch(basePersonTagDOS);

    }


    /**
     * 过滤出问卷中每题的选项并转换List<QuestionnaireAnswersVO>
     *
     * @param questionnaireAnswersDOList
     * @param questionnaireBankVO
     * @return
     */
    private List<QuestionnaireAnswersVO> getQuestionnaireAnswersVOList(List<BaseQuestionnaireAnswersDO> questionnaireAnswersDOList, QuestionnaireBankVO questionnaireBankVO) {
        List<QuestionnaireAnswersVO> questionnaireAnswersVOList = questionnaireAnswersDOList
                .stream()
                .filter(questionnaireAnswersData ->
                        Objects.equals(questionnaireBankVO.getId(), questionnaireAnswersData.getQuestionnaireBankId()))
                .map(questionnaireAnswersData -> {
                    QuestionnaireAnswersVO questionnaireAnswersVO = new QuestionnaireAnswersVO();
                    BeanUtils.copyProperties(questionnaireAnswersData, questionnaireAnswersVO);
                    return questionnaireAnswersVO;
                }).collect(Collectors.toList());
        return questionnaireAnswersVOList;
    }

    /**
     * 将问卷问题集与问卷答卷选项答案集组合成List<BaseQuestionnaireAnswersOptionRelationDO>
     *
     * @param questionnaireBO
     * @param questionnaireAnswersheetId
     * @return
     */
    private List<BaseQuestionnaireAnswersOptionRelationDO> getBaseQuestionnaireAnswersOptionRelationDOS(QuestionnaireBO questionnaireBO,
                                                                                                        String questionnaireAnswersheetId) {
        List<BaseQuestionnaireAnswersOptionRelationDO> questionnaireAnswersOptionRelationDOList = questionnaireBO
                //问卷问题集
                .getQuestionnaireBankBOList()
                .stream()
                .flatMap(questionnaireBankBO ->
                        //问卷答卷选项答案集
                        questionnaireBankBO
                                .getQuestionnaireAnswersIdList()
                                .stream()
                                .map(answersId -> {
                                    //设置答题问题与对应选项
                                    BaseQuestionnaireAnswersOptionRelationDO questionnaireAnswersOptionRelationDO = new BaseQuestionnaireAnswersOptionRelationDO();
                                    //id
                                    questionnaireAnswersOptionRelationDO.setId(IdUtils.fastSimpleUUID());
                                    //问题表id
                                    questionnaireAnswersOptionRelationDO.setQuestionnaireBankId(questionnaireBankBO.getQuestionnaireBankId());
                                    //问题选项id
                                    questionnaireAnswersOptionRelationDO.setQuestionnaireAnswersId(answersId);
                                    //答题表id
                                    questionnaireAnswersOptionRelationDO.setQuestionnaireAnswersheetId(questionnaireAnswersheetId);
                                    return questionnaireAnswersOptionRelationDO;
                                })
                ).collect(Collectors.toList());
        return questionnaireAnswersOptionRelationDOList;
    }
}
