package com.wang.springboot.demain;

import lombok.Data;
//保存token信息
@Data
public class TokenEntity {
    private Long id;
    private Long uerId;
    private int buildTime;
    private String token;
}
