package com.wang.springboot.util;

import cn.hutool.json.JSONObject;
import lombok.Data;
//登录返回信息
@Data
public class LoginResponseMessage {
    private Integer code;
    private JSONObject data;

}
