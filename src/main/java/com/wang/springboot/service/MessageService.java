package com.wang.springboot.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wang.springboot.demain.Message;

import java.util.List;

public interface MessageService {
    public boolean insert(Integer fromUid,Integer toUid,String text);
    public List<Message> selectAll(Integer user_a,Integer user_b );
    public List<Message> selectFromTo(Integer fromUid,Integer toUid);
    public boolean messageInsert(Message message);
    public Page<Message> messageIPage(Integer user_a, Integer user_b, Integer current, Integer size);
    public List<Message> unreadMessage(Integer formUid,Integer toUid);
    public Integer selectLast(Integer cid,Integer toUid);


}
