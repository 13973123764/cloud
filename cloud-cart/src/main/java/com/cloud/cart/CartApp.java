package com.cloud.cart;

import com.cloud.cart.config.JwtConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author zf
 * @date 2019-10-06-16:20
 */
@EnableSwagger2
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class CartApp {

    public static void main(String[] args) {
        SpringApplication.run(CartApp.class, args);
    }
}
