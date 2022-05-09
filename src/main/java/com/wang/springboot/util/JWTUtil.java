package com.wang.springboot.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Calendar;
import java.util.Map;


public class JWTUtil {
    private static String secret="}daw$^qw^$*&ad*afw";
    /**
    *
    * 生成token
    *
    * */

    public static String InsertToken(Map<String ,String> hashMAp){
        Calendar insCalendar = Calendar.getInstance();//日期处理格式
        insCalendar.add(Calendar.DATE,7);//默认七天过期
        JWTCreator.Builder builder = JWT.create();//创建JWT builder
        hashMAp.forEach((k,v)->{//将传入的值循环插入
            builder.withClaim(k,v);
        });
        String token=builder.withExpiresAt(insCalendar.getTime())//指定指令令牌过期时间
                .sign(Algorithm.HMAC256(secret));//定义的secret

        return token;

    }

    /**
    *
    * 验证token的合法性
    * */
    public static DecodedJWT SelectToken(String token){
        /**
         * - SignatureVerificationException : 签名不一致异常 - TokenExpiredException: 令牌过期异常 -
         * AlgorithmMismatchException: 算法不匹配异常 - InvalidClaimException: 失效的payload异常
         *
         */
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(secret)).build();//传入签名
        DecodedJWT verify = jwtVerifier.verify(token);//传入token
        return verify;

    }

}
