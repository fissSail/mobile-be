package com.ruoyi.mobile.controller.login;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.mobile.login.bo.LoginBO;
import com.ruoyi.mobile.login.service.MobileLoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;

@RequestMapping("mobile/login")
@Api(tags = "移动端用户登录")
@RestController
@Validated
public class MobileLoginController {

    @Autowired
    private MobileLoginService mobileLoginService;

    /**
     * 登录发送短信
     *
     * @param mobilePhone
     * @return
     */
    @GetMapping("verificationCode")
    @ApiOperation("登录发送短信")
    @ApiImplicitParam(name = "mobilePhone", value = "手机号", required = true)
    public AjaxResult verificationCode(@Pattern(regexp = "^[1][3,4,5,6,7,8,9][0-9]{9}$", message = "手机号格式有误")
                                           @RequestParam("mobilePhone") String mobilePhone) {
        return AjaxResult.success(mobileLoginService.verificationCode(mobilePhone));
    }

    /**
     * 移动端登录
     *
     * @param loginBO
     * @return
     */
    @PostMapping
    @ApiOperation("移动端登录")
    public AjaxResult login(@Valid @RequestBody LoginBO loginBO) {
        return AjaxResult.success(mobileLoginService.login(loginBO));
    }

    /**
     * 移动端微信登录
     *
     * @param code
     * @return
     */
    @GetMapping("/wx")
    @ApiOperation("移动端微信登录")
    @ApiImplicitParam(name = "code", value = "微信授权后，获取code", required = true)
    public AjaxResult wxLogin(@RequestParam("code") String code) {
        return AjaxResult.success(mobileLoginService.wxLogin(code));
    }

}
