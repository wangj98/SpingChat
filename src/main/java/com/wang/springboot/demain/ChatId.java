package com.wang.springboot.demain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
/*
* 会话表包含两个用户的uid，会话id，创建会话的时间
* */
@Data
@TableName("user_chat_cid")
public class ChatId {
    private Integer cid;
    private Integer uid_a;
    private Integer uid_b;
    private String create_time;

}
