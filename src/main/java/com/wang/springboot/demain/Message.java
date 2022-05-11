package com.wang.springboot.demain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("user_chat_message")
public class Message {
    /**
    * 发送的信息包含多个数据
     * id表示数据库保存的顺序
    * formUid表示发送方id
    * toUid表示接收方id,
    * cid表示会话id
    * sendTime表示信息发送时间
    * */
    private Integer id;
    private Integer fromUid;
    private Integer toUid;
    private Integer cid;
    private String text;
    private String sendTime;


}
