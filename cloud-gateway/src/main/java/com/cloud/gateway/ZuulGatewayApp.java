package com.cloud.gateway;

import com.cloud.gateway.config.FilterProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * @author zf
 * @date 2019-09-27-11:21
 */
@EnableZuulProxy
@EnableDiscoveryClient
@SpringBootApplication
@EnableConfigurationProperties({FilterProperties.class})
public class ZuulGatewayApp {

    public static void main(String[] args) {
        SpringApplication.run(ZuulGatewayApp.class, args);
    }

}
