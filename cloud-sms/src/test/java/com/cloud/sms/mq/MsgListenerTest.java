package com.cloud.sms.mq;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zf
 * @date 2019-10-04-18:39
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class MsgListenerTest {

    @Autowired
    AmqpTemplate amqpTemplate;
    @Autowired
    StringRedisTemplate redisTemplate;

//    @Test
//    public void test1(){
//        redisTemplate.opsForValue().set("123","123");
//    }

    @Test
    public void sendMsg() throws InterruptedException {
        Map<String, String> map = new HashMap<>();
        map.put("username","666");
        amqpTemplate.convertAndSend("cloud.sms.exchange","sms.verify.send", map);
        Thread.sleep(10000L);
        System.out.println("最后完  = " );
    }


}