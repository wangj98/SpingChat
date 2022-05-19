package com.wang.springboot.service.Imp;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.wang.springboot.dao.UnreadDao;
import com.wang.springboot.demain.Unread;
import com.wang.springboot.service.UnreadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UnreadServiceImp implements UnreadService {
    @Autowired
    private UnreadDao unreadDao;
    //每次读取未读时，刷新次数
    @Override
    public boolean update(Unread unread) {
        String last_read_id = unread.getLast_read_id().toString();
        UpdateWrapper<Unread> uw = new UpdateWrapper<>();
        uw.set("last_read_id",last_read_id).eq("to_uid",unread.getTo_uid()).eq("cid",unread.getCid());

        return unreadDao.update(null,uw)!=0;
    }

    //第一次查看未读信息,新建插入操作
    @Override
    public boolean insert(Unread unread) {
        return unreadDao.insert(unread)!=0;
    }

//查询unread信息是否存在
    @Override
    public boolean select(Unread unread) {
        QueryWrapper<Unread> qw = new QueryWrapper<>();
        qw.eq("to_uid",unread.getTo_uid()).eq("cid",unread.getCid());
        return unreadDao.selectOne(qw)!=null;
    }
}
