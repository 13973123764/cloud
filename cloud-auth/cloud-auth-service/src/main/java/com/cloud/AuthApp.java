package com.cloud;

import com.cloud.auth.config.JwtConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author zf
 * @date 2019-10-05-17:29
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class AuthApp {

    public static void main(String[] args) {
        SpringApplication.run(AuthApp.class, args);
    }
}
