package com.tbxx.wpct.service;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;

/**
 * @author ZXX
 * @InterfaceName WechatPayService
 * @Description TODO
 * @DATE 2022/10/7 10:23
 */

public interface WechatPayService {
    String  jsapiPay() throws IOException, NoSuchAlgorithmException, SignatureException, InvalidKeyException;
}
