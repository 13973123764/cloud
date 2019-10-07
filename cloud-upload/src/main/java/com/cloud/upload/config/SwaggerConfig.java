package com.cloud.upload.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author zf
 * @date 2019-09-30-11:48
 */
@Configuration
public class SwaggerConfig {

    @Bean
    public Docket api(){
        return new Docket(DocumentationType.SWAGGER_2)
                    .host("localhost:8082")
                    .apiInfo(apiInfo())
                    .select()
                    .apis(RequestHandlerSelectors.basePackage("com.cloud.upload.web"))
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
