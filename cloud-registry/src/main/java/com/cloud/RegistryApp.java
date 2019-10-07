package com.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @author zf
 * @date 2019-09-27-11:17
 */
@SpringBootApplication
@EnableEurekaServer
public class RegistryApp {

    public static void main(String[] args) {
        SpringApplication.run(RegistryApp.class, args);
    }

}
