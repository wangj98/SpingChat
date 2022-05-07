package com.wang.springboot.service;

import com.wang.springboot.demain.User;

public interface UserService {
    Boolean login(Integer id,String password);
    Boolean register(User user);
}
