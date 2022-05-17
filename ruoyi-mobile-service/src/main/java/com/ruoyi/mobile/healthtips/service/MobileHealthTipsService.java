package com.ruoyi.mobile.healthtips.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.mobile.healthtips.vo.HealthTipsVO;

/**
 * @author yanfeifan
 * @Package com.ruoyi.mobile.service
 * @Description 移动端健康小常识service
 * @date 2022/3/30 14:04
 */
public interface MobileHealthTipsService {


    /**
     * 根据用户id查询推荐小常识
     *
     * @param userId
     * @return
     */
    Page<HealthTipsVO> recommendListByUserId(String userId, Integer pageNum, Integer pageSize);

    /**
     * 根据小常识id查询小常识详情
     * @param id
     * @return
     */
    HealthTipsVO getById(String id);


    /**
     * 查询通知公告
     *
     * @return
     */
    Page<HealthTipsVO> noticeList(Integer pageNum, Integer pageSize);
}
