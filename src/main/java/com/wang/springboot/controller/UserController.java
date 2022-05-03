package com.wang.springboot.controller;

import com.wang.springboot.demain.SessionRequest;
import com.wang.springboot.service.WebSocketService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/pub")
public class UserController {
    @PostMapping
    public SessionRequest pub(@RequestBody SessionRequest sessionRequest){
        System.out.println(sessionRequest);
        WebSocketService.sendMessage("{\"message\":\"This is test.\"}",WebSocketService.sessionMap.get("test"));
        return sessionRequest;


    }
}
