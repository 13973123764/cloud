package com.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import tk.mybatis.spring.annotation.MapperScan;

import java.util.Arrays;
import java.util.List;

/**
 * @author zf
 * @date 2019-09-27-11:29
 */
@SpringBootApplication
@EnableDiscoveryClient
@MapperScan(basePackages = "com.cloud.item.mapper")
public class ServiceApp {

    public static void main(String[] args) {
        SpringApplication.run(ServiceApp.class, args);
    }

}
