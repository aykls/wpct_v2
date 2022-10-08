package com.tbxx.wpct.service;

import com.wechat.pay.contrib.apache.httpclient.exception.HttpCodeException;
import com.wechat.pay.contrib.apache.httpclient.exception.NotFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;

/**
 * @author ZXX
 * @InterfaceName WechatPayService
 * @Description
 * @DATE 2022/10/7 10:23
 */

public interface WechatPayService {
    String  jsapiPay() throws IOException, NoSuchAlgorithmException, SignatureException, InvalidKeyException;

    String payNotify(HttpServletRequest request, HttpServletResponse response) throws GeneralSecurityException, NotFoundException, IOException, HttpCodeException;
}
