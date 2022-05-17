package com.ruoyi.mobile.healthtips.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.mobile.entity.BaseHealthTipsDO;
import com.ruoyi.common.mobile.entity.BasePersonTagDO;
import com.ruoyi.common.mobile.enums.CommonlyUsedEnum;
import com.ruoyi.mobile.healthtips.service.MobileHealthTipsService;
import com.ruoyi.mobile.healthtips.vo.HealthTipsVO;
import com.ruoyi.mobile.userInfo.mapper.BasePersonTagMapper;
import com.ruoyi.moblie.healthtips.mapper.BaseHealthTipsMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author yanfeifan
 * @Package com.ruoyi.mobile.service.impl
 * @Description 移动端健康小常识实现层
 * @date 2022/3/30 14:04
 */
@Service
@RequiredArgsConstructor
public class MobileHealthTipsServiceImpl implements MobileHealthTipsService {

    private final BaseHealthTipsMapper baseHealthTipsMapper;

    private final BasePersonTagMapper personTagMapper;

    @Override
    public Page<HealthTipsVO> recommendListByUserId(String userId, Integer pageNum, Integer pageSize) {
        List<String> tagIdList = new ArrayList<>();
        //根据用户id查询用户绑定标签
        if(StringUtils.hasText(userId)){
            List<BasePersonTagDO> basePersonTagDOList = personTagMapper
                    .selectList(new LambdaQueryWrapper<BasePersonTagDO>()
                            .eq(StringUtils.hasText(userId), BasePersonTagDO::getPersonRecordId, userId));

            tagIdList = basePersonTagDOList.stream().map(data -> data.getTagId()).distinct().collect(Collectors.toList());
        }

        //分页查询关联套餐的小常识
        Page<BaseHealthTipsDO> baseHealthTipsDOPage = baseHealthTipsMapper
                .selectPage(new Page<>(pageNum, pageSize),
                        new LambdaQueryWrapper<BaseHealthTipsDO>()
                                .in(!CollectionUtils.isEmpty(tagIdList), BaseHealthTipsDO::getTagId, tagIdList)
                                .eq(BaseHealthTipsDO::getTipsType, CommonlyUsedEnum.HEALTH_TIPS.val()));

        return this.getHealthTipsVOPage(pageNum, pageSize, baseHealthTipsDOPage);
    }

    @Override
    public HealthTipsVO getById(String id) {
        HealthTipsVO vo = new HealthTipsVO();
        BaseHealthTipsDO healthTipsDO = baseHealthTipsMapper.selectById(id);
        BeanUtils.copyProperties(healthTipsDO, vo);
        return vo;
    }

    @Override
    public Page<HealthTipsVO> noticeList(Integer pageNum, Integer pageSize) {
        Page<BaseHealthTipsDO> baseHealthTipsDOPage = baseHealthTipsMapper
                .selectPage(new Page<>(pageNum, pageSize),
                        new LambdaQueryWrapper<BaseHealthTipsDO>().eq(BaseHealthTipsDO::getTipsType, CommonlyUsedEnum.NOTICE.val()));
        //BaseHealthTipsDO收集为HealthTipsVO
        return getHealthTipsVOPage(pageNum, pageSize, baseHealthTipsDOPage);
    }

    /**
     * 收集为HealthTipsVO
     *
     * @param pageNum
     * @param pageSize
     * @param baseHealthTipsDOPage
     * @return
     */
    private Page<HealthTipsVO> getHealthTipsVOPage(Integer pageNum, Integer pageSize, Page<BaseHealthTipsDO> baseHealthTipsDOPage) {
        //BaseHealthTipsDO收集为HealthTipsVO
        List<HealthTipsVO> healthTipsVOList = baseHealthTipsDOPage
                .getRecords()
                .stream()
                .map(data -> {
                    HealthTipsVO vo = new HealthTipsVO();
                    BeanUtils.copyProperties(data, vo);
                    return vo;
                }).collect(Collectors.toList());

        return new Page<HealthTipsVO>(pageNum, pageSize, baseHealthTipsDOPage.getTotal()).setRecords(healthTipsVOList);
    }
}
