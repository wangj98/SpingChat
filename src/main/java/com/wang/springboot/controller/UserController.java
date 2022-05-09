package com.wang.springboot.controller;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.wang.springboot.util.JWTUtil;
import com.wang.springboot.util.LoginMessage;
import com.wang.springboot.util.ResponseMessage;
import com.wang.springboot.demain.User;
import com.wang.springboot.service.UserService;
import com.wang.springboot.service.WebSocketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/*
* 实现登录注册
*
* */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    private WebSocketService webSocketService;

    @PostMapping ("/user/login")
    public ResponseMessage  login(@RequestBody LoginMessage loginMessage){


        return userService.login(loginMessage.getId(),loginMessage.getPassword());
    }

    //访问需要验证
    @PostMapping("/index.html")
    public Integer selectLogin(String token){
        try {
            DecodedJWT decodedJWT = JWTUtil.SelectToken(token);
            return 0;
        } catch (Exception e) {
            e.printStackTrace();
            return 1;
        }


    }



    @PostMapping("/user/register")
    public Boolean register(@RequestBody User user){



        return userService.register(user);
    }


    @PostMapping("/exit")
    public String exit(){



        return "成功退出登录";
    }




}
