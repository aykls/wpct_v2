package com.tbxx.wpct.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tbxx.wpct.dto.Result;
import com.tbxx.wpct.entity.SysRole;
import com.tbxx.wpct.entity.SysUser;

public interface SysRoleService extends IService<SysRole> {


    Result insertRole(SysRole role);
}
