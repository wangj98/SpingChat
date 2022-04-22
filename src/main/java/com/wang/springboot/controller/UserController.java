package com.wang.springboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class UserController {
    @RequestMapping(value = "/users",method = RequestMethod.POST)
    public String save(){
        System.out.println("user save");
        return "user save";
    }
}
