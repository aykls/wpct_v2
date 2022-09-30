package com.tbxx.wpct.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tbxx.wpct.dto.Result;
import com.tbxx.wpct.entity.SysUser;
import com.tbxx.wpct.mapper.SysUserMapper;
import com.tbxx.wpct.service.SysUserService;
import com.tbxx.wpct.util.constant.SysConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.tbxx.wpct.util.constant.SysConstant.PASSWORD_REGEX;

/**
 * @author ZXX
 * @ClassName SysUserServiceImpl
 * @Description TODO
 * @DATE 2022/9/29 18:35
 */

@Slf4j
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    /**
     * 新增用户
     * @param sysUser 用户信息
     */
    @Override
    @Transactional
    public Result insertUser(SysUser sysUser) {
        String password = sysUser.getPassword();
        String userName = sysUser.getUserName();
        SysUser user_name = query().eq("user_name", userName).one();
        if (StrUtil.isNotBlank(user_name.toString())){
            return Result.fail("用户名已存在");
        }
        if(password == null){
            return Result.fail("密码不能为空");
        }
        boolean flag = password.matches(PASSWORD_REGEX);
        if (!flag){
           return Result.fail("密码格式错误(请用6~20位的数字加字母或下划线)");
        }
        save(sysUser);
        return Result.ok("添加成功");
    }

    /**
     * 删除用户
     * @param ID 用户ID
     */
    @Override
    public Result removeUser(Integer ID) {
        if(ID == null){
            return Result.fail("用户不存在");
        }
        boolean success = removeById(ID);
        if(!success){
            return Result.fail("删除失败,请稍后重试");
        }
        return Result.ok("删除成功");
    }

    @Override
    public Result updateUser(SysUser sysUser) {
//        Integer id = sysUser.getID();
        Integer id =1;
        String password = sysUser.getPassword();
        String nickname=sysUser.getNickName();
        Integer roleId = sysUser.getRoleId();
        boolean flag= true;
        UpdateWrapper<SysUser> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("nickname",nickname).set("role_id",roleId).eq("id",id);
        /**
         * 不修改密码
         */
        if ("".equals(password))
        {
            update(updateWrapper);
            return Result.ok("修改成功");
        }
        /**
         * 修改密码
         */
        if (!password.matches(PASSWORD_REGEX))
        {
            return Result.fail("密码格式至少六位");
        }

        updateWrapper.set("password",password);
        update(updateWrapper);
        return Result.ok("修改成功");
    }
}
