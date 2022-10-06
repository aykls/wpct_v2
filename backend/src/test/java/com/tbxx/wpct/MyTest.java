package com.tbxx.wpct;

import com.tbxx.wpct.wechat.pay.config.WxPayConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @author ZXX
 * @ClassName MyTest
 * @Description TODO
 * @DATE 2022/10/6 16:32
 */

@SpringBootTest
@RunWith(SpringRunner.class)
public class MyTest {

    @Resource
    WxPayConfig wxPayConfig;


    @Test
    public void test(){
        String appSecret = wxPayConfig.getAppSecret();
        String appid = wxPayConfig.getAppid();
        String apiV3Key = wxPayConfig.getApiV3Key();
        String mchSerialNo = wxPayConfig.getMchSerialNo();
        String privateKeyPath = wxPayConfig.getPrivateKeyPath();
        System.out.println(privateKeyPath+apiV3Key);

    }
}
