package com.cloud.order.config;

import com.cloud.order.interceptor.UserInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author zf
 * @date 2019-10-06-19:00
 */
@Configuration
@EnableConfigurationProperties(JwtConfig.class)
public class MvcConfig implements WebMvcConfigurer {

    @Autowired
    JwtConfig jwtConfig;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new UserInterceptor(jwtConfig)).addPathPatterns("/**");
    }
}
