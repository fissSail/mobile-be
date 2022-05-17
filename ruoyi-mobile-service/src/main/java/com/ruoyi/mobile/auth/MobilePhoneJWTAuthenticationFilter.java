package com.ruoyi.mobile.auth;

import com.ruoyi.common.config.TokenConfig;
import com.ruoyi.common.core.domain.model.MobileLoginUser;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author yanfeifan
 * @Package com.ruoyi.mobile.auth
 * @Description 移动端jwt过滤
 * @date 2022/4/11 14:27
 */
@Component
public class MobilePhoneJWTAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private TokenConfig tokenConfig;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        MobileLoginUser mobileLoginUser = tokenConfig.getMobileLoginUser(request);
        //一次请求有一个线程负责，也就是说这个请求经过这个filter之后到这次请求结束的其他地方都能拿到认证信息。在这个filter之前拿不到，因为还没放进去
        if (StringUtils.isNotNull(mobileLoginUser) && StringUtils.isNull(SecurityUtils.getAuthentication())) {
            tokenConfig.verifyToken(mobileLoginUser);
            MobilePhoneAuthenticationToken authenticationToken = new MobilePhoneAuthenticationToken(mobileLoginUser, mobileLoginUser.getAuthorities());
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            //SecurityContext存储的是线程级变量，也可以简单理解为request级的。
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
        chain.doFilter(request, response);
    }

}
