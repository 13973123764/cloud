package com.cloud.user.service;

import com.cloud.enums.ExceptionEnum;
import com.cloud.exception.CloudException;
import com.cloud.user.mapper.UserMapper;
import com.cloud.user.pojo.User;
import com.cloud.user.utils.CodecUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author zf
 * @date 2019-10-05-12:29
 */
@Slf4j
@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    AmqpTemplate amqpTemplate;

    public Boolean checkData(String data, Integer type) {
        // 判断数据类型
        User user = new User();
        switch (type){
            case 1:
               user.setUsername(data); break;
            case 2:
                user.setPhone(data); break;
            default:
                throw new CloudException(ExceptionEnum.INVALID_USER_DATA);
        }
        return userMapper.selectCount(user) == 0;
    }


    public void sendCode(String phone) throws InterruptedException {
        // 生成验证码
        String code = "6379";
        Map<String, String > msg = new HashMap<>();
        msg.put("username", phone);
//        msg.put("code", code);

        // 发送验证码
        amqpTemplate.convertAndSend("cloud.sms.exchange", "sms.verify.send", msg);

        // 保存验证码
        redisTemplate.opsForValue().set("code_"+phone,code, 1L, TimeUnit.MINUTES);
        Thread.sleep(5000L);
    }


    public User queryUser(String username, String password) {
        // 根据用户名查询用户
        User user = new User();
        user.setUsername(username);
        User u = userMapper.selectOne(user);
        if(null == u){
            throw new CloudException(ExceptionEnum.USER_NOT_FOUND);
        }
        // 校验密码
        if(!u.getPassword().equals(CodecUtils.md5Hex(password, u.getSalt()))){
            throw new CloudException(ExceptionEnum.INVALID_PASSWORD);
        }
        return u;
    }

    public static void main(String[] args) {
        String pass = CodecUtils.md5Hex("123", "e0e2c30e49ff4a2d8cbc8d8a6cf832fb");
        System.out.println("pass = " + pass);
    }


    @Transactional
    public void registry(User user, String code) {
        String verify = redisTemplate.opsForValue().get("code_" + user.getPhone());
        if(null == verify){
            throw new CloudException(ExceptionEnum.INVALID_CODE);
        }
        if(!verify.equals(code)){
            throw new CloudException(ExceptionEnum.INVALID_CODE);
        }
        user.setId(null);
        user.setCreated(new Date());
        // 生成盐
        String salt = CodecUtils.generateSalt();
        user.setSalt(salt);
        // 对密码进行加密
        user.setPassword(CodecUtils.md5Hex(user.getPassword(), salt));
        int num = userMapper.insertSelective(user);
        if(num != 1){
            throw new CloudException(ExceptionEnum.SYSTEM_ERROR);
        }
        // 删除redis
        try{
            redisTemplate.delete("code_"+user.getPhone());
        }catch (Exception e){
            log.error("删除缓存失败 => {} ", user.getPhone());
        }
    }
}
