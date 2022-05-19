package com.wang.springboot.controller;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class RedisController {

    /**
     * 需要注入redis模板
     *
     * 对于RedisTemplate的泛型情况,
     * 可以使用<String, String>
     *       <Object, Object>
     *       或者不写泛型
     *
     *  注意,属性的名称必须为redisTemplate,因为按名称注入,框架创建的对象就是这个名字的
     */
    @Resource
    private RedisTemplate<String,String> redisTemplate;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    // 添加数据到redis
    @PostMapping("/redis/addString")
    public String addToRedis(String name, String value) {

        // 操作Redis中的string类型的数据,先获取ValueOperation
        ValueOperations valueOperations = redisTemplate.opsForValue();

        // 添加数据到redis
        valueOperations.set(name, value);
        return "向redis添加string类型的数据";
    }

    // 从redis获取数据
    @GetMapping("/redis/getKey")
    public String getData(String key) {

        ValueOperations valueOperations = redisTemplate.opsForValue();
        Object v = valueOperations.get(key);
        return "key是" + key + ",它的值是:" + v;
    }

    @PostMapping("/redis/{k}/{v}")
    public String addStringKV(@PathVariable String k,
                              @PathVariable String v) {

        // 使用StringRedisTemplate对象
        stringRedisTemplate.opsForValue().set(k,v);
        return "使用StringRedisTemplate对象添加";
    }

    @GetMapping("/redis/{k}")
    public String getStringValue(@PathVariable String k) {

        // 获取String类型的value
        String v = stringRedisTemplate.opsForValue().get(k);
        return "从redis中通过" + k + "获取到string类型的v=" + v;
    }
}
