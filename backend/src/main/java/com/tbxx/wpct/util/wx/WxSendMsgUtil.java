package com.tbxx.wpct.util.wx;

import cn.hutool.crypto.symmetric.AES;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Maps;
import com.tbxx.wpct.config.WxPayConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.tbxx.wpct.util.constant.RedisConstants.ACCESS_TOKEN;

/**
 * @ClassName WxSendMsgUtil
 * @Description TODO 未完成
 * @Author ZXX
 * @DATE 2022/10/12 16:46
 */

@Slf4j
@Component
@CrossOrigin
public class WxSendMsgUtil {

    @Resource
    private WxPayConfig wxPayConfig;


    /*微信官方换取openid的固定接口*/
    final String CODE2SESSION_URL = "https://api.weixin.qq.com/sns/jscode2session?appid={appId}&secret={appSecret}&js_code={code}&grant_type=authorization_code";

    @Resource
    private RestTemplate restTemplate;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;


    /*获取openid和session_key，参数code是小程序端传过来的*/
    public Map code2Session(String code) throws JsonMappingException, JsonProcessingException {
        Map<String, Object> params = Maps.newHashMap();
        params.put("appId", "wxaf4fff5b42d65d60");
        params.put("appSecret", "4a5b6b0f9d9e7042228a3c6a70d743ed");
        params.put("code", code);

        String url = "https://api.weixin.qq.com/sns/jscode2session?appid=wxaf4fff5b42d65d60&secret=4a5b6b0f9d9e7042228a3c6a70d743ed&js_code=" + code + "&grant_type=authorization_code";
        String s = HttpUtil.get(url);
        JSONObject jsonObject = JSON.parseObject(s);

        log.warn("json是{}", jsonObject);


//        JsonNode json = objectMapper.readTree(response.getBody());
//        Map returnMap=new HashMap();
//        returnMap.put("session_key",json.get("session_key").asText());
//        /*获取到openid*/
//        returnMap.put("openid",json.get("openid").asText());
        return null;
    }

    public String getAccessToken() {
        /*先从缓存中取openid，缓存中取不到 说明已经过期，则重新申请*/
        String accessToken = stringRedisTemplate.opsForValue().get(ACCESS_TOKEN);
        if (accessToken != null) {
            return accessToken;
        }
        Map<String, String> params = new HashMap<>();
        params.put("APPID", wxPayConfig.getAppid());
        params.put("APPSECRET", wxPayConfig.getAppSecret());
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(
                "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid={APPID}&secret={APPSECRET}", String.class, params);
        String body = responseEntity.getBody();
        JSONObject object = JSON.parseObject(body);
        String Access_Token = object.getString("access_token");
        /*access_token有效时长*/
        int expires_in = object.getInteger("expires_in");
        /*过期时间减去10毫秒：10毫秒是网络连接的程序运行所占用的时间*/
        stringRedisTemplate.opsForValue().set(ACCESS_TOKEN, Access_Token, expires_in - 10, TimeUnit.SECONDS);
        return Access_Token;
    }

//    public JsonNode decryptData(String encryptedData, String session_key, String iv) throws IOException {
//        AES aes = new AES();
//        byte[] data = aes.decrypt(Base64.getDecoder().decode(encryptedData), Base64.getDecoder().decode(session_key), Base64.getDecoder().decode(iv));
//        return objectMapper.readTree(data);
//    }
}
