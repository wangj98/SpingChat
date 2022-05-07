package com.wang.springboot.controller;

import com.wang.springboot.controller.util.LoginMessage;
import com.wang.springboot.demain.Message;
import com.wang.springboot.demain.User;
import com.wang.springboot.service.UserService;
import com.wang.springboot.service.WebSocketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;

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

    @PostMapping ("/login")
    public String  login(@RequestBody LoginMessage loginMessage){
        System.out.println(userService.login(loginMessage.getId(),loginMessage.getPassword())?"登录成功":"登陆失败");

        return userService.login(loginMessage.getId(),loginMessage.getPassword())?"登录成功":"登陆失败";
    }


    @PostMapping("/register")
    public String register(@RequestBody User user){
        return userService.register(user)?"注册成功":"注册失败";
    }


    @PostMapping("/exit")
    public String exit(){
        return "成功退出登录";
    }




}
