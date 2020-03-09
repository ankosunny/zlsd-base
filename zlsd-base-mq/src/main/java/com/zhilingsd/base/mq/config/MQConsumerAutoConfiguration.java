package com.zhilingsd.base.mq.config;

import com.zhilingsd.base.mq.annotation.MQConsumer;
import com.zhilingsd.base.mq.consumer.AbstractMQPushConsumer;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.consumer.listener.MessageListenerOrderly;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.util.Assert;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.UUID;

/**
 * @author yuboliang
 * @date 2019-10-17
 * 自动装配消息消费者
 */
@Slf4j
@Configuration
@ConditionalOnBean(MQBaseAutoConfiguration.class)
public class MQConsumerAutoConfiguration extends MQBaseAutoConfiguration {

    @PostConstruct
    public void init() throws Exception {
        Map<String, Object> beans = applicationContext.getBeansWithAnnotation(MQConsumer.class);
        for (Map.Entry<String, Object> entry : beans.entrySet()) {
            publishConsumer(entry.getKey(), entry.getValue());
        }
    }


    private void publishConsumer(String beanName, Object bean) throws Exception {
        MQConsumer mqConsumer = applicationContext.findAnnotationOnBean(beanName, MQConsumer.class);
        if (StringUtils.isEmpty(mqProperties.getNamesrvAddr())) {
            throw new RuntimeException("name server address 不能为空");
        }
        Assert.notNull(mqConsumer.consumerGroup(), "consumer's consumerGroup 不能为空");
        Assert.notNull(mqConsumer.topic(), "consumer's topic 不能为空");

        Environment environment = applicationContext.getEnvironment();
        String consumerGroup = environment.resolvePlaceholders(mqConsumer.consumerGroup());
        String topic = environment.resolvePlaceholders(mqConsumer.topic());
        String tags = "*";
        if(mqConsumer.tag().length == 1) {
            tags = environment.resolvePlaceholders(mqConsumer.tag()[0]);
        } else if(mqConsumer.tag().length > 1) {
            tags = StringUtils.join(mqConsumer.tag(), "||");
        }

        // 配置push consumer
        if (AbstractMQPushConsumer.class.isAssignableFrom(bean.getClass())) {
            DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(consumerGroup, true);
            consumer.setNamesrvAddr(mqProperties.getNamesrvAddr());
            consumer.subscribe(topic, tags);
            consumer.setInstanceName(UUID.randomUUID().toString());
            AbstractMQPushConsumer abstractMQPushConsumer = (AbstractMQPushConsumer) bean;
            if (MessageExtConst.CONSUME_MODE_CONCURRENTLY.equals(mqConsumer.consumeMode())) {
                // 多线程消费模式（无序）
                consumer.registerMessageListener((MessageListenerConcurrently) abstractMQPushConsumer::dealMessage);
            } else if (MessageExtConst.CONSUME_MODE_ORDERLY.equals(mqConsumer.consumeMode())) {
                // 单线程消费模式（有序）
                consumer.registerMessageListener((MessageListenerOrderly) abstractMQPushConsumer::dealMessage);
            } else {
                throw new RuntimeException("unknown consume mode ! only support CONCURRENTLY and ORDERLY");
            }

            abstractMQPushConsumer.setConsumer(consumer);

            consumer.start();
        }

        log.info(String.format("%s is ready", bean.getClass().getName()));
    }

}