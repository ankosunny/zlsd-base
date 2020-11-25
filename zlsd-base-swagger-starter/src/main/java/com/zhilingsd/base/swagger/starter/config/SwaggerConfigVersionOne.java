package com.zhilingsd.base.swagger.starter.config;

import com.google.common.collect.Lists;
import com.zhilingsd.base.swagger.starter.properties.SwaggerProperties;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.List;

/**
 * @program: 智灵时代广州研发中心
 * @description: swagger配置类
 * @author: 吞星(yangguojun)
 * @create: 2019-04-30 16:49
 **/
@Configuration
@EnableSwagger2
@ConditionalOnProperty(prefix = SwaggerProperties.SWAGGER_PREFIX, name = "whether.open.version.one", havingValue = "true", matchIfMissing = false)
@EnableConfigurationProperties(SwaggerProperties.class)
public class SwaggerConfigVersionOne {

    private SwaggerProperties properties;


    public SwaggerConfigVersionOne(SwaggerProperties properties) {
        this.properties = properties;
    }

    @Bean
    public Docket createRestApi() {
        //添加head参数start
        ParameterBuilder tokenPar = new ParameterBuilder();
        List<Parameter> pars = Lists.newArrayList();
        tokenPar.name("operatorId").defaultValue("1").description("操作人ID").modelRef(new ModelRef("long")).parameterType("header").required(false).build();
        pars.add(tokenPar.build());
        tokenPar.name("collectionCompanyId").defaultValue("0").description("所属机构ID").modelRef(new ModelRef("long")).parameterType("header").required(false).build();
        pars.add(tokenPar.build());
        tokenPar.name("isInner").defaultValue("true").description("是否行内").modelRef(new ModelRef("string")).parameterType("header").required(false).build();
        pars.add(tokenPar.build());
        tokenPar.name("collectionGroupId").defaultValue("0").description("所属业务组ID").modelRef(new ModelRef("long")).parameterType("header").required(false).build();
        pars.add(tokenPar.build());
        tokenPar.name("session").defaultValue("00000000-0000-0000-0000-000000000000").description("登陆Session").modelRef(new ModelRef("string")).parameterType("header").required(false).build();
        pars.add(tokenPar.build());
        //添加head参数end
        return new Docket(DocumentationType.SWAGGER_2)
                .globalOperationParameters(pars)
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
