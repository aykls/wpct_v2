package com.tbxx.wpct.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tbxx.wpct.dto.Result;
import com.tbxx.wpct.entity.SysRole;
import com.tbxx.wpct.entity.SysUser;
import com.tbxx.wpct.mapper.SysRoleMapper;
import com.tbxx.wpct.service.SysRoleService;
import com.tbxx.wpct.util.OneToMore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

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

    @Autowired
    SysUserServiceImpl sysUserService;

    @Override
    public Result addRoleAndPerm(SysRole role) {

        SysRole role_name = query().eq("role_name", role.getRoleName()).one();
        if(role_name==null)
            return Result.fail("已存在该角色");

        save(role);
        sysRoleMapper.addRoleAndPerm(role);
        return Result.ok("新增成功");
    }

    @Override
    public Result deleteRoleAndPerm(Integer id) {
        /**
         * 删除role表 和role_permission表 有关该角色的信息
         */
        sysRoleMapper.deleteRoleAndPerm(id);
        /**
         * 删除后，修改 该角色对应的用户设为  普通用户角色(默认)
         */
        sysUserService.updateUserRole(id);

        return Result.ok("删除成功");
    }


    /**
     *  角色列表
     */
    @Override
    public Result listRole() {
        List<OneToMore> maps = sysRoleMapper.listRole();
        return Result.ok(maps);
    }

    /**
     * 修改角色名&权限
     */
    @Override
    public Result updateRoleNameAndPerms(SysRole sysRole) {
        String roleName = sysRole.getRoleName();
        if(roleName == null){
            return Result.fail("请填写角色名称");
        }
        update().eq("role_name",roleName);
        //权限列表
        List<String> permsList = sysRole.getPermsList();

        //从角色列表中获取更新前的权限 迭代对比 删除|

        return Result.ok("修改成功");
    }
}
