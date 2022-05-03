package com.wang.springboot.demain;

import lombok.Data;

@Data
public class SessionRequest {
    private Integer uid;
    private Integer cid;
    private String message;

    @Override
    public String toString() {
        return "SessionRequest{" +
                "uid=" + uid +
                ", cid=" + cid +
                ", message='" + message + '\'' +
                '}';
    }
}
