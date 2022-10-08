package com.tbxx.wpct.controller;

import com.tbxx.wpct.dto.Result;
import com.tbxx.wpct.entity.BuildInfo;
import com.tbxx.wpct.service.impl.BuildInfoServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author ZXX
 * @ClassName BuildInfoController
 * @Description TODO
 * @DATE 2022/10/8 19:42
 */

@CrossOrigin //开放前端的跨域访问
@Api(tags = "房屋信息")
@Slf4j
@RestController
@RequestMapping("/build")
public class BuildInfoController {


    @Autowired
    BuildInfoServiceImpl buildInfoService;


//    @ApiOperation("用户新增绑定房屋信息")
//    @PostMapping("")









//
//    @ApiOperation("用户修改绑定房屋信息")
//    @PostMapping("/update/bind/build")
//    public Result removeBindBuild(@RequestBody int id){
//
//
//    }
//
//    @ApiOperation("用户删除房屋信息")
//    @PostMappideng("/remove/bind/build")
//    public Result addBindBuild(@RequestBody BuildInfo buildInfo){
//                    buildInfoService.
//
//                    }


}
