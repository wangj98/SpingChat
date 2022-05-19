package com.wang.springboot.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wang.springboot.demain.Unread;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UnreadDao extends BaseMapper<Unread> {
}
