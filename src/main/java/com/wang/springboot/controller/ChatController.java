package com.wang.springboot.controller;

import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wang.springboot.demain.Message;
import com.wang.springboot.demain.Unread;
import com.wang.springboot.demain.User;
import com.wang.springboot.service.*;
import com.wang.springboot.service.Imp.UserServiceImp;
import com.wang.springboot.util.SelectPage;
import com.wang.springboot.util.SendResponseMessage;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.websocket.server.PathParam;
import java.util.List;

/*
 *
 * */
@RestController
@RequestMapping("/chat/pub")
public class ChatController {
    /**
     * 需要注入redis模板
     * <p>
     * 对于RedisTemplate的泛型情况,
     * 可以使用<String, String>
     * <Object, Object>
     * 或者不写泛型
     * <p>
     * 注意,属性的名称必须为redisTemplate,因为按名称注入,框架创建的对象就是这个名字的
     */
    @Resource
    private RedisTemplate redisTemplate;

    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private ChatIdService chatIdService;
    @Autowired
    private MessageService messageService;
    @Autowired
    private UserService userService;
    @Autowired
    private UnreadService unreadService;

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
        //发送消息前查看数据库是否有之前unread记录，当不存在记录时插入一条记录,且last_read_id为0;
        Unread unread = new Unread();
        unread.setCid(cid);
        unread.setTo_uid(message.getToUid());
        if (!unreadService.select(unread)) {
            unread.setLast_read_id(0);
            unreadService.insert(unread);

        }

        //拼接用户ID设置为key,存入redis
        String key = message.getFromUid() + "_" + message.getToUid();
        if (stringRedisTemplate.opsForValue().get(key) == null) {//第一次设置为1
            stringRedisTemplate.opsForValue().set(key, "1");
        } else {
            stringRedisTemplate.opsForValue().increment(key);//每次发送自增长
        }


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
    public String unreadMessage(@RequestBody Message message) {
        //将数据库中的last_read_id更新到最近一条
        Integer toUid = message.getToUid();
        Integer cid=chatIdService.select(message.getFromUid(), message.getToUid()).getCid();//查询cid
        Unread unread = new Unread();
        unread.setCid(cid);
        unread.setTo_uid(toUid);
        boolean select = unreadService.select(unread);//查询是否存在该条记录
        if(!select){
            unreadService.insert(unread);//数据库不存在则新建unread记录
        }else {//存在的查询最新记录并更新unread记录
            Integer last_id = messageService.selectLast(cid, toUid);
            unread.setLast_read_id(last_id);
            unreadService.update(unread);
        }

        //读取redis未读数，并将未读数设置为0
        String key = message.getFromUid() + "_" + message.getToUid();
        String num = stringRedisTemplate.opsForValue().get(key);//获取未读数
        stringRedisTemplate.opsForValue().set(key, "0");//读取未读数后。将未读数设置为0
        return num;

    }


}
