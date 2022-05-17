package com.ruoyi.mobile.controller.healthtips;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.core.domain.ResponseResult;
import com.ruoyi.mobile.healthtips.service.MobileHealthTipsService;
import com.ruoyi.mobile.healthtips.vo.HealthTipsVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author yanfeifan
 * @Package com.ruoyi.mobile.controller
 * @Description 健康小常识移动端接口
 * @date 2022/3/29 17:11
 */
@RequestMapping("mobile/healthtips")
@RestController
@Api(tags = "健康小常识")
@Validated
public class MobileHealthTipsController {

    @Autowired
    private MobileHealthTipsService healthTipsService;

    /**
     * 根据用户id查询推荐小常识
     */
    @GetMapping("/list")
    @ApiOperation("查询推荐小常识")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户ID"),
            @ApiImplicitParam(name = "pageNum", value = "页码"),
            @ApiImplicitParam(name = "pageSize", value = "条数")
    })
    public ResponseResult<Page<HealthTipsVO>> list(@RequestParam(value = "userId", required = false) String userId,
                                                   @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                                                   @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize) {
        return new ResponseResult().ok(healthTipsService.recommendListByUserId(userId, pageNum, pageSize));
    }

    /**
     * 健康小常识详情
     */
    @GetMapping("/{id}")
    @ApiOperation("健康小常识详情")
    @ApiImplicitParam(name = "id", value = "健康小常识id", required = true)
    public ResponseResult<HealthTipsVO> info(@PathVariable("id") String id) {
        return new ResponseResult().ok(healthTipsService.getById(id));
    }


    /**
     * 根据用户id查询推荐小常识
     */
    @GetMapping("/notice/list")
    @ApiOperation("查询通知公告")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "页码"),
            @ApiImplicitParam(name = "pageSize", value = "条数")
    })
    public ResponseResult<Page<HealthTipsVO>> noticeList(@RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                                                         @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize) {
        return new ResponseResult().ok(healthTipsService.noticeList(pageNum, pageSize));
    }
}
