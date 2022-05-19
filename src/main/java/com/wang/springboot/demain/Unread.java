package com.wang.springboot.demain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("user_chat_unread")
public class Unread {
    private Integer id;
    private Integer last_read_id;
    private Integer to_uid;
    private Integer cid;
}
