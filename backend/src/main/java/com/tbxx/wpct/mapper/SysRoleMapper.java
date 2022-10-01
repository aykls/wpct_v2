package com.tbxx.wpct.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tbxx.wpct.dto.Result;
import com.tbxx.wpct.entity.SysRole;

public interface SysRoleMapper extends BaseMapper<SysRole> {

    public void addRoleAndPerm(SysRole sysRole);

    public void deleteRoleAndPerm(Integer ID);

    /**
     * 更新权限
     */
    void updateRolePerms();

    /**
     * 角色列表
     */
    void listRole();
}
