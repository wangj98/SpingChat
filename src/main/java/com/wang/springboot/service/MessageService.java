package com.wang.springboot.service;

import com.wang.springboot.demain.Message;

import java.util.List;

public interface MessageService {
    public boolean insert(Integer fromUid,Integer toUid,String text);
    public List<Message> selectAll(Integer user_a,Integer user_b );
    public List<Message> selectFromTo(Integer fromUid,Integer toUid);

}
