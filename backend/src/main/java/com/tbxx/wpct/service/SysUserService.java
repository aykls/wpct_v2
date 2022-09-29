package com.tbxx.wpct.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tbxx.wpct.dto.Result;
import com.tbxx.wpct.entity.SysUser;

/**
 * @author ZXX
 * @InterfaceName SysUserService
 * @Description TODO
 * @DATE 2022/9/29 18:24
 */

public interface SysUserService extends IService<SysUser> {

    Result insertUser(SysUser sysUser);

    Result removeUser(Integer ID);
}
