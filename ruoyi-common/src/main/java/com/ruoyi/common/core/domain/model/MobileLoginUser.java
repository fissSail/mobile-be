package com.ruoyi.common.core.domain.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.ruoyi.common.mobile.entity.BasePersonRecordDO;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * 移动端登录用户身份
 *
 * @author ruoyi
 */
@Data
public class MobileLoginUser implements UserDetails {
    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 用户名称
     */
    private String personName;

    /**
     * 用户唯一标识
     */
    private String token;

    /**
     * 登录时间
     */
    private Long loginTime;

    /**
     * 过期时间
     */
    private Long expireTime;

    /**
     * 用户信息
     */
    private BasePersonRecordDO basePersonRecordDO;

    /**
     * 是否为信息完善用户，true信息完善的用户，false信息不完善的用户，无法进行预约等操作
     */
    private Boolean isPerfectInfo = Boolean.TRUE;

    /**
     * 微信登录唯一标识
     */
    private String openId;

    @JSONField(serialize = false)
    @Override
    public String getPassword() {
        return null;
    }

    /**
     * 手机号登录，用户名为手机号
     * @return
     */
    @Override
    public String getUsername() {
        return isPerfectInfo ? basePersonRecordDO.getPhone() : "";
    }

    /**
     * 账户是否未过期,过期无法验证
     */
    @JSONField(serialize = false)
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * 指定用户是否解锁,锁定的用户无法进行身份验证
     *
     * @return
     */
    @JSONField(serialize = false)
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * 指示是否已过期的用户的凭据(密码),过期的凭据防止认证
     *
     * @return
     */
    @JSONField(serialize = false)
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * 是否可用 ,禁用的用户不能身份验证
     *
     * @return
     */
    @JSONField(serialize = false)
    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }
}
