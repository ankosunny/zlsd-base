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
package com.zhilingsd.base.mq.config;

import com.zhilingsd.base.mq.producer.Producer;
import com.zhilingsd.base.mq.producer.RocketMqProducer;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author ZhangRong
 * @date 2019-10-17
 * RocketMQ自动配置
 */
@Slf4j
@Configuration
@ConditionalOnBean(MQBaseAutoConfiguration.class)
@ConditionalOnProperty(MQProducerAutoConfiguration.PRODUCER_GROUP_NAME_PROPERTY)
public class MQProducerAutoConfiguration extends MQBaseAutoConfiguration {

    protected static final String PRODUCER_GROUP_NAME_PROPERTY = "spring.rocketmq.producerGroupName";

    /**
     * 初始化向rocketmq发送普通消息的生产者
     */
    @Bean
    public DefaultMQProducer defaultProducer() throws MQClientException {
        /**
         * 一个应用创建一个Producer，由应用来维护此对象，可以设置为全局对象或者单例<br>
         * 注意：ProducerGroupName需要由应用来保证唯一<br>
         * ProducerGroup这个概念发送普通的消息时，作用不大，但是发送分布式事务消息时，比较关键，
         * 因为服务器会回查这个Group下的任意一个Producer
         */
        DefaultMQProducer producer = new DefaultMQProducer(mqProperties.getProducerGroupName());
        producer.setNamesrvAddr(mqProperties.getNamesrvAddr());
        producer.setSendMsgTimeout(mqProperties.getSendMsgTimeout());

        /**
         * Producer对象在使用之前必须要调用start初始化，初始化一次即可<br>
         * 注意：切记不可以在每次发送消息时，都调用start方法
         */
        producer.start();
        System.out.println("RocketMq defaultProducer Started.");
        return producer;
    }

    @Bean
    public Producer rocketMqProducer(DefaultMQProducer defaultMQProducer) {
        RocketMqProducer rocketMqProducer = new RocketMqProducer();
        rocketMqProducer.setProducer(defaultMQProducer);
        rocketMqProducer.setRetryNum(mqProperties.getRetryNum());
        return rocketMqProducer;
    }
}