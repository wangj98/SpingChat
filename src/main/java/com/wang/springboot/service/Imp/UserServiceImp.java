package com.wang.springboot.service.Imp;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wang.springboot.dao.UserDao;
import com.wang.springboot.demain.User;
import com.wang.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImp implements UserService {
    @Autowired
    private UserDao userDao;
    @Override
    public Boolean login(Integer id,String password) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        QueryWrapper<User> eq = queryWrapper.eq("id", id).eq("password", password);
        return userDao.selectOne(eq)!=null;
    }

    @Override
    public Boolean register(User user) {
        return userDao.insert(user)>0;
    }
}
