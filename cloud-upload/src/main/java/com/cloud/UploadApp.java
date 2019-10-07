package com.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author zf
 * @date 2019-09-30-11:06
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableSwagger2
public class UploadApp {

    public static void main(String[] args) {
        SpringApplication.run(UploadApp.class, args);
    }

}
