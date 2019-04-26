/**
 * Software License Declaration.
 * <p>
 * wandaph.com, Co,. Ltd.
 * Copyright © 2017 All Rights Reserved.
 * <p>
 * Copyright Notice
 * This documents is provided to wandaph contracting agent or authorized programmer only.
 * This source code is written and edited by wandaph Co,.Ltd Inc specially for financial
 * business contracting agent or authorized cooperative company, in order to help them to
 * install, programme or central control in certain project by themselves independently.
 * <p>
 * Disclaimer
 * If this source code is needed by the one neither contracting agent nor authorized programmer
 * during the use of the code, should contact to wandaph Co,. Ltd Inc, and get the confirmation
 * and agreement of three departments managers  - Research Department, Marketing Department and
 * Production Department.Otherwise wandaph will charge the fee according to the programme itself.
 * <p>
 * Any one,including contracting agent and authorized programmer,cannot share this code to
 * the third party without the agreement of wandaph. If Any problem cannot be solved in the
 * procedure of programming should be feedback to wandaph Co,. Ltd Inc in time, Thank you!
 */
package com.zhilingsd.collection.common.support;

import org.apache.ibatis.plugin.Interceptor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author luziliang1
 * @version Id: SnowfakeInterceptorConfig.java, v 0.1 2018/5/4 18:06 luziliang1 Exp $$
 */
@Configuration
@ConditionalOnProperty(prefix = SnowflakeProperties.SNOWFLAKE_PREFIX, value = "url")
@EnableConfigurationProperties(SnowflakeProperties.class)
public class SnowflakeConfig {

    private final SnowflakeProperties properties;

    public SnowflakeConfig(SnowflakeProperties properties) {
        this.properties = properties;
    }

    @Bean
    @LoadBalanced
    public SnowflakeRestTemplate snowflakeRestTemplate() {
        return new SnowflakeRestTemplate();
    }

    @Bean
    @ConditionalOnBean(SnowflakeRestTemplate.class)
    public Interceptor snowfakeInterceptor(SnowflakeRestTemplate restTemplate) {
        return new SnowflakeInterceptor(restTemplate, properties.getUrl());
    }

}