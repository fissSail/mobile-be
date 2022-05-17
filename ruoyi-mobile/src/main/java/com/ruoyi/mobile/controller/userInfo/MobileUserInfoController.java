package com.ruoyi.mobile.controller.userInfo;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.ResponseResult;
import com.ruoyi.mobile.login.service.MobileLoginService;
import com.ruoyi.mobile.login.vo.LoginReturnVO;
import com.ruoyi.mobile.userInfo.service.MobileUserInfoService;
import com.ruoyi.mobile.userInfo.vo.BasePersonRecordVO;
import com.ruoyi.mobile.login.vo.PersonRecordBindingWxVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.HashSet;

@RequestMapping("userInfo")
@RestController
@Api(tags = "移动端用户信息")
@Validated
public class MobileUserInfoController {

    @Autowired
    private MobileUserInfoService mobileUserInfoService;

    @Autowired
    private MobileLoginService mobileLoginService;


    @GetMapping("{userId}")
    @ApiOperation("获取用户个人信息")
    public AjaxResult userInfo(@PathVariable("userId") String userId) {
        return AjaxResult.success(mobileUserInfoService.userInfo(userId));
    }

    @GetMapping("dictionary")
    @ApiOperation("获取字典类型数据")
    public AjaxResult dictionary(@RequestParam("dictType") String dictType) {
        return AjaxResult.success(mobileUserInfoService.dictionary(dictType));
    }

    @PostMapping
    @ApiOperation("添加或修改用户信息")
    public AjaxResult addAndUpdate(@Valid @RequestBody BasePersonRecordVO basePersonRecordVO) {


        return AjaxResult.success(mobileUserInfoService.addAndUpdate(basePersonRecordVO));
    }

    /**
     * 根据手机号或证件号查询用户信息
     *
     * @param phoneOrCertificateNo
     * @return
     */
    @GetMapping("info/{phoneOrCertificateNo}")
    @ApiOperation("根据手机号或证件号查询用户信息")
    public AjaxResult getUserInfoByPhoneOrCertificateNo(@NotBlank(message = "手机号或证件号不能为空")
                                                        @PathVariable("phoneOrCertificateNo") String phoneOrCertificateNo) {
        return AjaxResult.success(mobileUserInfoService.getUserInfoByPhoneOrCertificateNo(phoneOrCertificateNo));
    }

    /**
     * 移动端用户id绑定微信授权后的用户标识apenid
     *
     * @param personRecordBindingWxVO
     * @return
     */
    @PostMapping("/bindingWx")
    @ApiOperation("用户id绑定微信授权用户标识")
    public ResponseResult<LoginReturnVO> bindingWx(@Valid @RequestBody PersonRecordBindingWxVO personRecordBindingWxVO) {
        try {
            return new ResponseResult().ok(mobileLoginService.bindingWx(personRecordBindingWxVO));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseResult.errorResult(e.getMessage());
        }
    }


    /**
     * 移动端用户id解绑微信授权后的用户标识apenid
     *
     * @param openId
     * @param personRecordId
     * @return
     */
    @GetMapping("/unboundWx")
    @ApiOperation("用户id解绑微信授权用户标识")
    public ResponseResult<LoginReturnVO> unboundWx(@RequestParam(value = "openId", required = false) String openId,
                                                   @NotBlank(message = "用户id不能为空")
                                                   @RequestParam(value = "personRecordId") String personRecordId) {
        try {
            return new ResponseResult().ok(mobileLoginService.unboundWx(openId, personRecordId));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseResult.errorResult(e.getMessage());
        }
    }
}
