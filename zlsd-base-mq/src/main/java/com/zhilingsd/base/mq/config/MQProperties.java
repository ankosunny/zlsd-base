package com.zhilingsd.base.mq.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;


/**
 * @author ZhangRong
 * @date 2019-10-17
 * RocketMQ属性
 */
@ConfigurationProperties(MQProperties.PREFIX)
@Data
public class MQProperties {
    public static final String PREFIX = "spring.rocketmq";

    /**
     * nameServer地址
     */
    private String namesrvAddr;

    /**
     * 生产者group
     */
    private String producerGroupName;

    /**
     * 发送超时时间
     */
    private Integer sendMsgTimeout = 3000;

    /**
     * 重试次数
     */
    private Integer retryNum = 3;

}