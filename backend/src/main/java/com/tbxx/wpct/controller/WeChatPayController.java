package com.tbxx.wpct.controller;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.crypto.digest.DigestUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.tbxx.wpct.dto.Result;
import com.tbxx.wpct.entity.WechatUser;
import com.tbxx.wpct.service.impl.WechatPayServiceImpl;
import com.tbxx.wpct.service.impl.WechatUserServiceImpl;
import com.tbxx.wpct.config.WxPayConfig;
import com.tbxx.wpct.util.wx.HttpUtil;
import com.tbxx.wpct.util.wx.WeiXinUtil;
import com.wechat.pay.contrib.apache.httpclient.exception.HttpCodeException;
import com.wechat.pay.contrib.apache.httpclient.exception.NotFoundException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ZXX
 * @ClassName WeChatPayController
 * @Description
 * @DATE 2022/10/6 13:48
 */

@Api(tags = "微信支付测试")
@Slf4j
@RestController
@RequestMapping("/weixin")
@CrossOrigin
public class WeChatPayController {

    @Resource
    WxPayConfig wxPayConfig;

    @Autowired
    WechatPayServiceImpl wechatPayService;

    @Autowired
    WechatUserServiceImpl wechatUserService;


    private String domain = "https://4s3471264h.zicp.fun";   //"http://fjwpct.com";//"http://dadanb.top";
    String app1 = "wxb7756386a217f9f1";
    String app2 = "705ab7713492d438d4181c211e82f0ec";


    @ApiOperation("获取授权码")
    @GetMapping("/auth")
    @CrossOrigin
    public void auth(HttpServletResponse response, HttpServletRequest request) throws IOException {
        //1.
        String openid = request.getParameter("openid");

        System.out.println("我拿到openid是" + openid);

        if (openid == null || openid == "" || openid == "null") { //让用户拿code 就是注册

            //String path = domain + "/#/wxauth";
            String path = domain + "/weixin/callback";//"/#/wxauth"; //"/weixin/callback"; "/#/weixinlogin"   "/auth.html" "/#/wxauth"
            try {
                path = URLEncoder.encode(path, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            String url = "https://open.weixin.qq.com/connect/oauth2/authorize?" +
                    "appid=" + app1 +
                    "&redirect_uri=" + path +
                    "&response_type=code" +
                    "&scope=snsapi_userinfo" +
                    "&state=1" +
                    "#wechat_redirect ";
            response.sendRedirect(url);
        } else { //验证通过 让用户登录
            String url = domain + "/?openid=" + openid + "#/wxauth";
            System.out.println("我要跳转网页是" + url);
            response.sendRedirect(url);
        }

    }


    @ApiOperation("获得微信用户信息")
    @CrossOrigin
    @RequestMapping(value = "/callback", method = RequestMethod.GET)
    public void callback(HttpServletResponse response, HttpServletRequest request) throws IOException {

        log.warn("我带着code来了");
        String code = request.getParameter("code");
        log.warn("我拿到code了==>{}", code);
        // String state = request.getParameter("state");

        /**
         * 获取code后，请求以下链接获取access_token：
         * https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code
         */
        //2.通过code换取网页token
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?" +
                "appid=" + app1 +
                "&secret=" + app2 +
                "&code=" + code +
                "&grant_type=authorization_code";
        String s = HttpUtil.get(url);
        JSONObject object = JSON.parseObject(s);

        log.warn("json是{}", object);

        /**
         {
         "access_token":"ACCESS_TOKEN",
         "expires_in":7200,
         "refresh_token":"REFRESH_TOKEN",
         "openid":"OPENID",
         "scope":"SCOPE"
         }
         */
        String accessToken = object.getString("access_token");
        String openid = object.getString("openid");
        log.warn("token==>{}", accessToken);
        log.warn("openid==>{}", openid);

        //3.根据openid和token获取用户基本信息
        /**
         * http：GET（请使用https协议）
         * https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN
         */
        String userUrl = "https://api.weixin.qq.com/sns/userinfo?" +
                "access_token=" + accessToken +
                "&openid=" + openid +
                "&lang=zh_CN";

        String userRes = WeiXinUtil.httpRequest(userUrl, "GET", null);
        log.warn("用户的信息==>{}", userRes);


        JSONObject jsonObject = JSONObject.parseObject(userRes);
        WechatUser user = wechatUserService.query().eq("openid", openid).one();
        if (user == null) {   //TODO *一个人也可以绑定多个房屋信息 这里逻辑是一个openid（用户）只能绑定一套房屋
            //保存用户信息
            WechatUser wechatUser = new WechatUser();
            wechatUser.setOpenid(jsonObject.getString("openid"));
            wechatUser.setNickname(jsonObject.getString("nickname"));
            wechatUser.setSex(jsonObject.getInteger("sex"));
            wechatUser.setCity(jsonObject.getString("city"));
            wechatUser.setCountry(jsonObject.getString("country"));
            wechatUserService.save(wechatUser);
        }

        if (user == null || user.getPid() == null) {
            //TODO 跳转注册页面
        }
        // TODO 绑定身份证-> 跳转首页


        /*
        String userInfo = HttpUtil.get(userUrl);

        String userRes = new String(userInfo.getBytes("GBK"), "UTF-8");
       // String userRes=new String(userInfo.getBytes("GBK"),"UTF-8");

        //JSONObject user = JSON.parseObject(userInfo);
*/
        //checkService.addCheck(jsonObject);//增加微信用户
        //System.out.println("我捉:" + jsonObject.getString("openid"));
        //System.out.println(userRes);

        userUrl = "https://60z8193p42.goho.co//zqb/new.html" + "?openid=" + jsonObject.getString("openid");
        //userUrl = "https://60z8193p42.goho.co//zqb/new.html" + "?openid=oXXFD6jXgtzCz4GWDJG7jzC11Z0Q";

        //TODO 如果没有授权登录 跳转注册页面  未完成
        response.sendRedirect(userUrl);

    }


    //access_token是公众号的全局唯一接口调用凭据，公众号调用各接口时都需使用access_token。  参考官方文档-->附录一
    @ApiOperation("获取Access token")
    @PostMapping("/jsapi/sdk")
    public Result wechatPaySDK() {
        Gson gson = new Gson();

        // https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET
        String url1 = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + app1 + "&secret=" + app2;
        String resu1 = WeiXinUtil.httpRequest(url1, "GET", null);
        log.warn("结果1是==>{}", resu1);

        HashMap<String, Object> map = gson.fromJson(resu1, HashMap.class);
        String access_token = (String) map.get("access_token");


        // https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token="+access_token+"&type=jsapi";
        url1 = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=" + access_token + "&type=jsapi";
        String resu2 = WeiXinUtil.httpRequest(url1, "GET", null);
        log.warn("结果2是==>{}", resu2);

        HashMap<String, Object> map2 = gson.fromJson(resu2, HashMap.class);
        String ticket = (String) map2.get("ticket");
        String nonceStr = RandomUtil.randomString(32);// 随机字符串
        String timeStamp = String.valueOf(System.currentTimeMillis() / 1000);// 时间戳
        String url = "https://4s3471264h.zicp.fun";   //test

        String jsapi_ticket = "jsapi_ticket=" + ticket + "&noncestr=" + nonceStr + "&timestamp=" + timeStamp + "&url=" + url;

        String resSign = DigestUtil.sha1Hex(jsapi_ticket);
        HashMap<String, Object> respJsonMap = new HashMap();

        respJsonMap.put("appId", app1);
        respJsonMap.put("timestamp", timeStamp);
        respJsonMap.put("nonceStr", nonceStr);
        respJsonMap.put("signature", resSign);

        String respJson = gson.toJson(respJsonMap);

        return Result.ok(respJson);
    }


    @ApiOperation("JSAPI下单")
    @PostMapping("/jsapi/pay")
    public Result wechatPay() throws IOException, NoSuchAlgorithmException, SignatureException, InvalidKeyException {
        log.warn("JSAPI下单");
        String resultJson = wechatPayService.jsapiPay(openid,orderId);
        return Result.ok(resultJson);
    }


    @ApiOperation("支付结果通知")
    @PostMapping("/jsapi/notify")
    public Result wechatPayNotify(HttpServletRequest request, HttpServletResponse response) throws GeneralSecurityException, NotFoundException, IOException, HttpCodeException {
        log.warn("支付结果通知");
        String notify = wechatPayService.payNotify(request, response);
        return Result.ok(notify);
    }

}
