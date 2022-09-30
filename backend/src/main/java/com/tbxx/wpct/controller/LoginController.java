package com.tbxx.wpct.controller;

import com.tbxx.wpct.dto.LoginFormDTO;
import com.tbxx.wpct.dto.Result;
import com.tbxx.wpct.service.impl.SysUserServiceImpl;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

/**
 * @author ZXX
 * @ClassName LoginController
 * @Description TODO
 * @DATE 2022/9/30 11:45
 */

@RestController
@RequestMapping("/login")
public class LoginController {
    @Resource
    SysUserServiceImpl userService;

    @PostMapping("/auth")
    public Result authLogin(@RequestBody LoginFormDTO loginForm, HttpSession session){
        return userService.authLogin(loginForm, session);
    }
}
