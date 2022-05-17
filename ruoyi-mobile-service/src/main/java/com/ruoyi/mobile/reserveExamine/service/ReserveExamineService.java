package com.ruoyi.mobile.reserveExamine.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.mobile.reserveExamine.dto.ExamineFinishListDTO;
import com.ruoyi.mobile.reserveExamine.dto.ReserveExamineDTO;
import com.ruoyi.mobile.reserveExamine.vo.ExamineFinishListVO;

public interface ReserveExamineService {
    /**
     * 添加预约体检记录
     * @param reserveExamineDTO
     * @return
     */
    AjaxResult addExamineRecord(ReserveExamineDTO reserveExamineDTO);

    /**
     * 获取用户体检项目
     * @param userId
     * @return
     */
    AjaxResult findByList(Integer pageNum,Integer pageSize,Integer type);

    /**
     * 套餐详情列表
     * @param examineRecordId
     * @return
     */
    AjaxResult packageDetails(String examineRecordId,Integer pageNum,Integer pageSize);

    /**
     * 取消体检预约
     * @param examineRecordId
     * @return
     */
    void cancelAppointment(String examineRecordId);

    /**
     * 获取体检完成列表
     * @param examineFinishListDTO
     * @return
     */
    Page<ExamineFinishListVO> examineFinishList(ExamineFinishListDTO examineFinishListDTO);
    /**
     * 获取体检列表
     * @param examineFinishListDTO
     * @return
     */
    Page<ExamineFinishListVO> examineList(ExamineFinishListDTO examineFinishListDTO);
}
