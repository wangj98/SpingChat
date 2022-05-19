package com.wang.springboot.service;

import com.wang.springboot.demain.Unread;

public interface UnreadService {
    public boolean update(Unread unread);
    public boolean insert(Unread unread);
    public boolean select(Unread unread);

}
