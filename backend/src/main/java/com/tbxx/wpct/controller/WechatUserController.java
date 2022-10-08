package com.tbxx.wpct.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tbxx.wpct.dto.Result;
import com.tbxx.wpct.entity.BuildInfo;
import com.tbxx.wpct.entity.WechatUser;
import com.tbxx.wpct.service.impl.BuildInfoServiceImpl;
import com.tbxx.wpct.service.impl.WechatPayServiceImpl;
import com.tbxx.wpct.service.impl.WechatUserServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author ZXX
 * @ClassName WechatUserController
 * @Description
 * @DATE 2022/10/7 18:40
 */
@CrossOrigin //开放前端的跨域访问
@Api(tags = "微信用户")
@Slf4j
@RestController
@RequestMapping("/wenxin")
public class WechatUserController {

    @Autowired
    WechatUserServiceImpl wechatUserService;

    @Autowired
    BuildInfoServiceImpl buildInfoService;


    @ApiOperation("微信用户注册")
    @PostMapping ("/register")
    @CrossOrigin
    public Result register(@RequestBody WechatUser wechatUser,@RequestParam String openid){
          wechatUserService.register(wechatUser,openid);
          return Result.ok();
    }




}
