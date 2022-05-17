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
        //根据用户答卷id查询问卷答案信息
        List<BaseQuestionnaireAnswersOptionRelationDO> questionnaireAnswersOptionRelationDOList = questionnaireAnswersOptionRelationMapper
                .selectList(new LambdaQueryWrapper<BaseQuestionnaireAnswersOptionRelationDO>()
                        .eq(BaseQuestionnaireAnswersOptionRelationDO::getQuestionnaireAnswersheetId, id));

        //问卷选项list
        List<BaseQuestionnaireAnswersDO> questionnaireAnswersDOList = questionnaireAnswersMapper
                .selectList(new QueryWrapper<BaseQuestionnaireAnswersDO>().orderByAsc("sort"));

        //查询问题题库所有问题
        List<BaseQuestionnaireBankDO> baseQuestionnaireBankDOList = questionnaireBankMapper
                .selectList(new LambdaQueryWrapper<BaseQuestionnaireBankDO>()
                        .orderByAsc(BaseQuestionnaireBankDO::getSort)
                        .orderByDesc(BaseQuestionnaireBankDO::getCreateTime));

        //收集为返回对象
        List<QuestionnaireAnswersOptionRelationVO> questionnaireAnswersOptionRelationVOList = baseQuestionnaireBankDOList
                .stream()
                .map(questionnaireBankDO -> {
                    QuestionnaireAnswersOptionRelationVO optionRelationVO = new QuestionnaireAnswersOptionRelationVO();
                    BeanUtils.copyProperties(questionnaireBankDO, optionRelationVO);

                    //过滤出问卷中每题的选项
                    this.extractedQuestionnaireAnswersVOList(questionnaireAnswersDOList, optionRelationVO);

                    //过滤出对应问题的答案
                    this.extractedQuestionnaireAnswersIdList(questionnaireAnswersOptionRelationDOList, optionRelationVO);

                    return optionRelationVO;
                }).collect(Collectors.toList());

        return questionnaireAnswersOptionRelationVOList;
    }

    /**
     * 过滤出对应问题的答案
     *
     * @param questionnaireAnswersOptionRelationDOList
     * @param optionRelationVO
     */
    private void extractedQuestionnaireAnswersIdList(List<BaseQuestionnaireAnswersOptionRelationDO> questionnaireAnswersOptionRelationDOList,
                                                     QuestionnaireAnswersOptionRelationVO optionRelationVO) {
        List<String> list = questionnaireAnswersOptionRelationDOList
                .stream()
                .filter(optionRelationDO ->
                        //过滤出对应问题的答案
                        Objects.equals(optionRelationDO.getQuestionnaireBankId(), optionRelationVO.getId()))
                .map(BaseQuestionnaireAnswersOptionRelationDO::getQuestionnaireAnswersId)
                .collect(Collectors.toList());
        optionRelationVO.setQuestionnaireAnswersIdList(list);
    }

    /**
     * 过滤出问卷中每题的选项
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
