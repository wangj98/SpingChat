package com.wang.springboot.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wang.springboot.demain.TokenEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TokenEntityDao extends BaseMapper<TokenEntity> {
}
