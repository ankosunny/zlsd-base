package com.zhilingsd.base.es.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @program: 智灵时代广州研发中心
 * @description:
 * @author: 吞星(yangguojun)
 * @create: 2020-02-26 18:09
 **/
@ConfigurationProperties(prefix = HttpHostProperties.ES_HOST)
@Configuration
public class HttpHostProperties {

    public static final String ES_HOST = "host";

    private String ip;

    private String port;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }
}
