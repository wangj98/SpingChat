package com.wang.springboot.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wang.springboot.demain.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserDao extends BaseMapper<User> {

}
