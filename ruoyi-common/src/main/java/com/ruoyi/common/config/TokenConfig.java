package com.ruoyi.common.config;

import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.core.domain.model.MobileLoginUser;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.common.mobile.login.WxUserInfoVO;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.uuid.IdUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * token验证处理
 *
 * @author ruoyi
 */
@Component
public class TokenConfig {
    // 令牌自定义标识
    @Value("${token.header}")
    private String header;

    // 令牌秘钥
    @Value("${token.secret}")
    private String secret;

    // 令牌有效期（默认30分钟）
    @Value("${token.expireTime}")
    private int expireTime;

    protected static final long MILLIS_SECOND = 1000;

    protected static final long MILLIS_MINUTE = 60 * MILLIS_SECOND;

    private static final Long MILLIS_MINUTE_TEN = 20 * 60 * 1000L;

    @Autowired
    private RedisCache redisCache;

    /**
     * 获取移动端用户身份信息
     *
     * @return 用户信息
     */
    public MobileLoginUser getMobileLoginUser(HttpServletRequest request) {
        // 获取请求携带的令牌
        String token = getToken(request);
        if (StringUtils.isNotEmpty(token)) {
            try {
                Claims claims = parseToken(token);
                // 解析对应的权限以及用户信息
                String uuid = (String) claims.get(Constants.MOBILE_LOGIN_USER_KEY);
                String userKey = getTokenKey(uuid);
                MobileLoginUser user = redisCache.getCacheObject(userKey);
                return user;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 删除用户身份信息
     */
    public void delLoginUser(String token) {
        if (StringUtils.isNotEmpty(token)) {
            String userKey = getTokenKey(token);
            redisCache.deleteObject(userKey);
        }
    }


    /**
     * 创建移动端令牌
     *
     * @param mobileLoginUser 移动端用户信息
     * @return 令牌
     */
    public String createToken(MobileLoginUser mobileLoginUser) {
        String token = IdUtils.fastUUID();
        mobileLoginUser.setToken(token);
        refreshToken(mobileLoginUser);

        Map<String, Object> claims = new HashMap<>();
        claims.put(Constants.MOBILE_LOGIN_USER_KEY, token);
        return createToken(claims);
    }

    /**
     * 验证移动端令牌有效期，相差不足20分钟，自动刷新缓存
     *
     * @param mobileLoginUser
     * @return 令牌
     */
    public void verifyToken(MobileLoginUser mobileLoginUser) {
        long expireTime = mobileLoginUser.getExpireTime();
        long currentTime = System.currentTimeMillis();
        if (expireTime - currentTime <= MILLIS_MINUTE_TEN) {
            refreshToken(mobileLoginUser);
            refreshWxUserInfoVO(mobileLoginUser);
        }
    }

    /**
     * 刷新令牌有效期
     *
     * @param mobileLoginUser 移动端登录信息
     */
    public void refreshToken(MobileLoginUser mobileLoginUser) {
        mobileLoginUser.setLoginTime(System.currentTimeMillis());
        mobileLoginUser.setExpireTime(mobileLoginUser.getLoginTime() + expireTime * MILLIS_MINUTE);
        // 根据uuid将loginUser缓存
        String userKey = Constants.MOBILE_LOGIN_TOKEN_KEY + mobileLoginUser.getToken();
        redisCache.setCacheObject(userKey, mobileLoginUser, expireTime, TimeUnit.MINUTES);
    }

    /**
     * 刷新微信用户信息有效期
     *
     * @return
     */
    public void refreshWxUserInfoVO(MobileLoginUser mobileLoginUser) {
        WxUserInfoVO wxUserInfoVO = redisCache.getCacheObject(Constants.NICK_NAME_KEY + mobileLoginUser.getOpenId());
        redisCache.setCacheObject(Constants.NICK_NAME_KEY + mobileLoginUser.getOpenId(), wxUserInfoVO, expireTime, TimeUnit.MINUTES);
    }


    /**
     * 从数据声明生成令牌
     *
     * @param claims 数据声明
     * @return 令牌
     */
    private String createToken(Map<String, Object> claims) {
        String token = Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, secret).compact();
        return token;
    }

    /**
     * 从令牌中获取数据声明
     *
     * @param token 令牌
     * @return 数据声明
     */
    private Claims parseToken(String token) {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * 从令牌中获取用户名
     *
     * @param token 令牌
     * @return 用户名
     */
    public String getUsernameFromToken(String token) {
        Claims claims = parseToken(token);
        return claims.getSubject();
    }

    /**
     * 获取请求token
     *
     * @param request
     * @return token
     */
    private String getToken(HttpServletRequest request) {
        String token = request.getHeader(header);
        if (StringUtils.isNotEmpty(token) && token.startsWith(Constants.TOKEN_PREFIX)) {
            token = token.replace(Constants.TOKEN_PREFIX, "");
        }
        return token;
    }

    private String getTokenKey(String uuid) {
        return Constants.MOBILE_LOGIN_TOKEN_KEY + uuid;
    }
}
