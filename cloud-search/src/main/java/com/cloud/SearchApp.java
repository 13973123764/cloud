package com.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author zf
 * @date 2019-10-03-13:32
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class SearchApp {

    public static void main(String[] args) {
        SpringApplication.run(SearchApp.class, args);
    }

}
