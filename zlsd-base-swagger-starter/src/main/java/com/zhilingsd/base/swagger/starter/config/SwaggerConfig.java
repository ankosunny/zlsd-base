package com.zhilingsd.base.swagger.starter.config;

import com.zhilingsd.base.swagger.starter.properties.SwaggerProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
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
 * @program: 智灵时代广州研发中心
 * @description: swagger配置类
 * @author: jacky(yangguojun)
 * @create: 2019-04-30 16:49
 **/
@Configuration
@EnableSwagger2
@ConditionalOnProperty(prefix = SwaggerProperties.SWAGGER_PREFIX,value = "scanPackage")
@EnableConfigurationProperties(SwaggerProperties.class)
public class SwaggerConfig {

    private SwaggerProperties properties;


    public SwaggerConfig(SwaggerProperties properties) {
        this.properties=properties;
    }

    @Bean
    public Docket buildDocket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(buildApiInf())
                .select()
                .apis(RequestHandlerSelectors.basePackage(properties.getScanPackage()))      //要扫描的API(Controller)基础包
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo buildApiInf() {
        return new ApiInfoBuilder()
                .title("智灵时代API文档")
                .contact("zlsd")
                .version("2.0")
                .build();
    }

}
