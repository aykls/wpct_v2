package com.tbxx.wpct.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tbxx.wpct.dto.Result;
import com.tbxx.wpct.entity.SysRole;
import com.tbxx.wpct.util.OneToMore;

import java.util.List;
import java.util.Map;

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
    List<OneToMore> listRole();
}
