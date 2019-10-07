package com.cloud.order.config;

import com.github.wxpay.sdk.WXPay;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author zf
 * @date 2019-10-07-16:59
 */
@Configuration
public class WXPayConfiguration {

    @Bean
    public PayConfig payConfig(){
        return new PayConfig();
    }



}
