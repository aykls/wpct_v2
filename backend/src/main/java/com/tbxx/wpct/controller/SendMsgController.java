package com.tbxx.wpct.controller;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.Gson;
import com.tbxx.wpct.config.WxPayConfig;
import com.tbxx.wpct.dto.Result;
import com.tbxx.wpct.entity.PayInfo;
import com.tbxx.wpct.entity.WechatUser;
import com.tbxx.wpct.service.impl.SendMsgServiceImpl;
import com.tbxx.wpct.util.wx.WeiXinUtil;
import com.tbxx.wpct.util.wx.WxSendMsgUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author ZXX
 * @ClassName SendMsgController
 * @Description TODO
 * @DATE 2022/10/11 18:34
 */


@CrossOrigin
@Api(tags = "公众号消息推送（催缴）")
@Slf4j
@RestController
@RequestMapping("/news")
public class SendMsgController {

    @Autowired
    SendMsgServiceImpl sendMsgService;

    @GetMapping("/recive")
    public void test0(@RequestParam String code) throws JsonProcessingException {
        log.warn("code是==>{}",code);
        WxSendMsgUtil wxSendMsgUtil =new WxSendMsgUtil();
        Map map = wxSendMsgUtil.code2Session(code);
        log.warn("map是==={}",map);
    }


    @ApiOperation("测试消息推送")
    @PostMapping("/test")
    public Result test(@RequestParam String openid){
        return sendMsgService.sendHasten(openid);
    }





}
