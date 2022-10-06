package com.tbxx.wpct.wechat.pay.config;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @author ZXX
 * @ClassName WxPayConfig
 * @Description TODO
 * @DATE 2022/10/6 13:41
 */

@Slf4j
@Configuration
@PropertySource("classpath:wxpay.properties") //读取配置文件
@ConfigurationProperties(prefix = "wxpay") //读取wxpay节点
@Data //使用set方法将wxpay节点中的值填充到当前类的属性中
public class WxPayConfig {

    // 商户号
    private String mchId;

    // 商户API证书序列号
    private String mchSerialNo;

    // 商户私钥文件
    private String privateKeyPath;

    // APIv3密钥
    private String apiV3Key;

    // APPID
    private String appid;

    //APP密钥
    private String appSecret;

    // 微信服务器地址
    private String domain;

    // 接收结果通知地址
    private String notifyDomain;


}
