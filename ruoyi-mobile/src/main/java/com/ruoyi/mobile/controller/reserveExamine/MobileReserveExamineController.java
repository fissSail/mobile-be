package com.ruoyi.mobile.controller.reserveExamine;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.mobile.reserveExamine.dto.ExamineFinishListDTO;
import com.ruoyi.mobile.reserveExamine.dto.ReserveExamineDTO;
import com.ruoyi.mobile.reserveExamine.service.ReserveExamineService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping("reserveExamine")
@RestController
@Api(tags = "预约体检")
public class MobileReserveExamineController {

    @Autowired
    private ReserveExamineService reserveExamineService;


    @PostMapping("add")
    @ApiOperation("用户预约体检")
    public AjaxResult add(@Valid @RequestBody ReserveExamineDTO reserveExamineDTO) {
        return reserveExamineService.addExamineRecord(reserveExamineDTO);

    }

    @GetMapping("list")
    @ApiOperation("获取用户体检项目列表")
    public AjaxResult list(@RequestParam("pageSize") Integer pageSize
            , @RequestParam("pageNum") Integer pageNum,
                           @RequestParam("type") Integer type) {
        return reserveExamineService.findByList(pageNum, pageSize, type);
    }

    @GetMapping("packageDetails")
    @ApiOperation("获取用户体检项目详情")
    public AjaxResult packageDetails(@RequestParam("examineRecordId") String examineRecordId,
                                     @RequestParam("pageSize") Integer pageSize
            , @RequestParam("pageNum") Integer pageNum) {
        return reserveExamineService.packageDetails(examineRecordId, pageNum, pageSize);
    }


    @DeleteMapping("cancelAppointment")
    @ApiOperation("取消预约")
    public AjaxResult cancelAppointment(@RequestParam("examineRecordId") String examineRecordId) {
        reserveExamineService.cancelAppointment(examineRecordId);
        return AjaxResult.success();

    }

    @GetMapping("examineFinishList")
    @ApiOperation("用户体检完成列表")
    public AjaxResult examineFinishList(ExamineFinishListDTO examineFinishListDTO) {
        return AjaxResult.success(reserveExamineService.examineFinishList(examineFinishListDTO));

    }


    @GetMapping("examineList")
    @ApiOperation("用户体检记录列表")
    public AjaxResult examineList(ExamineFinishListDTO examineFinishListDTO) {
        return AjaxResult.success(reserveExamineService.examineList(examineFinishListDTO));

    }
}
