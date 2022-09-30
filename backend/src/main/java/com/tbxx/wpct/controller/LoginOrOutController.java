package com.tbxx.wpct.controller;

import com.tbxx.wpct.dto.LoginFormDTO;
import com.tbxx.wpct.dto.Result;
import com.tbxx.wpct.service.impl.SysUserServiceImpl;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author ZXX
 * @ClassName LoginOrOutController
 * @Description TODO
 * @DATE 2022/9/30 11:45
 */

@RestController
@RequestMapping("/log")
public class LoginOrOutController {
    @Resource
    SysUserServiceImpl userService;

    /**
     * 登录
     */
    @PostMapping("/login")
    public Result authLogin(@RequestBody LoginFormDTO loginForm, HttpSession session){
        return userService.authLogin(loginForm, session);
    }

    /**
     * 登出
     */
    @GetMapping("/logout")
    public Result Logout(HttpServletRequest request){
        return userService.logout(request);
    }
}
