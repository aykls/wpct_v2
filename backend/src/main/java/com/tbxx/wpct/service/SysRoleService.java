package com.tbxx.wpct.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tbxx.wpct.dto.Result;
import com.tbxx.wpct.entity.SysRole;
import com.tbxx.wpct.entity.SysUser;
import org.springframework.transaction.annotation.Transactional;

public interface SysRoleService extends IService<SysRole> {

    @Transactional
    Result addRoleAndPerm(SysRole role);

    @Transactional
    Result deleteRoleAndPerm(Integer id);
}
