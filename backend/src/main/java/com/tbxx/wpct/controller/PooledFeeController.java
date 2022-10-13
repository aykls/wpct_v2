package com.tbxx.wpct.controller;

import com.tbxx.wpct.dto.Result;
import com.tbxx.wpct.entity.Examine;
import com.tbxx.wpct.entity.PooledFee;
import com.tbxx.wpct.service.impl.PooledFeeServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author ZXX
 * @ClassName PooledFeeController
 * @Description TODO
 * @DATE 2022/10/10 19:19
 */

@CrossOrigin 
@Api(tags = "公摊费模块")
@RestController
@RequestMapping("/pooled")
public class PooledFeeController {
    @Autowired
    PooledFeeServiceImpl pooledFeeService;

    /**
     * control  lift_fee为电梯费 water_fee为水费 electricity_fee为电费
     */
    @ApiOperation("公摊列表")
    @GetMapping("/list")
    public Result pooledList(@RequestParam int pageNum){
        return pooledFeeService.pooledList(pageNum);
    }

    @ApiOperation("新增公摊费")
    @PostMapping("/add")
    public Result addpooled(@RequestBody PooledFee pooledFee,@RequestParam String control){
        return pooledFeeService.addpooled(pooledFee,control);
    }

    @ApiOperation("删除公摊费")
    @GetMapping("/remove")
    public Result removepooled(@RequestParam int id){
        return pooledFeeService.removePooled(id);
    }

    @ApiOperation("改公摊费")
    @PostMapping("/update")
    public Result updatepooled(@RequestBody PooledFee pooledFee,@RequestParam String control){
        return pooledFeeService.updatepooled(pooledFee,control);
    }

    @ApiOperation("查某一小区公摊费")
    @PostMapping("/cost")
    public Result singlepooled(@RequestBody String villageName ,@RequestParam String control){
        return pooledFeeService.singlepooled(villageName,control);
    }




}
