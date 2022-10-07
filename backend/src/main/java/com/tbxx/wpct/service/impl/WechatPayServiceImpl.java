package com.tbxx.wpct.service.impl;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.json.JSON;
import cn.hutool.json.JSONObject;
import com.google.gson.Gson;
import com.tbxx.wpct.service.WechatPayService;
import com.tbxx.wpct.util.OrderNoUtils;
import com.tbxx.wpct.wechat.pay.config.WxPayConfig;
import com.wechat.pay.contrib.apache.httpclient.util.PemUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;
import springfox.documentation.spring.web.json.Json;

import javax.annotation.Resource;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ZXX
 * @ClassName WechatPayServiceImpl
 * @Description TODO
 * @DATE 2022/10/7 10:24
 */

@Slf4j
@Service
public class WechatPayServiceImpl implements WechatPayService {

    @Resource
    WxPayConfig wxPayConfig;

    @Resource(name = "WxPayClient")
    private CloseableHttpClient httpClient;


    /**
     * @return 预支付交易会话标识 prepay_id string[1,64]
     * 请求URL：https://api.mch.weixin.qq.com/v3/pay/transactions/jsapi
     */
    @Override
    public String jsapiPay() throws IOException, NoSuchAlgorithmException, SignatureException, InvalidKeyException {

        log.warn("生成订单======test");

        log.warn("调用统一下单api=====test");
        HttpPost httpPost = new HttpPost(wxPayConfig.getDomain().concat("/v3/pay/transactions/jsapi"));

        //请求body参数
        Gson gson = new Gson();
        Map<String, Object> paramsMap = new HashMap<>();

        //
        paramsMap.put("appid", wxPayConfig.getAppid());
        paramsMap.put("mchid", wxPayConfig.getMchId());
        paramsMap.put("description", "wpct支付");
        paramsMap.put("out_trade_no", OrderNoUtils.getOrderNo());
        paramsMap.put("notify_url", "https://4s3471264h.zicp.fun");

        Map amountMap = new HashMap();
        amountMap.put("total", 1);
        amountMap.put("currency", "CNY");

        Map payerMap = new HashMap();
        payerMap.put("openid", "oXXFD6jXgtzCz4GWDJG7jzC11Z0Q");

        paramsMap.put("amount", amountMap);
        paramsMap.put("payer", payerMap);

        //将参数转换成json字符串
        String jsonParams = gson.toJson(paramsMap);
        log.info("请求参数:{}", jsonParams);

        StringEntity entity = new StringEntity(jsonParams, "utf-8");
        entity.setContentType("application/json");
        httpPost.setEntity(entity);
        httpPost.setHeader("Accept", "application/json");

        //完成签名并执行请求
        CloseableHttpResponse response = httpClient.execute(httpPost);

        try {
            String bodyAsString = EntityUtils.toString(response.getEntity());
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == 200) { //处理成功
                log.info("成功, 返回结果 = " + bodyAsString);
            } else if (statusCode == 204) { //处理成功，无返回Body
                log.info("成功");
            } else {
                log.info("JSAPI下单失败,响应码 = " + statusCode + ",返回结果 = " +
                        bodyAsString);
                throw new IOException("request failed");
            }

            String nonceStr = RandomUtil.randomString(32);// 随机字符串
            String timeStamp = String.valueOf(System.currentTimeMillis() / 1000);// 时间戳

            //响应结果
            Map<String, String> resultMap = gson.fromJson(bodyAsString,
                    HashMap.class);
            String prepayId = resultMap.get("prepay_id");

            String paySign = wxPayConfig.getSign(wxPayConfig.getAppid(), timeStamp, nonceStr, prepayId,
                    wxPayConfig.getPrivateKeyPath());// 签名

            resultMap.put("timeStamp", timeStamp);
            resultMap.put("nonceStr", nonceStr);
            resultMap.put("appId", wxPayConfig.getAppid());
            resultMap.put("signType", "RSA");
            resultMap.put("paySign", paySign);

            String resultJson = gson.toJson(resultMap);

            log.warn("resultJson是=====>{}", resultJson);

            return resultJson;
        } finally {
            response.close();
        }
    }


}
