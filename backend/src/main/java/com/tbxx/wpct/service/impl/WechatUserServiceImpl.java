package com.tbxx.wpct.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.tbxx.wpct.util.constant.SysConstant.*;

/**
 * @Author ZXX
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
     */
    @Override
    public Result register(WechatUser wechatUser) {
        String phoneNumber = wechatUser.getNumber();
        String pid = wechatUser.getPid();
        if(!phoneNumber.matches(PHONE_REGEX)){
            return Result.fail("手机号格式有误，请重新输入~");
        }
        if(!(pid.matches(PID_REGEX18)||pid.matches(PID_REGEX15))){
            return Result.fail("身份证格式有误，请重新输入~");
        }
        log.warn("手机身份证格式验证");

        //用户注册
        List<BuildInfo> buildInfoList = wechatUser.getBuildInfoList();
        buildInfoMapper.insertBuildInfos(buildInfoList);
        //wechatUser表插入
        UpdateWrapper<WechatUser> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("openid",wechatUser.getOpenid());
        WechatUser user = new WechatUser();
        user.setNumber(phoneNumber);
        user.setPid(pid);
        user.setName(wechatUser.getName());
        wechatUserMapper.update(user,updateWrapper);

        return Result.ok("1") ;
    }


    /**
     * 查询用户信息
     */
    @Override
    public Result getInfo(String openid) {
        QueryWrapper<WechatUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("openid",openid);
        WechatUser user = wechatUserMapper.selectOne(queryWrapper);
        String number = user.getNumber();
        String name = user.getName();
        Map map = new HashMap();
        map.put("name",name);map.put("number",number);
        return Result.ok(map);
    }
}
