package com.tbxx.wpct.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tbxx.wpct.dto.Result;
import com.tbxx.wpct.entity.BuildInfo;
import com.tbxx.wpct.entity.WechatUser;
import com.tbxx.wpct.mapper.BuildInfoMapper;
import com.tbxx.wpct.mapper.WechatUserMapper;
import com.tbxx.wpct.service.WechatUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springfox.documentation.builders.BuilderDefaults;

import javax.annotation.Resource;
import java.util.List;

import static com.tbxx.wpct.util.constant.SysConstant.*;

/**
 * @author ZXX
 * @ClassName WechatUserServiceImpl
 * @Description
 * @DATE 2022/10/1 17:13
 */

@Slf4j
@Service
public class WechatUserServiceImpl extends ServiceImpl<WechatUserMapper, WechatUser> implements WechatUserService {


    @Resource
    BuildInfoMapper buildInfoMapper;

    @Resource
    WechatUserMapper wechatUserMapper;

     /**
     * 注册
     * QueryWrapper<WechatUser> queryWrapper=new QueryWrapper<>();
     *           queryWrapper.eq("openid",openid);
     *           wechatUserService.update(wechatUser, queryWrapper);
     *           return  Result.ok("注册成功");
     */
    @Override
    public Result register(WechatUser wechatUser, String openid) {
        String phoneNumber = wechatUser.getNumber();
        String pid = wechatUser.getPid();
        if(!phoneNumber.matches(PHONE_REGEX)){
            return Result.fail("手机号格式有误，请重新输入~");
        }
        if(!(pid.matches(PID_REGEX18)||pid.matches(PID_REGEX15))){
            return Result.fail("身份证格式有误，请重新输入~");
        }
        log.warn("手机身份证格式验证");

        //写入用户关联的房屋信息（一个用户可以对应多个房屋 多对多）
        List<BuildInfo> buildInfoList = wechatUser.getBuildInfoList();



        buildInfoMapper.insertBuildInfos(buildInfoList);



        wechatUserMapper.insertBuildAndwechatUser(openid,wechatUser.getBuildInfoList());

        return null ;
    }



}
