package com.tbxx.wpct.mapper;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tbxx.wpct.dto.UserDTO;
import com.tbxx.wpct.entity.SysUser;

import java.util.Set;

/**
 * @author ZXX
 * @InterfaceName SysUserMapper
 * @Description TODO
 * @DATE 2022/9/29 18:37
 */


public interface SysUserMapper extends BaseMapper<SysUser> {

    Set<String> findPermsListByRoleId(UserDTO userDTO);

}
