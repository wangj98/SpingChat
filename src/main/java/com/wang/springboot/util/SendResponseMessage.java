package com.wang.springboot.util;

import cn.hutool.json.JSONObject;
import lombok.Data;

@Data
public class SendResponseMessage {
    private Integer code;//发送信息的反馈码
    private JSONObject data;//发送信息反馈
}

