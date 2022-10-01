package com.tbxx.wpct.controller;

import com.tbxx.wpct.dto.Result;
import com.tbxx.wpct.entity.SysUser;
import com.tbxx.wpct.service.impl.SysUserServiceImpl;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


/**
 * @author ZXX
 * @ClassName SysUserController
 * @Description TODO
 * @DATE 2022/9/29 21:04
 */

@Slf4j
@RestController
@RequestMapping("/user")
public class SysUserController {
    @Resource
    private SysUserServiceImpl userService;

    /**
     * 新增用户
     */
    @PostMapping("/add")
    public Result insertUser(@RequestBody SysUser user){
        return userService.insertUser(user);
    }

    /**
     * 删除用户
     */
    @GetMapping("/remove")
    public Result removeUser(@RequestParam Integer ID){
        return userService.removeUser(ID);
    }


    @PostMapping("/update")
    public Result updateUser(@RequestBody SysUser user){
        return userService.updateUser(user);
    }

    @GetMapping("/list")
    public Result UserList(){
        return userService.UserDTOList();
    }

}
