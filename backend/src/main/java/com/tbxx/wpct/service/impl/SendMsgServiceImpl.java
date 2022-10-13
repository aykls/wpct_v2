package com.tbxx.wpct.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tbxx.wpct.config.WxMsgConfig;
import com.tbxx.wpct.dto.Result;
import com.tbxx.wpct.entity.Consumption;
import com.tbxx.wpct.entity.PayInfo;
import com.tbxx.wpct.entity.WechatUser;
import com.tbxx.wpct.entity.wxpush.WxMsgTemplateHasten;
import com.tbxx.wpct.mapper.PayInfoMapper;
import com.tbxx.wpct.mapper.WechatUserMapper;
import com.tbxx.wpct.service.SendMsgService;
import com.tbxx.wpct.util.wx.WxSendMsgUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName SendMsgServiceImpl
 * @Description TODO
 * @Author ZXX
 * @DATE 2022/10/12 16:23
 */


@Slf4j
@Service
public class SendMsgServiceImpl implements SendMsgService {

    @Resource
    private RestTemplate restTemplate;

    @Resource
    private WxSendMsgUtil wxSendMsgUtil;

    @Resource
    private PayInfoMapper payInfoMapper;

    @Resource
    private WechatUserMapper wechatUserMapper;


    /**
     * 参数拼接
     */
    public WxMsgConfig getMsgConfig(PayInfo payInfo, WechatUser wechatUser) {
        //TODO 这里只是测试
        WxMsgTemplateHasten wxMsgTemplateHasten = new WxMsgTemplateHasten();
        wxMsgTemplateHasten.setTime31(String.valueOf(payInfo.getPayBeginTime()));
        wxMsgTemplateHasten.setThing30(payInfo.getRoomNo());
        wxMsgTemplateHasten.setCharacter_string29(payInfo.getOrderNo());
        wxMsgTemplateHasten.setAmount28("50");
        wxMsgTemplateHasten.setAmount27("0");


        /*消息推送配置参数拼接*/
        WxMsgConfig wxMsgConfig = new WxMsgConfig();
        wxMsgConfig.setTouser(wechatUser.getOpenid());
        wxMsgConfig.setTemplate_id("DBrQ5E3r0dDNxXQqwEtSE7feVps6Qo-IKg8pv3FMy4w");
        wxMsgConfig.setData(wxMsgTemplateHasten);
        return wxMsgConfig;
    }


    /**
     * 发送请求
     */
    public JSONObject postData(String url, WxMsgConfig param) {
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(type);
        HttpEntity<WxMsgConfig> httpEntity = new HttpEntity<>(param, headers);
        JSONObject jsonResult = restTemplate.postForObject(url, httpEntity, JSONObject.class);
        return jsonResult;
    }


    /**
     * 催缴
     */
    @Override
    public Result sendHasten(String openid) {
        QueryWrapper<WechatUser> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("openid",openid);
        WechatUser wechatUser = wechatUserMapper.selectOne(queryWrapper1);

        String pid = wechatUser.getPid();
        QueryWrapper<PayInfo> queryWrapper2 = new QueryWrapper<>();
        queryWrapper2.eq("pid",pid);
        PayInfo payInfo = payInfoMapper.selectOne(queryWrapper2);

        WxMsgConfig requestData = this.getMsgConfig(payInfo,wechatUser);
        log.info("推送消息请求参数：{}", JSON.toJSONString(requestData));

        String url = "https://api.weixin.qq.com/cgi-bin/message/subscribe/send?access_token=" + wxSendMsgUtil.getAccessToken();
        log.info("推送消息请求地址：{}", url);
        JSONObject responseData = postData(url, requestData);
        log.info("推送消息返回参数：{}", JSON.toJSONString(responseData));

        Integer errorCode = responseData.getInteger("errcode");
        String errorMessage = responseData.getString("errmsg");
        if (errorCode == 0) {
            log.info("推送消息发送成功");
            return Result.ok("推送消息发送成功");

        } else {
            log.info("推送消息发送失败,errcode：{},errorMessage：{}", errorCode, errorMessage);
            return Result.ok("推送消息发送失败");
        }
    }


}
