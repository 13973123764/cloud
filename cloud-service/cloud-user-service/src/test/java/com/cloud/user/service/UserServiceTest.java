package com.cloud.user.service;


import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author zf
 * @date 2019-10-05-13:51
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class UserServiceTest {

    @Autowired
    UserService userService;

    @Test
    public void sendCode() throws InterruptedException {
        userService.sendCode("139");


    }
}