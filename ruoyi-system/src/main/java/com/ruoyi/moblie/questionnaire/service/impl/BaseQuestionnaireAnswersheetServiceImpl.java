package com.ruoyi.moblie.questionnaire.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.mobile.entity.BaseQuestionnaireAnswersDO;
import com.ruoyi.common.mobile.entity.BaseQuestionnaireAnswersOptionRelationDO;
import com.ruoyi.common.mobile.entity.BaseQuestionnaireAnswersheetDO;
import com.ruoyi.common.mobile.entity.BaseQuestionnaireBankDO;
import com.ruoyi.moblie.healthtips.domain.vo.QuestionnaireAnswersOptionRelationVO;
import com.ruoyi.moblie.healthtips.domain.vo.SysQuestionnaireAnswersVO;
import com.ruoyi.moblie.questionnaire.mapper.BaseQuestionnaireAnswersMapper;
import com.ruoyi.moblie.questionnaire.mapper.BaseQuestionnaireAnswersOptionRelationMapper;
import com.ruoyi.moblie.questionnaire.mapper.BaseQuestionnaireAnswersheetMapper;
import com.ruoyi.moblie.questionnaire.mapper.BaseQuestionnaireBankMapper;
import com.ruoyi.moblie.questionnaire.service.BaseQuestionnaireAnswersheetService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class BaseQuestionnaireAnswersheetServiceImpl extends ServiceImpl<BaseQuestionnaireAnswersheetMapper, BaseQuestionnaireAnswersheetDO> implements BaseQuestionnaireAnswersheetService {

    private final BaseQuestionnaireBankMapper questionnaireBankMapper;

    private final BaseQuestionnaireAnswersOptionRelationMapper questionnaireAnswersOptionRelationMapper;

    private final BaseQuestionnaireAnswersMapper questionnaireAnswersMapper;

    @Override
    public List<BaseQuestionnaireAnswersheetDO> queryPage(String userName) {
        return this.list(new QueryWrapper<BaseQuestionnaireAnswersheetDO>()
                .like(StringUtils.hasText(userName), "user_name", userName)
                .orderByDesc("create_time"));
    }

    @Override
    public List<QuestionnaireAnswersOptionRelationVO> getQuestionnaireAnswersOptionRelationByAnswersheetId(String id) {
        //??????????????????id????????????????????????
        List<BaseQuestionnaireAnswersOptionRelationDO> questionnaireAnswersOptionRelationDOList = questionnaireAnswersOptionRelationMapper
                .selectList(new LambdaQueryWrapper<BaseQuestionnaireAnswersOptionRelationDO>()
                        .eq(BaseQuestionnaireAnswersOptionRelationDO::getQuestionnaireAnswersheetId, id));

        //????????????list
        List<BaseQuestionnaireAnswersDO> questionnaireAnswersDOList = questionnaireAnswersMapper
                .selectList(new QueryWrapper<BaseQuestionnaireAnswersDO>().orderByAsc("sort"));

        //??????????????????????????????
        List<BaseQuestionnaireBankDO> baseQuestionnaireBankDOList = questionnaireBankMapper
                .selectList(new LambdaQueryWrapper<BaseQuestionnaireBankDO>()
                        .orderByAsc(BaseQuestionnaireBankDO::getSort)
                        .orderByDesc(BaseQuestionnaireBankDO::getCreateTime));

        //?????????????????????
        List<QuestionnaireAnswersOptionRelationVO> questionnaireAnswersOptionRelationVOList = baseQuestionnaireBankDOList
                .stream()
                .map(questionnaireBankDO -> {
                    QuestionnaireAnswersOptionRelationVO optionRelationVO = new QuestionnaireAnswersOptionRelationVO();
                    BeanUtils.copyProperties(questionnaireBankDO, optionRelationVO);

                    //?????????????????????????????????
                    this.extractedQuestionnaireAnswersVOList(questionnaireAnswersDOList, optionRelationVO);

                    //??????????????????????????????
                    this.extractedQuestionnaireAnswersIdList(questionnaireAnswersOptionRelationDOList, optionRelationVO);

                    return optionRelationVO;
                }).collect(Collectors.toList());

        return questionnaireAnswersOptionRelationVOList;
    }

    /**
     * ??????????????????????????????
     *
     * @param questionnaireAnswersOptionRelationDOList
     * @param optionRelationVO
     */
    private void extractedQuestionnaireAnswersIdList(List<BaseQuestionnaireAnswersOptionRelationDO> questionnaireAnswersOptionRelationDOList,
                                                     QuestionnaireAnswersOptionRelationVO optionRelationVO) {
        List<String> list = questionnaireAnswersOptionRelationDOList
                .stream()
                .filter(optionRelationDO ->
                        //??????????????????????????????
                        Objects.equals(optionRelationDO.getQuestionnaireBankId(), optionRelationVO.getId()))
                .map(BaseQuestionnaireAnswersOptionRelationDO::getQuestionnaireAnswersId)
                .collect(Collectors.toList());
        optionRelationVO.setQuestionnaireAnswersIdList(list);
    }

    /**
     * ?????????????????????????????????
     *
     * @param questionnaireAnswersDOList
     * @param optionRelationVO
     */
    private void extractedQuestionnaireAnswersVOList(List<BaseQuestionnaireAnswersDO> questionnaireAnswersDOList,
                                                     QuestionnaireAnswersOptionRelationVO optionRelationVO) {
        List<SysQuestionnaireAnswersVO> questionnaireAnswersVOList = questionnaireAnswersDOList
                .stream()
                .filter(questionnaireAnswersData ->
                        Objects.equals(optionRelationVO.getId(), questionnaireAnswersData.getQuestionnaireBankId()))
                .map(questionnaireAnswersData -> {
                    SysQuestionnaireAnswersVO questionnaireAnswersVO = new SysQuestionnaireAnswersVO();
                    BeanUtils.copyProperties(questionnaireAnswersData, questionnaireAnswersVO);
                    return questionnaireAnswersVO;
                }).collect(Collectors.toList());

        optionRelationVO.setQuestionnaireAnswersVOList(questionnaireAnswersVOList);
    }
}
