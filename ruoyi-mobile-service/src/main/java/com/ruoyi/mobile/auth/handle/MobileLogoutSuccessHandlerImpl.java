package com.ruoyi.mobile.auth.handle;

import com.alibaba.fastjson.JSON;
import com.ruoyi.common.config.TokenConfig;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.constant.HttpStatus;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.model.MobileLoginUser;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.common.utils.ServletUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.mobile.login.service.impl.MobileLoginServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Description：移动端退出实现
 * @Author：yff
 * @Date：2022/4/26 11:19
 */
@Configuration
public class MobileLogoutSuccessHandlerImpl implements LogoutSuccessHandler {

    @Autowired
    private TokenConfig tokenConfig;

    @Autowired
    private RedisCache redisCache;

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        MobileLoginUser mobileLoginUser = tokenConfig.getMobileLoginUser(request);
        if (StringUtils.isNotNull(mobileLoginUser)) {
            // 删除用户缓存记录
            tokenConfig.delLoginUser(mobileLoginUser.getToken());
            //删除redis中缓存的微信昵称
            redisCache.deleteObject(Constants.NICK_NAME_KEY + mobileLoginUser.getOpenId());
        }
        ServletUtils.renderString(response, JSON.toJSONString(AjaxResult.error(HttpStatus.SUCCESS, "退出成功")));
    }
}
