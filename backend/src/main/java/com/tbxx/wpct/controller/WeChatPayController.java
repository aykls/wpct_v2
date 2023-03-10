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
import com.tbxx.wpct.util.HttpUtils;
import com.tbxx.wpct.util.wx.HttpUtil;
import com.tbxx.wpct.util.wx.WeiXinUtil;
import com.wechat.pay.contrib.apache.httpclient.exception.HttpCodeException;
import com.wechat.pay.contrib.apache.httpclient.exception.NotFoundException;
import com.wechat.pay.contrib.apache.httpclient.exception.ParseException;
import com.wechat.pay.contrib.apache.httpclient.exception.ValidationException;
import com.wechat.pay.contrib.apache.httpclient.notification.Notification;
import com.wechat.pay.contrib.apache.httpclient.notification.NotificationHandler;
import com.wechat.pay.contrib.apache.httpclient.notification.NotificationRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static com.tbxx.wpct.util.constant.RedisConstants.ACCESS_TOKEN;
import static com.tbxx.wpct.util.constant.RedisConstants.ACCESS_TOKEN_TTL;

/**
 * @Author ZXX
 * @ClassName WeChatPayController
 * @Description
 * @DATE 2022/10/6 13:48
 */

@Api(tags = "????????????JSAPI  ")
@Slf4j
@RestController
@RequestMapping("/weixin")
@CrossOrigin
public class WeChatPayController {

    @Resource
    private  WxPayConfig wxPayConfig;

    @Autowired
    private  WechatPayServiceImpl wechatPayService;

    @Autowired
    private  WechatUserServiceImpl wechatUserService;




    private String domain = "https://4s3471264h.zicp.fun";  //"http://fjwpct.com";  //"http://dadanb.top";  //"https://4s3471264h.zicp.fun";

    String app1 = "wxb7756386a217f9f1";
    String app2 = "705ab7713492d438d4181c211e82f0ec";


    @ApiOperation("???????????????")
    @GetMapping("/auth")
    @CrossOrigin
    public void auth(HttpServletResponse response, HttpServletRequest request) throws IOException {
        //1.
        String openid = request.getParameter("openid");

        System.out.println("?????????openid???" + openid);

        if (openid == null || openid == "" || openid == "null") { //????????????code ????????????

            //String path = domain + "/#/wxauth";
            String path = domain + "/weixin/callback";//"/#/wxauth"; //"/weixin/callback"; "/#/weixinlogin"   "/auth.html" "/#/wxauth"
            try {
                path = URLEncoder.encode(path, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            String url = "https://open.weixin.qq.com/connect/oauth2/authorize?" +
                    "appid=" + wxPayConfig.getAppid() +
                    "&redirect_uri=" + path +
                    "&response_type=code" +
                    "&scope=snsapi_userinfo" +
                    "&state=1" +
                    "#wechat_redirect ";
            response.sendRedirect(url);
        } else { //???????????? ???????????????
            String url = domain + "/?openid=" + openid + "#/wxauth";
            System.out.println("?????????????????????" + url);
            response.sendRedirect(url);
        }

    }


    @ApiOperation("????????????????????????")
    @CrossOrigin
    @RequestMapping(value = "/callback", method = RequestMethod.GET)
    public Result callback(HttpServletResponse response, HttpServletRequest request) throws IOException {

        log.warn("?????????code??????");
        String code = request.getParameter("code");
        log.warn("?????????code???==>{}", code);
        // String state = request.getParameter("state");

        /**
         * ??????code??????????????????????????????access_token???
         * https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code
         */
        //2.??????code????????????token
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?" +
                "appid=" + wxPayConfig.getAppid() +
                "&secret=" + wxPayConfig.getAppSecret() +
                "&code=" + code +
                "&grant_type=authorization_code";
        String s = HttpUtil.get(url);
        JSONObject object = JSON.parseObject(s);

        log.warn("json???{}", object);

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

        //3.??????openid???token????????????????????????
        /**
         * http???GET????????????https?????????
         * https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN
         */
        String userUrl = "https://api.weixin.qq.com/sns/userinfo?" +
                "access_token=" + accessToken +
                "&openid=" + openid +
                "&lang=zh_CN";

        // String userRes = WeiXinUtil.httpRequest(userUrl, "GET", null);
        String userRes = HttpUtil.get(userUrl);
        log.warn("???????????????==>{}", userRes);

        //???openid???????????????  10-19
        Gson gson = new Gson();
        HashMap getMap = gson.fromJson(userRes, HashMap.class);
        String getOpenid = (String) getMap.get("openid");
        Map<String,String> rMap = new HashMap<>();
        rMap.put("openid",getOpenid);
        log.warn("??????????????????====>{}",rMap);


        JSONObject jsonObject = JSONObject.parseObject(userRes);
        WechatUser user = wechatUserService.query().eq("openid", openid).one();
        if (user == null) {   //TODO *?????????????????????????????????????????? ?????????????????????openid????????????????????????????????????
            //??????????????????
            WechatUser wechatUser = new WechatUser();
            wechatUser.setOpenid(jsonObject.getString("openid"));
            wechatUser.setNickname(jsonObject.getString("nickname"));
            wechatUserService.save(wechatUser);
        }
//        if (user == null || user.getPid() == null) {
//            //TODO ??????????????????
//        }
//        // TODO ???????????????-> ????????????


        userUrl = "https://60z8193p42.goho.co//zqb/new.html" + "?openid=" + jsonObject.getString("openid");

        //TODO ???????????????????????? ??????????????????  ?????????
        response.sendRedirect(userUrl);
        return Result.ok(rMap);
    }


    /**
     * ??????????????? wx.config????????????
     */
    @ApiOperation("??????jsapiSDK")
    @PostMapping("/jsapi/sdk")
    public Result wechatPaySDK() {
        Gson gson = new Gson();

        // https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET
        String url1 = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + wxPayConfig.getAppid() + "&secret=" + wxPayConfig.getAppSecret();
        String resu1 = WeiXinUtil.httpRequest(url1, "GET", null);
        log.warn("??????1???==>{}", resu1);

        HashMap<String, Object> map = gson.fromJson(resu1, HashMap.class);
        String access_token = (String) map.get("access_token");
        log.warn("access_token{}",access_token);



        // https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token="+access_token+"&type=jsapi";
        url1 = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=" + access_token + "&type=jsapi";
        String resu2 = WeiXinUtil.httpRequest(url1, "GET", null);
        log.warn("??????2???==>{}", resu2);

        HashMap<String, Object> map2 = gson.fromJson(resu2, HashMap.class);
        String ticket = (String) map2.get("ticket");
        String nonceStr = RandomUtil.randomString(32);// ???????????????
        String timeStamp = String.valueOf(System.currentTimeMillis() / 1000);// ?????????
        String url = "https://4s3471264h.zicp.fun";   //test
        //String url = "http://fjwpct.com";

        String jsapi_ticket = "jsapi_ticket=" + ticket + "&noncestr=" + nonceStr + "&timestamp=" + timeStamp + "&url=" + url;

        String resSign = DigestUtil.sha1Hex(jsapi_ticket);
        HashMap<String, Object> respJsonMap = new HashMap();

        respJsonMap.put("appId", wxPayConfig.getAppid());
        respJsonMap.put("timestamp", timeStamp);
        respJsonMap.put("nonceStr", nonceStr);
        respJsonMap.put("signature", resSign);

        String respJson = gson.toJson(respJsonMap);

        return Result.ok(respJson);
    }


    @ApiOperation("JSAPI??????")
    @PostMapping("/jsapi/pay")
    public Result wechatPay(@RequestParam String openid, @RequestParam(name = "id") String orderId) throws Exception {
        log.warn("JSAPI??????");
        String resultJson = wechatPayService.jsapiPay(openid, orderId);
        return Result.ok(resultJson);
    }


    @ApiOperation("??????????????????")
    @PostMapping("/jsapi/notify")
    public Result wechatPayNotify(HttpServletRequest request, HttpServletResponse response) throws GeneralSecurityException, NotFoundException, IOException, HttpCodeException {
        log.warn("??????????????????");
        String notify = wechatPayService.payNotify(request, response);
        return Result.ok(notify);
    }


    /**
     * ??????????????????
     * //TODO ?????????????????????
     */
    @ApiOperation("??????????????????")
    @PostMapping("/cancel/{orderNo}")
    public Result cancel(@PathVariable String orderNo) throws Exception {
        log.info("????????????");
        wechatPayService.cancelOrder(orderNo);
        return Result.ok("???????????????");
    }

    /**
     * ????????????
     */
    @ApiOperation("????????????????????????????????????")
    @GetMapping("query/{orderNo}")
    public Result queryOrder(@PathVariable String orderNo) throws IOException {
        log.info("????????????");
        String bodyAsString = wechatPayService.queryOrder(orderNo);
        return Result.ok("????????????", bodyAsString);
    }


    @ApiOperation("????????????")
    @PostMapping("/refunds/{orderNo}/{reason}/{refundFee}")
    public Result refunds(@PathVariable String orderNo, @PathVariable String reason, @PathVariable Integer refundFee)
            throws Exception {
        log.info("????????????");
        wechatPayService.refund(orderNo, reason,refundFee);
        return Result.ok();
    }

    /**
     * ????????????
     *
     * @param refundNo
     * @return
     * @throws Exception
     */
    @ApiOperation("????????????????????????")
    @GetMapping("/query-refund/{refundNo}")
    public Result queryRefund(@PathVariable String refundNo) throws Exception {
        log.info("????????????");
        String result = wechatPayService.queryRefund(refundNo);
        return Result.ok(result);
    }


    /**
     * ??????????????????
     * ????????????????????????????????????????????????????????????????????????
     */
    @ApiOperation("??????????????????")
    @PostMapping("/refunds/notify")
    public String refundsNotify(HttpServletRequest request, HttpServletResponse response) throws ValidationException, ParseException {

        log.info("??????????????????");
        Gson gson = new Gson();
        Map<String, String> map = new HashMap<>(); //????????????

        try {
            //??????????????????
            String body = HttpUtils.readData(request);
            String wechatPaySerial = request.getHeader("Wechatpay-Serial");
            String nonce = request.getHeader("Wechatpay-Nonce");
            String timestamp = request.getHeader("Wechatpay-Timestamp");
            String signature = request.getHeader("Wechatpay-Signature");
            HashMap<String, Object> bodyMap = gson.fromJson(body, HashMap.class);
            String requestId = (String) bodyMap.get("id");
            log.info("?????????????????? ===> {}", requestId);
            log.info("??????????????????????????? ===> {}", body);   //????????????ciphertext

            //??????request?????????????????????(wxPaySDK0.4.8??????request????????????????????? github)
            NotificationRequest Nrequest = new NotificationRequest.Builder()
                    .withSerialNumber(wechatPaySerial)
                    .withNonce(nonce)
                    .withTimestamp(timestamp)
                    .withSignature(signature)
                    .withBody(body)
                    .build();

            NotificationHandler handler = new NotificationHandler(wxPayConfig.getVerifier(), wxPayConfig.getApiV3Key().getBytes(StandardCharsets.UTF_8));

            //????????????????????????(?????????????????????)
            Notification notification = handler.parse(Nrequest);
            log.info("????????????");

            //???notification??????????????????(????????????)
            String plainText = notification.getDecryptData();
            log.info("????????????===>{}", plainText);
            //???????????????map ????????????
            HashMap resultMap = gson.fromJson(plainText, HashMap.class);
            log.info("????????????map===>{}", resultMap);

            //???????????????
            wechatPayService.processRefund(resultMap);

            //????????????
            response.setStatus(200);
            map.put("code", "SUCCESS");
            map.put("message", "??????");
            return gson.toJson(map);
        } catch (Exception e) {
            e.printStackTrace();
            //????????????
            response.setStatus(500);
            map.put("code", "ERROR");
            map.put("message", "??????");
            return gson.toJson(map);
        }

    }

    @ApiOperation("????????????url????????????")
    @GetMapping("/querybill/{billDate}/{type}")   //type???tradebill|fundflowbill
    public Result queryTradeBill(@PathVariable String billDate, @PathVariable String type) throws Exception {
        log.info("????????????url");
        String downloadUrl = wechatPayService.queryBill(billDate, type);
        return Result.ok("????????????url??????", downloadUrl);
    }

    @ApiOperation("????????????")
    @GetMapping("/downloadbill/{billDate}/{type}")
    public Result downloadBill(@PathVariable String billDate, @PathVariable String type) throws Exception {
        log.info("????????????");
        String result = wechatPayService.downloadBill(billDate, type);
        return Result.ok(result);
    }

}
