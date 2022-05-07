package com.wang.springboot.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wang.springboot.demain.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
public interface UserDao extends BaseMapper<User> {

}
