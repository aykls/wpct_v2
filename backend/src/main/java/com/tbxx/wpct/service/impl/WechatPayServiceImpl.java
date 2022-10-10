package com.tbxx.wpct.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.gson.Gson;
import com.tbxx.wpct.entity.OrderInfo;
import com.tbxx.wpct.mapper.OrderInfoMapper;
import com.tbxx.wpct.service.WechatPayService;
import com.tbxx.wpct.util.HttpUtils;
import com.tbxx.wpct.util.OrderNoUtils;
import com.tbxx.wpct.config.WxPayConfig;
import com.wechat.pay.contrib.apache.httpclient.exception.HttpCodeException;
import com.wechat.pay.contrib.apache.httpclient.exception.NotFoundException;
import com.wechat.pay.contrib.apache.httpclient.notification.Notification;
import com.wechat.pay.contrib.apache.httpclient.notification.NotificationHandler;
import com.wechat.pay.contrib.apache.httpclient.notification.NotificationRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ZXX
 * @ClassName WechatPayServiceImpl
 * @Description
 * @DATE 2022/10/7 10:24
 */

@Slf4j
@Service
public class WechatPayServiceImpl implements WechatPayService {

    @Resource
    WxPayConfig wxPayConfig;


    @Resource(name = "WxPayClient")
    private CloseableHttpClient httpClient;


    @Resource
    OrderInfoMapper orderInfoMapper;

    //测试号 appid 和 app
    String app1 = "wxb7756386a217f9f1";
    String app2 = "705ab7713492d438d4181c211e82f0ec";



    /**
     * @return 预支付交易会话标识 prepay_id string[1,64]
     * 请求URL：https://api.mch.weixin.qq.com/v3/pay/transactions/jsapi
     */
    @Override
    public String jsapiPay(String openid, String orderId) throws Exception {
        log.warn("生成订单");

        OrderInfo orderInfo = orderInfoMapper.selectById(orderId);

        log.warn("调用统一下单api");
        HttpPost httpPost = new HttpPost(wxPayConfig.getDomain().concat("/v3/pay/transactions/jsapi"));

        //请求body参数
        Gson gson = new Gson();
        Map<String, Object> paramsMap = new HashMap<>();

        paramsMap.put("appid", wxPayConfig.getAppid());
        paramsMap.put("mchid", wxPayConfig.getMchId());
        paramsMap.put("description", orderInfo.getTitle());
        paramsMap.put("out_trade_no", orderInfo.getOrderNo());   //test
        paramsMap.put("notify_url", "https://4s3471264h.zicp.fun/weixin/jsapi/notify");  //test

        Map amountMap = new HashMap();
        amountMap.put("total", orderInfo.getTotalFee());
        amountMap.put("currency", "CNY");

        Map payerMap = new HashMap();
        payerMap.put("openid", openid);  //test

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
            //存入 预支付交易会话标识 防止调用下单接口
            orderInfo.setPrepayId(prepayId);
            QueryWrapper<OrderInfo> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("id",orderId);
            orderInfoMapper.update(orderInfo,queryWrapper);


//            String paySign = wxPayConfig.getSign(wxPayConfig.getAppid(), timeStamp, nonceStr, prepayId,
//                    wxPayConfig.getPrivateKeyPath());// 签名
            String Sign =  wxPayConfig.getSign(wxPayConfig.getAppid(), Long.parseLong(timeStamp),nonceStr,"prepay_id="+ prepayId);

            resultMap.put("timeStamp", timeStamp);
            resultMap.put("nonceStr", nonceStr);
            resultMap.put("appId", wxPayConfig.getAppid());
            resultMap.put("signType", "RSA");
            resultMap.put("paySign", Sign);

            String resultJson = gson.toJson(resultMap);

            log.warn("resultJson是=====>{}", resultJson);

            return resultJson;
        } finally {
            response.close();
        }
    }


    /**
     * jsapi支付结果
     *
     * @return 通知
     */
    @Override
    public String payNotify(HttpServletRequest request, HttpServletResponse response) throws GeneralSecurityException, NotFoundException, IOException, HttpCodeException {
        Gson gson = new Gson();
        Map<String, String> map = new HashMap<>();  //应答对象  Json格式


        try {
            //处理通知参数
            String body = HttpUtils.readData(request);
            String wechatPaySerial = request.getHeader("Wechatpay-Serial");
            String nonce = request.getHeader("Wechatpay-Nonce");
            String timestamp = request.getHeader("Wechatpay-Timestamp");
            String signature = request.getHeader("Wechatpay-Signature");
            HashMap<String, Object> bodyMap = gson.fromJson(body, HashMap.class);


            String requestId = (String) bodyMap.get("id");
            log.info("支付通知的id ===> {}", requestId);
            log.info("支付通知的完整数据 ===> {}", body);   //对称解密ciphertext

            //构建request，传入必要参数(wxPaySDK0.4.8带有request方式验签的方法 github)
            NotificationRequest Nrequest = new NotificationRequest.Builder()
                    .withSerialNumber(wechatPaySerial)
                    .withNonce(nonce)
                    .withTimestamp(timestamp)
                    .withSignature(signature)
                    .withBody(body)
                    .build();

            NotificationHandler handler = new NotificationHandler(wxPayConfig.getVerifier(), wxPayConfig.getApiV3Key().getBytes(StandardCharsets.UTF_8));
            //验签和解析请求体(只有这里会报错)
            Notification notification = handler.parse(Nrequest);
            log.info("验签成功");

            //从notification获取请求报文(对称解密)
            String plainText = notification.getDecryptData();
            log.info("请求报文===>{}", plainText);
            //将密文转成map 方便拿取
            HashMap resultMap = gson.fromJson(plainText, HashMap.class);
            log.info("请求报文map===>{}", resultMap);


            log.warn("收到支付结果通知，处理订单........");
            //TODO 处理订单
            //////////////////////////////////////////////////

            //成功应答
            response.setStatus(200);
            map.put("code", "SUCCESS");
            map.put("message", "成功");
            return gson.toJson(map);

        } catch (Exception e) {
            e.printStackTrace();
            log.error("验签失败");

            //应答失败
            response.setStatus(500);
            map.put("code", "ERROR");
            map.put("message", "验签失败");
            return gson.toJson(map);
        }

    }



}
