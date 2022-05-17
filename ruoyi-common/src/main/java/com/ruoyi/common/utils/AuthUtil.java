package com.ruoyi.common.utils;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * @author yanfeifan
 * @Package com.ruoyi.common.utils
 * @Description
 * @date 2022/4/11 13:32
 */
@Slf4j
@Data
@Component
public class AuthUtil {

    @Value("${wxconfig.appid}")
    private String APPID;

    @Value("${wxconfig.appsecret}")
    private String APPSECRET;

    /**
     * @Title: getAccessToken
     * @Description: 获取接口调用凭证
     * @param: @return
     * @return: String
     */
    public JSONObject getAccessToken(String code) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + APPID + "&secret=" + APPSECRET + "&code=" + code
                + "&grant_type=authorization_code";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        String body = responseEntity.getBody();
        log.info(body);
        // 返回结果转换为json对象
        JSONObject jObject = JSONObject.parseObject(body);
        return jObject;
    }

    /**
     * 查询微信授权后的用户信息
     *
     * @param accessToken
     * @param openid
     * @return
     */
    public JSONObject getUserInfo(String accessToken, String openid) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://api.weixin.qq.com/sns/userinfo?access_token=" + accessToken + "&openid=" + openid + "&lang=zh_CN";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<String>(headers);
        String body = restTemplate.exchange(url, HttpMethod.GET, entity, String.class).getBody();
        log.info(body);
        // 返回结果转换为json对象
        JSONObject jObject = JSONObject.parseObject(body);
        return jObject;
    }
}
