package com.tbxx.wpct.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tbxx.wpct.entity.WechatUser;
import com.tbxx.wpct.mapper.WechatUserMapper;
import com.tbxx.wpct.service.WechatUserService;
import org.springframework.stereotype.Service;

/**
 * @author ZXX
 * @ClassName WechatUserServiceImpl
 * @Description TODO
 * @DATE 2022/10/1 17:13
 */

@Service
public class WechatUserServiceImpl extends ServiceImpl<WechatUserMapper, WechatUser> implements WechatUserService {
}
