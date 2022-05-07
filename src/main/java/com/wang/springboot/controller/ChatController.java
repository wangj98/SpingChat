package com.wang.springboot.controller;

import com.wang.springboot.demain.Message;
import com.wang.springboot.service.WebSocketService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
/*
*
* */
@RestController
@RequestMapping("/pub")
public class ChatController {
    @PostMapping
    public Message privateChat(@RequestBody Message message){

        WebSocketService.sendMessage(message.getText(),WebSocketService.sessionMap.get(message.getToUid()));
        return message;
    }




}
