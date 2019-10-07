package com.cloud.order.config;

import com.cloud.utils.IdWorker;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author zf
 * @date 2019-10-07-12:40
 */
@Configuration
@EnableConfigurationProperties(IdWorkerProperties.class)
public class IdWorkerConfig {

    /**
     * 注册Idorker
     * @param prop
     * @return
     */
    @Bean
    public IdWorker idWorker(IdWorkerProperties prop){
        return new IdWorker(prop.getWorkerId(), prop.getDataCenterId());
    }

}
