package com.wang.springboot.service;

import com.wang.springboot.demain.User;

public interface UserService {
    User login(Integer id, String password);
    Boolean userIdVerify(Integer id);
    Boolean register(User user);
    boolean tokenUpdate(User user);
}
