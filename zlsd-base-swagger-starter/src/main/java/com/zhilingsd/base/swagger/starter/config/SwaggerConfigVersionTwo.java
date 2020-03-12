package com.zhilingsd.base.swagger.starter.config;

import com.google.common.collect.Lists;
import com.zhilingsd.base.swagger.starter.constants.TechConstant;
import com.zhilingsd.base.swagger.starter.properties.SwaggerProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
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

@EnableSwagger2
@Configuration
@ConditionalOnProperty(prefix = SwaggerProperties.SWAGGER_PREFIX, name = "whether.open.version.two", havingValue = "true", matchIfMissing = false)
public class SwaggerConfigVersionTwo {

    @Value("${zlsd.swagger.tile}")
    private String tile;
    @Value("${zlsd.swagger.description}")
    private String description;
    @Value("${zlsd.swagger.names}")
    private String names;
    @Value("${zlsd.swagger.scanPackage}")
    private String scanPackage;

    @Bean
    public Docket createRestApi() {
        //添加head参数start
        ParameterBuilder tokenPar = new ParameterBuilder();
        List<Parameter> pars = Lists.newArrayList();
        tokenPar.name(TechConstant.OPERATOR_ID).defaultValue("1225844921758253056").description("操作人ID").modelRef(new ModelRef("long")).parameterType("header").required(true).build();
        pars.add(tokenPar.build());
        tokenPar.name(TechConstant.ACCOUNT).defaultValue("test01").description("账号").modelRef(new ModelRef("string")).parameterType("header").required(true).build();
        pars.add(tokenPar.build());
        tokenPar.name(TechConstant.SESSION).defaultValue("00000000-0000-0000-0000-000000000000").description("session").modelRef(new ModelRef("string")).parameterType("header").required(true).build();
        pars.add(tokenPar.build());
        tokenPar.name(TechConstant.MERCHANT_ID).defaultValue("1217176721873043456").description("商户ID").modelRef(new ModelRef("long")).parameterType("header").required(true).build();
        pars.add(tokenPar.build());
        tokenPar.name(TechConstant.PLATFORM).defaultValue("quality").description("平台类型").modelRef(new ModelRef("string")).parameterType("header").required(true).build();
        pars.add(tokenPar.build());
        //添加head参数end
        return new Docket(DocumentationType.SWAGGER_2)
                .globalOperationParameters(pars)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage(scanPackage))
                .paths(PathSelectors.any())
                .build();

    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(tile)
                .description(description)
                .contact(new Contact(names, null, null))
                .version("1.0")
                .build();
    }
}