package com.cloud.auth.controller;

import com.cloud.auth.config.JwtConfig;
import com.cloud.auth.entity.UserInfo;
import com.cloud.auth.service.AuthService;
import com.cloud.auth.utils.JwtUtils;
import com.cloud.utils.CookieUtils;
import com.netflix.client.http.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author zf
 * @date 2019-10-05-18:47
 */
@RestController
public class AuthController {

    @Autowired
    AuthService authService;
    @Value("${cloud.jwt.cookieName}")
    private String cookieName;

    @Autowired
    private JwtConfig jwtConfig;

    /**
     * 登陆授权
     * @param username  用户名
     * @param password  密码
     * @return
     */
    @PostMapping("login")
    public ResponseEntity<Void> login(@RequestParam("username") String username,
                                        @RequestParam("password") String password,
                                        HttpServletResponse response,
                                        HttpServletRequest request
    ){
        // 获取token
        String token = authService.login(username,password);
        // 写入cookies
        CookieUtils.newBuilder(response).httpOnly().request(request)
                .build(cookieName, token);
        System.out.println("token = " + token);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    /**
     * 校验用户登录状态
     * @param token
     * @param request
     * @param response
     * @return
     */
    @GetMapping("verify")
    public ResponseEntity<UserInfo> verify(@CookieValue("CLOUD_TOKEN") String token,
                                            HttpServletRequest request,
                                            HttpServletResponse response){
        UserInfo info = JwtUtils.getUserInfo(jwtConfig.getPublicKey(), token);
        String newToken = JwtUtils.generateToken(info, jwtConfig.getPrivateKey(), jwtConfig.getExpire());
        CookieUtils.newBuilder(response).httpOnly().request(request).build(cookieName, newToken);
        return ResponseEntity.ok(info);
    }


}
