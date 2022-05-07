package com.wang.springboot.service;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
@ServerEndpoint(value = "/websocket/{uid}")
//@Component
public class WebSocketService {

    private static final Logger log = LoggerFactory.getLogger(WebSocketService.class);
    //记录当前在线连接数
    public static final Map<Integer, Session> sessionMap = new ConcurrentHashMap<>();


    /*
     * 连接建立成功调用的方法
     *
     * */
    @OnOpen
    public void onOpen(Session session, @PathParam("uid") Integer uid) {

        sessionMap.put(uid, session);
        log.info("有新用户加入,uid={},当前在线人数为：{}", uid, sessionMap.size());
        JSONObject result = new JSONObject();//存放所有在线人员
        JSONArray array = new JSONArray();//将所有人放在一个json数组中
        result.set("users", array);
        for (Object key : sessionMap.keySet()) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.set("uid", key);
            array.add(jsonObject);//数组存放的所有人的uid
        }


    }

    /*
     * 收到客户端消息后调用的的方法
     * 后台收到客户端发送过来的消息
     * OnMessage是一个消息的中转站
     * 接受游览器端socket.send发送过来的json数据
     * @param message客户端发送过来的消息
     *
     * */
    /*
    *message 需要是json格式的字符串, {"fromUid":"1","uid":"424","text":"你好啊"}
    * fromUid,cid,text,
    *发给cid房间号的所有uid,当cid中只有一个uid时，即为私聊(暂用uid实现私聊)
    * 存在多个uid即为一个群，遍历发给每个用户
     */
    @OnMessage
    public void OnMessage(String message, Session session, @PathParam("uid") Integer uid) {

        log.info("服务端收到用户uid={}的消息：{}", uid, message);
        JSONObject obj = JSONUtil.parseObj(message);
        Integer toUid = obj.getInt("uid");//接收方的id
        String text = obj.getStr("text");//需要发送的文本
        Session toSession = sessionMap.get(toUid);//通过用户名找到session，通过session发送文本

        if (toSession != null) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.set("from", uid);//发送方的uid
            jsonObject.set("text", text);
            this.sendMessage(message, toSession);
            log.info("用户{}发送给用户{}，消息：{}",uid , toUid, jsonObject.get("text"));

        } else {
            log.info("发送失败,未找到用户Uid={}的session", toUid);
        }

    }

    @OnError
    public void OnError(Session session, Throwable error) {
        log.error("发送错误");
        error.printStackTrace();
    }

    /*
     * 服务端发送消息给客户端
     * */
    public static void sendMessage(String message, Session toSession) {
        try {
            log.info("服务端给客户端[{}]发送消息,消息为{}", toSession.getId(), message);
            toSession.getBasicRemote().sendText(message);

        } catch (IOException e) {
            log.error("服务端发送消息给客户端失败", e);
        }
    }

    public static void sendMessageByUid(String message, Session toSession) {
        try {

            log.info("服务端给客户端[{}]发送消息", toSession.getId(), toSession);
            toSession.getBasicRemote().sendText(message);

        } catch (IOException e) {
            log.error("服务端发送消息给客户端失败", e);
        }
    }
    /*
     *
     * 服务端发送消息给所有客户端
     * */

    private static void sendAllMessage(String message) {
        try {
            for (Session session : sessionMap.values()) {
                log.info("服务端给客户端[{}]发送消息{}", session.getId(), message);
                session.getBasicRemote().sendText(message);
            }
        } catch (IOException e) {
            log.error("服务端发送消息给客户端失败", e);
        }
    }

}

