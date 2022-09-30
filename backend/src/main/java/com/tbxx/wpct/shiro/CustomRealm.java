package com.tbxx.wpct.shiro;

import com.tbxx.wpct.dto.Result;
import com.tbxx.wpct.entity.SysUser;
import com.tbxx.wpct.service.SysUserService;
import com.xjt.travel.domain.sysUser;
import com.xjt.travel.service.sysUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.SimpleByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

@Slf4j
public class CustomRealm extends AuthorizingRealm {
    @Autowired
    private SysUserService sysUserService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        log.debug("======doAuthorizationInfo授权=======");

        String principal = (String) principals.getPrimaryPrincipal();
        //2、通过用户名查询所有的权限表达式
        Set<String> permissions = sysUserService.getPermissionsByUsername(principal);
        log.debug("获得权限===>{}",permissions.iterator().toString());

        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        authorizationInfo.setStringPermissions(permissions);
        return authorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        log.debug("======doGetAuthenticationInfo认证=======");

        //从传过来的token获取到的用户名
        String username = (String) token.getPrincipal();
        //根据用户名从数据库获得sysUser对象
        SysUser sysUser = sysUserService.QueryUser(username);


        log.debug("====认证的sysUser===="+sysUser);


            SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(
                    username,
                    sysUser.getPassword(),
                    new SimpleByteSource(sysUser.getSalt()),//未完成
                    this.getName());

//            Session session = SecurityUtils.getSubject().getSession();
//            session.setAttribute("USER_SESSION",sysUser);

            return simpleAuthenticationInfo;



    }

}