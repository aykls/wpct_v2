package com.tbxx.wpct.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tbxx.wpct.dto.Result;
import com.tbxx.wpct.entity.SysRole;
import com.tbxx.wpct.entity.SysUser;
import com.tbxx.wpct.mapper.SysRoleMapper;
import com.tbxx.wpct.service.SysRoleService;
import org.springframework.stereotype.Service;

/**
 * @ClassName SysUserServicelmpl * @Description TODO
 * @Author ZQB
 * @Date 20:29 2022/9/29
 * @Version 1.0
 **/
@Service
public class SysRoleServicelmpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {


    @Override
    public Result insertRole(SysRole role) {
        save(role);
        return Result.ok("新增成功");
    }
}
