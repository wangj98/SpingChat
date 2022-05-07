package com.wang.springboot.service.Imp;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wang.springboot.dao.ChatIdDao;
import com.wang.springboot.demain.ChatId;
import com.wang.springboot.service.ChatIdService;
import org.springframework.beans.factory.annotation.Autowired;

public class ChatIdServiceImp implements ChatIdService {
    @Autowired
    private ChatIdDao chatIdDao;


    //保存cid前进行一次查询操作,如果不存在，则保存cid
    @Override
    public Boolean  save(Integer uid_a, Integer uid_b) {
        QueryWrapper<ChatId> qw = new QueryWrapper<>();
        if(uid_a>uid_b){
            uid_a=uid_a+uid_b-(uid_b=uid_a);
        }
        qw.eq("uid_a",uid_a).eq("uid_b",uid_b);
        //保存时，uid小的保存在前面
        if(chatIdDao.selectOne(qw)!=null){
            //如果存在不需要添加cid
            return false;
        }else {
            ChatId chatId = new ChatId();
            chatId.setUid_a(uid_a);
            chatId.setUid_b(uid_b);
            chatIdDao.insert(chatId);
            return true;
        }

    }
    //查询cid
    @Override
    public ChatId select(Integer uid_a, Integer uid_b) {
        QueryWrapper<ChatId> qw = new QueryWrapper<>();
        if(uid_a>uid_b){
            uid_a=uid_a+uid_b-(uid_b=uid_a);
        }
        //查询时，小数放前面
        qw.eq("uid_a",uid_a).eq("uid_b",uid_b);

        return chatIdDao.selectOne(qw);
    }
}
