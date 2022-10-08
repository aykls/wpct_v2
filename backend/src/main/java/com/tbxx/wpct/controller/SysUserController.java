package com.tbxx.wpct.controller;

import com.tbxx.wpct.dto.Result;
import com.tbxx.wpct.entity.SysUser;
import com.tbxx.wpct.service.impl.SysUserServiceImpl;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


/**
 * @author ZXX
 * @ClassName SysUserController
 * @Description
 * @DATE 2022/9/29 21:04
 */

@CrossOrigin //开放前端的跨域访问
@Api(tags = "用户接口")
@Slf4j
@RestController
@RequestMapping("/user")
public class SysUserController {
    @Resource
    private SysUserServiceImpl userService;

    /**
     * 新增用户
     */
    @ApiOperation("新增用户")
    @PostMapping("/add")
    public Result insertUser(@RequestBody SysUser user){
        return userService.insertUser(user);
    }

    /**
     * 删除用户
     */
    @ApiOperation("删除用户")
    @GetMapping("/remove")
    public Result removeUser(@RequestParam Integer ID){
        return userService.removeUser(ID);
    }

    @ApiOperation("更新用户信息")
    @PostMapping("/update")
    public Result updateUser(@RequestBody SysUser user){
        return userService.updateUser(user);
    }

    @ApiOperation("查看用户列表")
    @GetMapping("/list")
    public Result UserList(@RequestParam int pageNum){
        return userService.UserList(pageNum);
    }

}
