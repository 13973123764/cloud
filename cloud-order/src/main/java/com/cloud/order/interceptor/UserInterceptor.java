package com.cloud.order.interceptor;

import com.cloud.auth.entity.UserInfo;
import com.cloud.auth.utils.JwtUtils;
import com.cloud.order.config.JwtConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * @author zf
 * @date 2019-10-06-18:33
 */
@Slf4j
public class UserInterceptor implements HandlerInterceptor {

    JwtConfig jwtConfig;

    public UserInterceptor(JwtConfig jwtConfig){
        this.jwtConfig = jwtConfig;
    }

    // k v 结构 k 为线程本身名称
    private static ThreadLocal<UserInfo> tl = new ThreadLocal<>();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String uri = request.getRequestURI();
        if(uri.contains("swagger") || uri.contains("v2")){
            return true;
        }
//        String token = CookieUtils.getCookieValue(request, jwtConfig.getCookieName());
        String token = "eyJhbGciOiJSUzI1NiJ9" +
                ".eyJpZCI6MjgsInVzZXJuYW1lIjoiMTM5IiwiZXhwIjoxNTcwMzU4MzY1fQ" +
                ".vJhXWHcEbpBtEztovn-C9fVTxF3Q1SfP5c6eizd5lDAjFPkpG3IUXx8JZ0-ZM9eOEGdG6VA_M-IgVmvtWLXVyejAg6rg_cEN9h7jXtNTvr2PzhTFi_XA_oLBTBa7pd4JSd0_oC4vMuaAZUHSMbpBDsy1DJfJu9tdfBjuPwkhEvA";

        System.out.println("jwtConfig.getPublicKey() = " + jwtConfig.getPublicKey());
        
        try{
            // 解析
            UserInfo info = JwtUtils.getUserInfo(jwtConfig.getPublicKey(), token);
            tl.set(info);
            return true;
        } catch (Exception e){
            log.error("解析用户信息出错 => {} ", e.getMessage());
            return false;
        }
    }


    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // 清楚当前线程域中的数据
        tl.remove();
    }

    public static UserInfo getUser(){
        return tl.get();
    }

}
