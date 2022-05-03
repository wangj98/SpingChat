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
@RestController
@ServerEndpoint(value = "/imsocket/{username}")
//@Component
public class WebSocketService {

    private static final Logger log = LoggerFactory.getLogger(WebSocketService.class);
    //记录当前在线连接数
    public static final Map<String, Session> sessionMap = new ConcurrentHashMap<>();


    /*
     * 连接建立成功调用的方法
     *
     * */
    @OnOpen
    public void onOpen(Session session, @PathParam("username") String username) {

        sessionMap.put(username, session);
        log.info("有新用户加入,username={},当前在线人数为：{}", username, sessionMap.size());
        JSONObject result = new JSONObject();
        JSONArray array = new JSONArray();
        result.set("users", array);
        for (Object key : sessionMap.keySet()) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.set("username", key);
            array.add(jsonObject);
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
    @OnMessage
    public void OnMessage(String message, Session session, @PathParam("username") String username) {
        log.info("服务端收到用户username={}的消息：{}", username, message);
        JSONObject obj = JSONUtil.parseObj(message);
        String toUsername = obj.getStr("test");//发送给某人
        String text = obj.getStr("text");//需要发送的文本
        Session toSession = sessionMap.get(username);//通过用户名找到session，通过session发送文本
        if (toSession != null) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.set("from", username);
            jsonObject.set("text", text);
            this.sendMessage("{\"data\":\"Hello World!\"}", toSession);
            log.info("发送给用户username={}，消息：{}", toUsername, jsonObject.toString());

        } else {
            log.info("发送失败,未找到用户username={}的=session", toUsername);
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
            log.info("服务端给客户端[{}]发送消息", toSession.getId(), message);
            toSession.getBasicRemote().sendText(message);

        } catch (IOException e) {
            log.error("服务端发送消息给客户端失败", e);
        }
    }

    public static void sendMessageByUid(String message, int uid) {
        try {

            log.info("服务端给客户端[{}]发送消息", toSession.getId(), Session toSession);
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

