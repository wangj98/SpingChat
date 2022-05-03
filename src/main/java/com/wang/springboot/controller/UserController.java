package com.wang.springboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/users")
public class UserController {
    @PostMapping
    public String save(){
        System.out.println("user save");
        return "user save";
    }
}
