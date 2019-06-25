package com.zhilingsd.base.common.support;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *功能描述:SingleResult注解包装配置类
 * @author: 吞星
 * @date: 2018/5/15
 * Time: 17:05
 **/
@Configuration
@EnableConfigurationProperties(AnnotationWrapConfig.class)
public class AnnotationWrapConfig {

    @Bean
    public SingleResultWrapFactoryBean getResponseBodyWrap() {
        return new SingleResultWrapFactoryBean();
    }


}