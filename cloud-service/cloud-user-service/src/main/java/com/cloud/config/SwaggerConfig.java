package com.cloud.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * @author zf
 * @date 2019-10-05-21:04
 */
@Configuration
public class SwaggerConfig {

    @Bean
    public Docket api(){
        return new Docket(DocumentationType.SWAGGER_2)
                .host("localhost:8086")
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.cloud.user.controller"))
                .paths(PathSelectors.any())
                .build();
    }


    public ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                .title("微服务项目")
                .description("文件上传接口文档")
                .version("1.0")
                .build();
    }

}