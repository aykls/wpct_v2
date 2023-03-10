package com.tbxx.wpct.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.tbxx.wpct.dto.Result;
import com.tbxx.wpct.dto.SR;
import com.tbxx.wpct.entity.WechatUser;
import springfox.documentation.builders.BuilderDefaults;

import java.util.List;

/**
 * @Author ZXX
 * @InterfaceName WechatUserService
 * @Description
 * @DATE 2022/10/1 17:13
 */

public interface WechatUserService extends IService<WechatUser> {

    Result register(WechatUser wechatUser);

    Result getInfo(String openid);

    SR getInfoToBackend();
}
