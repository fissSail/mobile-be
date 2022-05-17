package com.ruoyi.mobile.userInfo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.mobile.entity.BasePersonTagDO;
import com.ruoyi.common.mobile.entity.BaseQuestionnaireAnswersOptionRelationDO;
import com.ruoyi.mobile.userInfo.mapper.BasePersonTagMapper;
import com.ruoyi.mobile.userInfo.service.BasePersonTagService;
import com.ruoyi.moblie.questionnaire.mapper.BaseQuestionnaireAnswersOptionRelationMapper;
import com.ruoyi.moblie.questionnaire.service.BaseQuestionnaireAnswersOptionRelationService;
import org.springframework.stereotype.Service;

/**
 * @Description：
 * @Author：yff 用户绑定标签实现
 * @Date：2022/4/20 11:08
 */
@Service
public class BasePersonTagServiceImpl extends ServiceImpl<BasePersonTagMapper, BasePersonTagDO> implements BasePersonTagService {
}
