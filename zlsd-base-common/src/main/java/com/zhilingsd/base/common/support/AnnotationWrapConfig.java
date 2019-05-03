package com.zhilingsd.base.common.support;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: jacky
 * @date: 2018/5/15
 * Time: 17:05
 * Description:
 **/
@Configuration
@EnableConfigurationProperties(AnnotationWrapConfig.class)
public class AnnotationWrapConfig {

    @Bean
    public ResponseBodyWrapFactoryBean getResponseBodyWrap() {
        return new ResponseBodyWrapFactoryBean();
    }

}