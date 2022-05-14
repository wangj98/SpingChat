package com.wang.springboot.service.Imp;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.wang.springboot.dao.UserDao;
import com.wang.springboot.demain.User;
import com.wang.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImp implements UserService {
    @Autowired
    private UserDao userDao;


    //查询账号密码,返回json数据,当账号密码都正确时,返回用户数据和状态码,否则只返回状态码
    @Override
    public User login(Integer id, String password) {


        QueryWrapper<User> qw2 = new QueryWrapper<>();
        qw2.eq("id", id).eq("password", password);//查询账号密码




        return userDao.selectOne(qw2);


    }

    //查询账号,如果为空返回false
    @Override
    public Boolean userIdVerify(Integer id) {
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("id", id);
        return userDao.selectOne(userQueryWrapper) != null;

    }

    @Override
    public Boolean register(User user) {

        return userDao.insert(user) > 0;
    }

    @Override
    public boolean tokenUpdate(User user) {//更新token
        UpdateWrapper<User> qw = new UpdateWrapper<>();

        qw.eq("id",user.getId());
        qw.set("token",null);

        return userDao.update(null,qw)!=0;//每次登录成功更新token;
    }
}
