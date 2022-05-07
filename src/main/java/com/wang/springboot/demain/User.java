package com.wang.springboot.demain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("user")
public class User {
    private Integer id;
    private String email;
    private String sex;
    private String username;
    private String password;
    private String address;
    private Integer age;
}
