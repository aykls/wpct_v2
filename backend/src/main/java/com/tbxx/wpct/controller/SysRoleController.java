package com.tbxx.wpct.controller;

import com.tbxx.wpct.dto.Result;
import com.tbxx.wpct.entity.SysRole;
import com.tbxx.wpct.entity.SysUser;
import com.tbxx.wpct.service.SysRoleService;
import com.tbxx.wpct.service.impl.SysRoleServicelmpl;
import com.tbxx.wpct.service.impl.SysUserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequestMapping("/role")
public class SysRoleController {

    @Autowired
    private SysRoleServicelmpl roleService;

    /**
     * 新增角色
     */
    @PostMapping("/add")
    public Result insersysUser(@RequestBody SysRole role){
        return roleService.addRoleAndPerm(role);
    }

    /**
     * 删除角色
     */
    @GetMapping("/remove")
    public Result removeUser(@RequestParam Integer ID){
        return roleService.deleteRoleAndPerm(ID);
    }
//
//    @PostMapping("/update")
//    public Result updateUser(@RequestBody SysRole user){
//        return roleService.updateRole(user);
//    }


}
