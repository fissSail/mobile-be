package com.ruoyi.mobile.auth;

import com.ruoyi.mobile.login.service.impl.MobilePhoneLoginServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

/**
 * @author yanfeifan
 * @Package com.ruoyi.mobile.auth
 * @Description 自定义移动端手机认证
 * @date 2022/4/8 11:52
 */
@Component
public class MobilePhoneAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private MobilePhoneLoginServiceImpl mobilePhoneLoginService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        //手机号
        String phone = authentication.getName();
        UserDetails userDetails = mobilePhoneLoginService.loadUserByUsername(phone);
        return new MobilePhoneAuthenticationToken(userDetails);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return MobilePhoneAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
