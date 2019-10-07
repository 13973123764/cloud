package com.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @author zf
 * @date 2019-10-05-12:07
 */
@EnableSwagger2
@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.cloud.user.mapper")
public class UserApp {

    public static void main(String[] args) {
        SpringApplication.run(UserApp.class, args);
    }

}
