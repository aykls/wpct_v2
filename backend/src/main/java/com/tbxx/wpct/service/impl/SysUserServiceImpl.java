package com.tbxx.wpct.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tbxx.wpct.dto.Result;
import com.tbxx.wpct.entity.SysUser;
import com.tbxx.wpct.mapper.SysUserMapper;
import com.tbxx.wpct.service.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author ZXX
 * @ClassName SysUserServiceImpl
 * @Description TODO
 * @DATE 2022/9/29 18:35
 */

@Slf4j
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {
    @Override
    public Result insertUser(SysUser sysUser) {
        save(sysUser);
        return Result.ok("添加成功");
    }
}
