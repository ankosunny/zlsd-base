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

    /**
     * 是否开启VIP通道
     */
    private boolean vipChannelEnabled = false;

    /**
     * 单条消息大小上限
     */
    private int maxMessageSize =1024 * 1024 * 20; // 20M;

}