package com.redisqueue;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by zhangbin on 16/11/22.
 */
public class TestMessage {
    static RedisTemplate redisTemplate;


    public static void main(String[] args) {
        new ClassPathXmlApplicationContext("spring-redis.xml");
        while (true){
            System.out.println("current time: " + new Date());
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    public void handleMessage(Serializable message) {
        if (message == null) {
            System.out.println("null");
        } else if (message.getClass().isArray()) {
            System.out.println(Arrays.toString((Object[]) message));
        } else if (message instanceof List<?>) {
            System.out.println(message);
        } else if (message instanceof Map<?, ?>) {
            System.out.println(message);
        } else {
            System.out.println(message);
        }
    }
}