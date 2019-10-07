package com.cloud.auth.service;

import com.cloud.auth.client.UserClient;
import com.cloud.auth.config.JwtConfig;
import com.cloud.auth.entity.UserInfo;
import com.cloud.auth.utils.JwtUtils;
import com.cloud.enums.ExceptionEnum;
import com.cloud.exception.CloudException;
import com.cloud.user.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * @author zf
 * @date 2019-10-05-18:47
 */
@Service
@EnableConfigurationProperties(JwtConfig.class)
public class AuthService {

    @Autowired
    UserClient userClient;
    @Autowired
    JwtConfig jwtConfig;

    public String login(String username, String password) {
        // 校验用户名和密码
        User user = userClient.queryUser(username, password);
        // 判断
        if(null == user){
            throw new CloudException(ExceptionEnum.USER_NOT_FOUND);
        }
        // 生成token
        String token = JwtUtils.generateToken(new UserInfo(user.getId(), username),
                            jwtConfig.getPrivateKey(), jwtConfig.getExpire());
        return token;
    }
}
