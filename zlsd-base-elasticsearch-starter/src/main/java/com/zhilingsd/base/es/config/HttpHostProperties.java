package com.zhilingsd.base.es.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @program: 智灵时代广州研发中心
 * @description:
 * @author: 吞星(yangguojun)
 * @create: 2020-02-26 18:09
 **/
@ConfigurationProperties(prefix = HttpHostProperties.ES_HOST)
@Data
public class HttpHostProperties {

    public static final String ES_HOST = "host";

    private String ip;

    private String port;

}
