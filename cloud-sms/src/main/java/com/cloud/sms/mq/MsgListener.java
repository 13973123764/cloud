package com.cloud.sms.mq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author zf
 * @date 2019-10-04-18:25
 */
@Slf4j
@Component
public class MsgListener {

    @Autowired
    StringRedisTemplate redisTemplate;

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "sms.verify.queue", durable = "true"),
            exchange = @Exchange(name = "cloud.sms.exchange", type = ExchangeTypes.TOPIC),
            key = {"sms.verify.send"}
    ))
    public void sendMsg(Map<String, String> map) throws InterruptedException {
        if(CollectionUtils.isEmpty(map)){
            return;
        }
        String username = map.get("username");
        if(StringUtils.isEmpty(username)){
            return;
        }
//        String code = redisTemplate.opsForValue().get(username);
//        if(!StringUtils.isEmpty(code)){
//            log.error("username =>{}  验证不可重复发送", username);
//            return;
//        }
        redisTemplate.opsForValue().set(username,"6379", 1,TimeUnit.MINUTES);
        log.info("短信发送成功 => {}", username);
        System.out.println("username = " + username);
    }



}
