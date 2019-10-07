package com.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author zf
 * @date 2019-10-03-20:08
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class PageApp {

    public static void main(String[] args) {
        SpringApplication.run(PageApp.class, args);
    }

}
