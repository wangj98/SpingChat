package com.wang.springboot.service;

import com.wang.springboot.util.ResponseMessage;
import com.wang.springboot.demain.User;

public interface UserService {
    ResponseMessage login(Integer id, String password);
    Boolean userIdVerify(Integer id);
    Boolean register(User user);
}
