package com.wang.springboot.service.Imp;

import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wang.springboot.util.JWTUtil;
import com.wang.springboot.util.ResponseMessage;
import com.wang.springboot.dao.UserDao;
import com.wang.springboot.demain.User;
import com.wang.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class UserServiceImp implements UserService {
    @Autowired
    private UserDao userDao;


    //查询账号密码,返回json数据,当账号密码都正确时,返回用户数据和状态码,否则只返回状态码
    @Override
    public ResponseMessage login(Integer id, String password) {
        QueryWrapper<User> qw1 = new QueryWrapper<>();
        qw1.eq("id", id);        //查询账号

        QueryWrapper<User> qw2 = new QueryWrapper<>();
        qw2.eq("id", id).eq("password", password);//查询账号密码


        ResponseMessage responseMessage = new ResponseMessage();
        JSONObject data = new JSONObject();

        User user = userDao.selectOne(qw1);


        if (user != null) {//判断账号是否正确
            if (userDao.selectOne(qw2) != null) {//判断密码是否正确
                responseMessage.setCode(10000);//账号密码正确
                data.set("username", user.getUsername());
                data.set("address", user.getAddress());
                data.set("age", user.getAge());
                data.set("sex", user.getSex());
                data.set("email", user.getEmail());

                HashMap<String, String> map = new HashMap<>();
                map.put("id", String.valueOf(id));
                String token = JWTUtil.InsertToken(map);//生成token

                user.setToken(token);
                userDao.updateById(user);//每次登录成功更新token

                data.set("token", token);
                responseMessage.setData(data);

            } else {
                responseMessage.setCode(10001);//密码错误
            }


        } else {
            responseMessage.setCode(10002);//账号错误
        }
        return responseMessage;


    }

    //查询账号
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
}
