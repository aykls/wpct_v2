package com.tbxx.wpct.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * @author ZXX
 * @ClassName BuildInfoController
 * @Description TODO
 * @DATE 2022/10/8 19:42
 */

@CrossOrigin //开放前端的跨域访问
@Slf4j
@RestController
@RequestMapping("/")
public class ATestController {
    //网页授权域名测试
    @RequestMapping({"MP_verify_vsgQgCEAiPMzCEMo.txt"})
    private String returnapi(){
        return "vsgQgCEAiPMzCEMo";
    }






}
