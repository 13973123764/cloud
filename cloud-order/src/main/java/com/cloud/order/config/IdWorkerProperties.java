package com.cloud.order.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author zf
 * @date 2019-10-07-12:38
 */
@Data
@ConfigurationProperties(prefix = "cloud.worker")
public class IdWorkerProperties {

    private long workerId;      
    private long dataCenterId;
}
