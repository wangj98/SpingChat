package com.wang.springboot.service;

import com.wang.springboot.demain.ChatId;

public interface ChatIdService {
    public ChatId  save(Integer uid_a,Integer uid_b);
    public ChatId select(Integer uid_a,Integer uid_b);
}
