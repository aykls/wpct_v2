package com.tbxx.wpct.controller;

import com.tbxx.wpct.dto.Result;
import com.tbxx.wpct.entity.SysRole;
import com.tbxx.wpct.entity.SysUser;
import com.tbxx.wpct.service.SysRoleService;
import com.tbxx.wpct.service.impl.SysRoleServicelmpl;
import com.tbxx.wpct.service.impl.SysUserServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin //开放前端的跨域访问
@Api(tags = "角色接口")
@Slf4j
@RestController
@RequestMapping("/role")
public class SysRoleController {

    @Autowired
    private SysRoleServicelmpl roleService;

    /**
     * 新增角色
     */
    @ApiOperation("新增角色")
    @PostMapping("/add")
    public Result insersysUser(@RequestBody SysRole role) {
        return roleService.addRoleAndPerm(role);
    }

    /**
     * 删除角色
     */
    @ApiOperation("删除角色")
    @GetMapping("/remove")
    public Result removeRole(@RequestParam Integer ID) {
        return roleService.deleteRoleAndPerm(ID);
    }


    /**
     * 角色列表
     */
    @ApiOperation("角色列表")
    @GetMapping("/listRole")
    public Result listRole() {
        return roleService.listRole();
    }


    /**
     * 修改角色名&权限
     */
    @ApiOperation("修改角色名&权限")
    @PostMapping("/update")
    public Result updateRoleNameAndPerms(@RequestBody SysRole sysRole) {
        return roleService.updateRoleNameAndPerms(sysRole);
    }

}
