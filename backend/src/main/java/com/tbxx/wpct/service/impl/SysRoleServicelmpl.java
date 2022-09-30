package com.tbxx.wpct.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tbxx.wpct.dto.Result;
import com.tbxx.wpct.entity.SysRole;
import com.tbxx.wpct.entity.SysUser;
import com.tbxx.wpct.mapper.SysRoleMapper;
import com.tbxx.wpct.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @ClassName SysUserServicelmpl * @Description TODO
 * @Author ZQB
 * @Date 20:29 2022/9/29
 * @Version 1.0
 **/
@Service
public class SysRoleServicelmpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {

    @Resource
    SysRoleMapper sysRoleMapper;

    @Override
    public Result insertRole(SysRole role) {

        save(role);
        sysRoleMapper.addRoleAndPerm(role);
        return Result.ok("新增成功");
    }

    @Override
    public Result removeRole(SysRole id) {
        removeById(id);
        return Result.ok("删除成功");
    }
}
