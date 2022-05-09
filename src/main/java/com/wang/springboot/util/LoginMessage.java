package com.wang.springboot.util;

import lombok.Data;
//登录信息，账号和密码
@Data
public class LoginMessage {
    private Integer id;
    private String password;
}
