package com.wang.springboot.controller;

import cn.hutool.json.JSONObject;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.wang.springboot.util.JWTUtil;
import com.wang.springboot.util.LoginMessage;
import com.wang.springboot.util.LoginResponseMessage;
import com.wang.springboot.demain.User;
import com.wang.springboot.service.UserService;
import com.wang.springboot.service.WebSocketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

/**
 * 实现登录注册
 *
 * */
@RestController
@RequestMapping()
public class UserController {
    private UserService userService;

    private WebSocketService webSocketService;

    //登录后返回信息,成功登录生成token返回到前端
    @PostMapping("/user/login")
    public LoginResponseMessage login(@RequestBody LoginMessage loginMessage) {
        User user = userService.login(loginMessage.getId(), loginMessage.getPassword());

        LoginResponseMessage loginResponseMessage = new LoginResponseMessage();
        JSONObject data = new JSONObject();


        if (userService.userIdVerify(loginMessage.getId())) {//查询账号是否正确
            if (user != null) {//判断密码是否正确
                loginResponseMessage.setCode(10000);//账号密码正确
                data.set("username", user.getUsername());
                data.set("address", user.getAddress());
                data.set("age", user.getAge());
                data.set("sex", user.getSex());
                data.set("email", user.getEmail());

                HashMap<String, String> map = new HashMap<>();
                map.put("id", String.valueOf(loginMessage.getId()));
                String token = JWTUtil.InsertToken(map);//生成token

                user.setToken(token);
                userService.tokenUpdate(user);


                data.set("token", token);
                loginResponseMessage.setData(data);

            } else {
                loginResponseMessage.setCode(10001);//密码错误
            }

        } else {
            loginResponseMessage.setCode(10002);//账号错误
        }
        return loginResponseMessage;

    }

    //访问需要token验证,传来整个user信息
    @PostMapping("/index.html")
    public Integer selectLogin(@RequestBody User user) {

        try {
            DecodedJWT decodedJWT = JWTUtil.SelectToken(user.getToken());
            return 0;
        } catch (Exception e) {
            e.printStackTrace();
            return 1;
        }


    }

    //注册返回布尔值
    @PostMapping("/user/register")
    public Boolean register(@RequestBody User user) {


        return userService.register(user);
    }

    //前端传来用户信息,后端根据传来的用户信息清除数据库token记录,返回用户信息,下次需要重新登录
    @PostMapping("/exit")
    public User exit(@RequestBody User user) {

        userService.tokenUpdate(user);

        return user;
    }


}
