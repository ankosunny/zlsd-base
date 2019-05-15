package com.zhilingsd.base.swagger.starter.config;

import com.zhilingsd.base.swagger.starter.properties.SwaggerProperties;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @program: 智灵时代广州研发中心
 * @description: swagger配置类
 * @author: jacky(yangguojun)
 * @create: 2019-04-30 16:49
 **/
@Configuration
@EnableSwagger2
@ConditionalOnProperty(prefix = SwaggerProperties.SWAGGER_PREFIX, name = "whether.open",havingValue = "true",matchIfMissing = true)
@EnableConfigurationProperties(SwaggerProperties.class)
public class SwaggerConfig {

    private SwaggerProperties properties;


    public SwaggerConfig(SwaggerProperties properties) {
        this.properties=properties;
    }

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage(properties.getScanPackage()))
                .paths("true".equals(properties.getFlag()) ? PathSelectors.any() : PathSelectors.none())
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .build();

    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(properties.getTile())
                .description(properties.getDescription())
                .contact(new Contact(properties.getNames(), null, null))
                .version("1.0")
                .build();
    }

}
