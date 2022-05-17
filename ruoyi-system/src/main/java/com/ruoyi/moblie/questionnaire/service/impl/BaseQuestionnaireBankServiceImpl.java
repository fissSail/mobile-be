package com.ruoyi.moblie.questionnaire.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.mobile.entity.BaseQuestionnaireAnswersDO;
import com.ruoyi.common.mobile.entity.BaseQuestionnaireBankDO;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.uuid.IdUtils;
import com.ruoyi.moblie.healthtips.domain.vo.SysBaseQuestionnaireBankVO;
import com.ruoyi.moblie.healthtips.domain.vo.SysQuestionnaireAnswersVO;
import com.ruoyi.moblie.questionnaire.mapper.BaseQuestionnaireBankMapper;
import com.ruoyi.moblie.questionnaire.service.BaseQuestionnaireAnswersService;
import com.ruoyi.moblie.questionnaire.service.BaseQuestionnaireBankService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class BaseQuestionnaireBankServiceImpl extends ServiceImpl<BaseQuestionnaireBankMapper, BaseQuestionnaireBankDO> implements BaseQuestionnaireBankService {

    @Autowired
    private BaseQuestionnaireAnswersService questionnaireAnswersService;

    @Override
    public List<BaseQuestionnaireBankDO> queryPage(String questionTitle) {
        return this.list(new QueryWrapper<BaseQuestionnaireBankDO>()
                .like(StringUtils.hasText(questionTitle), "question_title", questionTitle)
                .orderByAsc("sort").orderByDesc("create_time"));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveQuestionnaireBankAndOptions(SysBaseQuestionnaireBankVO sysBaseQuestionnaireBankVO) {
        //获取用户信息
        SysUser user = SecurityUtils.getLoginUser().getUser();
        //保存问题信息
        BaseQuestionnaireBankDO questionnaireBankDO = new BaseQuestionnaireBankDO();
        String questionnaireBankDOId = IdUtils.fastSimpleUUID();
        BeanUtils.copyProperties(sysBaseQuestionnaireBankVO, questionnaireBankDO);
        questionnaireBankDO.setId(questionnaireBankDOId);
        questionnaireBankDO.setCreateBy(user.getUserName());
        questionnaireBankDO.setCreateId(user.getUserId());
        questionnaireBankDO.setCreateTime(DateUtils.getNowDate());
        this.save(questionnaireBankDO);

        //批量保存问题对应选项
        this.saveBatchByQuestionnaireAnswers(sysBaseQuestionnaireBankVO, questionnaireBankDOId);
    }

    @Override
    public SysBaseQuestionnaireBankVO getQuestionnaireBankAndOptionsById(String id) {
        SysBaseQuestionnaireBankVO questionnaireBankVO = new SysBaseQuestionnaireBankVO();
        //根据id查询题目信息
        BaseQuestionnaireBankDO questionnaireBankDO = this.getById(id);
        BeanUtils.copyProperties(questionnaireBankDO, questionnaireBankVO);

        //根据题目表id查询题目对应选项信息
        List<BaseQuestionnaireAnswersDO> questionnaireAnswersDOList = questionnaireAnswersService
                .list(new QueryWrapper<BaseQuestionnaireAnswersDO>()
                        .eq("questionnaire_bank_id", id).orderByAsc("sort"));

        //BaseQuestionnaireAnswersDO转SysQuestionnaireAnswersVO
        List<SysQuestionnaireAnswersVO> questionnaireAnswersVOList = questionnaireAnswersDOList
                .stream()
                .map(data -> {
                    SysQuestionnaireAnswersVO questionnaireAnswersVO = new SysQuestionnaireAnswersVO();
                    BeanUtils.copyProperties(data, questionnaireAnswersVO);
                    return questionnaireAnswersVO;
                }).collect(Collectors.toList());

        questionnaireBankVO.setQuestionnaireAnswersVOList(questionnaireAnswersVOList);
        return questionnaireBankVO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateQuestionnaireBankAndOptionsById(SysBaseQuestionnaireBankVO sysBaseQuestionnaireBankVO) {
        //获取用户信息
        SysUser user = SecurityUtils.getLoginUser().getUser();
        //根据id修改题目信息
        BaseQuestionnaireBankDO questionnaireBankDO = new BaseQuestionnaireBankDO();
        BeanUtils.copyProperties(sysBaseQuestionnaireBankVO, questionnaireBankDO);

        questionnaireBankDO.setUpdateBy(user.getUserName());
        questionnaireBankDO.setUpdateId(user.getUserId());
        questionnaireBankDO.setUpdateTime(DateUtils.getNowDate());
        this.updateById(questionnaireBankDO);

        //判断问题选项是否可修改
        if(sysBaseQuestionnaireBankVO.getUpdateOptionsFlag()){
            //删除并新增题目选项
            String questionnaireBankDOId = questionnaireBankDO.getId();
            questionnaireAnswersService.remove(new QueryWrapper<BaseQuestionnaireAnswersDO>()
                    .eq("questionnaire_bank_id", questionnaireBankDOId));

            //批量保存问题对应选项
            this.saveBatchByQuestionnaireAnswers(sysBaseQuestionnaireBankVO, questionnaireBankDOId);
        }

    }


    /**
     * 批量保存问题对应选项
     *
     * @param sysBaseQuestionnaireBankVO
     * @param questionnaireBankDOId
     */
    private void saveBatchByQuestionnaireAnswers(SysBaseQuestionnaireBankVO sysBaseQuestionnaireBankVO, String questionnaireBankDOId) {
        List<BaseQuestionnaireAnswersDO> questionnaireAnswersDOList = sysBaseQuestionnaireBankVO
                .getQuestionnaireAnswersVOList()
                .stream()
                .map(data -> {
                    BaseQuestionnaireAnswersDO questionnaireAnswersDO = new BaseQuestionnaireAnswersDO();
                    BeanUtils.copyProperties(data, questionnaireAnswersDO);
                    questionnaireAnswersDO.setId(IdUtils.fastSimpleUUID());
                    questionnaireAnswersDO.setQuestionnaireBankId(questionnaireBankDOId);
                    return questionnaireAnswersDO;
                }).collect(Collectors.toList());
        questionnaireAnswersService.saveBatch(questionnaireAnswersDOList);
    }

}
