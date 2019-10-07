package com.cloud.gateway.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * @author zf
 * @date 2019-10-06-14:37
 */

@Data
@ConfigurationProperties("cloud.filter")
public class FilterProperties {

    private List<String> allowPaths;

}
