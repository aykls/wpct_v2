package com.tbxx.wpct.wechat.pay.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tbxx.wpct.dto.Result;
import com.tbxx.wpct.service.impl.WechatPayServiceImpl;
import com.tbxx.wpct.wechat.pay.config.WxPayConfig;
import com.tbxx.wpct.wechat.pay.util.HttpUtil;
import com.tbxx.wpct.wechat.pay.util.WeiXinUtil;
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
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;

/**
 * @author ZXX
 * @ClassName WeChatPayController
 * @Description TODO
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



    private String domain = "https://4s3471264h.zicp.fun";   //"http://fjwpct.com";//"http://dadanb.top";
    String app1 = "wxb7756386a217f9f1";
    String app2 = "705ab7713492d438d4181c211e82f0ec";


    @ApiOperation("获取授权码")
    @GetMapping("/auth")
    @CrossOrigin
    public void auth(HttpServletResponse response, HttpServletRequest request) throws IOException {
        //1.
        String openid = request.getParameter("openid");

        System.out.println("我拿到openid是"+openid);

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
            String url = domain +"/?openid=" + openid+ "#/wxauth";
            System.out.println("我要跳转网页是"+url);
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
        /*
        String userInfo = HttpUtil.get(userUrl);

        String userRes = new String(userInfo.getBytes("GBK"), "UTF-8");
       // String userRes=new String(userInfo.getBytes("GBK"),"UTF-8");

        //JSONObject user = JSON.parseObject(userInfo);
*/

        //checkService.addCheck(jsonObject);//增加微信用户
        //System.out.println("我捉:" + jsonObject.getString("openid"));
        //System.out.println(userRes);
        userUrl = domain + "/?openid=" + jsonObject.getString("openid") + "#/wxauth";
        //注册完毕 让用户验证通过登录
        response.sendRedirect(userUrl);
        //return userUrl;
    }


    @ApiOperation("JSAPI下单")
    @PostMapping("/jsapi/pay")
    public Result wechatPay() throws IOException, NoSuchAlgorithmException, SignatureException, InvalidKeyException {
        log.warn("JSAPI下单");
        String resultjson = wechatPayService.jsapiPay();
        return Result.ok(resultjson);
    }

}
