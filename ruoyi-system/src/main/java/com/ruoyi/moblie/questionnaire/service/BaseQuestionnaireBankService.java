package com.ruoyi.moblie.questionnaire.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.common.mobile.entity.BaseQuestionnaireBankDO;
import com.ruoyi.moblie.healthtips.domain.vo.SysBaseQuestionnaireBankVO;

import java.util.List;

/**
 * 问卷问题表
 *
 * @author yff
 * @email sunlightcs@gmail.com
 * @date 2022-03-24 10:29:06
 */
public interface BaseQuestionnaireBankService extends IService<BaseQuestionnaireBankDO> {

    /**
     * 题目列表
     * @param questionTitle
     * @return
     */
    List<BaseQuestionnaireBankDO> queryPage(String questionTitle);

    /**
     * 保存题目信息及选项
     * @param sysBaseQuestionnaireBankVO
     */
    void saveQuestionnaireBankAndOptions(SysBaseQuestionnaireBankVO sysBaseQuestionnaireBankVO);

    /**
     * 根据题目id查询题目信息及选项
     * @param id
     * @return
     */
    SysBaseQuestionnaireBankVO getQuestionnaireBankAndOptionsById(String id);

    /**
     * 根据题目id修改题目信息及选项
     * @param sysBaseQuestionnaireBankVO
     */
    void updateQuestionnaireBankAndOptionsById(SysBaseQuestionnaireBankVO sysBaseQuestionnaireBankVO);
}

