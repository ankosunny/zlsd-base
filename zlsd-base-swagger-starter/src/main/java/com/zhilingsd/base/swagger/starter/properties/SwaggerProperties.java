package com.zhilingsd.base.swagger.starter.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @program: 智灵时代广州研发中心
 * @description: swagger配置文件类
 * @author: jacky(yangguojun)
 * @create: 2019-04-30 17:32
 **/
@ConfigurationProperties(prefix = SwaggerProperties.SWAGGER_PREFIX)
public class SwaggerProperties {

    public static final String SWAGGER_PREFIX ="swagger";



    private String scanPackage;

    public String getScanPackage() {
        return scanPackage;
    }

    public void setScanPackage(String scanPackage) {
        this.scanPackage = scanPackage;
    }
}
