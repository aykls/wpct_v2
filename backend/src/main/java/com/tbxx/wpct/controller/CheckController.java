package com.tbxx.wpct.controller;

import com.tbxx.wpct.dto.Result;
import com.tbxx.wpct.entity.Consumption;
import com.tbxx.wpct.entity.PayInfo;
import com.tbxx.wpct.entity.SysRole;
import com.tbxx.wpct.service.impl.CheckServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author ZXX
 * @ClassName CheckController
 * @Description TODO
 * @DATE 2022/10/8 21:09
 */

@CrossOrigin //开放前端的跨域访问
@Api(tags = "费用")
@Slf4j
@RestController
@RequestMapping("/check")
public class CheckController {

    @Autowired
    CheckServiceImpl checkService;
    /**
     * 缴费添加
     */
    @ApiOperation("新增缴费")
    @PostMapping("/add")
    public Result addCheck(@RequestBody PayInfo payinfo){
        return checkService.addCheck(payinfo);
    }


    /**
     * 删除缴费
     */
//    @ApiOperation("删除缴费")
//    @PostMapping("/remove")
//    public Result removeCheck(@RequestBody ){
//
//
//        return
//    }

}
