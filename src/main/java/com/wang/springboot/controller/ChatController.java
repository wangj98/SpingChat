package com.wang.springboot.controller;

import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wang.springboot.demain.Message;
import com.wang.springboot.demain.User;
import com.wang.springboot.service.ChatIdService;
import com.wang.springboot.service.Imp.UserServiceImp;
import com.wang.springboot.service.MessageService;
import com.wang.springboot.service.UserService;
import com.wang.springboot.service.WebSocketService;
import com.wang.springboot.util.SelectPage;
import com.wang.springboot.util.SendResponseMessage;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;

/*
 *
 * */
@RestController
@RequestMapping("/chat/pub")
public class ChatController {
    @Autowired
    private ChatIdService chatIdService;
    @Autowired
    private MessageService messageService;
    @Autowired
    private UserService userService;

    //前端传入的数据:发送方id,接收方id,信息主体text,cid和发送时间为null,后端生成返回
    @PostMapping
    public SendResponseMessage privateChat(@RequestBody Message message) {
        SendResponseMessage sendResponseMessage = new SendResponseMessage();
        JSONObject data = new JSONObject();
        if (message == null) {
            sendResponseMessage.setCode(-1);//未传入任何数据
            return sendResponseMessage;
        }
        if (!(userService.userIdVerify(message.getToUid()))) {
            sendResponseMessage.setCode(-2);//接收方不存在或者已注销
            return sendResponseMessage;

        }

        //传入用户id生成cid返回;
        Integer cid = chatIdService.save(message.getFromUid(), message.getToUid()).getCid();
        message.setCid(cid);

        System.out.println(message);


//        WebSocketService.sendMessage(message.getText(), WebSocketService.sessionMap.get(message.getToUid()));

        sendResponseMessage.setCode(0);//发送成功
        messageService.messageInsert(message);
        data.set("message", message);
        sendResponseMessage.setData(data);
        System.out.println(sendResponseMessage);

        return sendResponseMessage;

    }

    //登录成功后,点击一个用户,展示历史信息,按页查询
    @PostMapping("/historyMessage")
    public Page<Message> historyMessage(@RequestBody SelectPage selectPage) {
        Page<Message> messagePage = messageService.messageIPage(selectPage.getFromUid(), selectPage.getToUid(), selectPage.getCurrent(), selectPage.getSize());
        return messagePage;


    }


    //登录后，展示与某人的未读信息
    @PostMapping("/unreadMessage")
    public List<Message> unreadMessage(@RequestBody Message message) {

        return messageService.unreadMessage(message.getFromUid(), message.getToUid());


    }


}
