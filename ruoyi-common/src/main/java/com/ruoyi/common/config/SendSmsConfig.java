package com.ruoyi.common.config;

import com.aliyun.dysmsapi20170525.Client;
import com.aliyun.dysmsapi20170525.models.SendSmsRequest;
import com.aliyun.dysmsapi20170525.models.SendSmsResponse;
import com.aliyun.teaopenapi.models.Config;
import com.google.gson.Gson;
import com.ruoyi.common.handle.HandleUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author yanfeifan
 * @Package com.ruoyi.common.config
 * @Description 短信服务
 * @date 2022/4/7 10:15
 */
@ConfigurationProperties(prefix = "sms")
@Component
@Data
@Slf4j
public class SendSmsConfig {

    private String accessKeyId;

    private String accessKeySecret;

    //短信签名名称
    private String signName;

    //短信模板CODE
    private String templateCode;

    // 访问的域名
    private String endpoint;

    /**
     * 发送验证码
     *
     * @param phone 手机号
     * @param code  发送验证码
     * @throws Exception
     */
    public String sandSms(String phone, String code) throws Exception {
        Client client = this.createClient(accessKeyId, accessKeySecret);
        SendSmsRequest sendSmsRequest = new SendSmsRequest()
                .setSignName(signName)
                .setTemplateCode(templateCode)
                .setPhoneNumbers(phone)
                .setTemplateParam("{\"code\":\"" + code + "\"}");
        SendSmsResponse resp = client.sendSms(sendSmsRequest);
        log.info(new Gson().toJson(resp.getBody()));
        HandleUtil.serviceExceptionAssert(!"OK".equals(resp.getBody().getCode()), resp.getBody().message, 0001);
        return resp.getBody().message;
    }

    /**
     * 使用AK&SK初始化账号Client
     *
     * @param accessKeyId
     * @param accessKeySecret
     * @return Client
     * @throws Exception
     */
    public Client createClient(String accessKeyId, String accessKeySecret) throws Exception {
        Config config = new Config()
                // AccessKey ID
                .setAccessKeyId(accessKeyId)
                // AccessKey Secret
                .setAccessKeySecret(accessKeySecret);
        // 访问的域名
        config.endpoint = endpoint;
        return new Client(config);
    }
}
