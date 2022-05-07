package com.wang.springboot.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wang.springboot.demain.ChatId;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ChatIdDao extends BaseMapper<ChatId> {
}
