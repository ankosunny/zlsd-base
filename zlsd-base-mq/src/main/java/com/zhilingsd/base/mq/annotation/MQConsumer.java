package com.zhilingsd.base.mq.annotation;

import com.zhilingsd.base.mq.config.MessageExtConst;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * @author yuboliang
 * @date 2019-10-17
 * RocketMQ消费者自动装配注解
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface MQConsumer {

    String consumerGroup();

    String topic();

    String[] tag() default {"*"};

    /**
     * 使用线程池并发消费: CONCURRENTLY("CONCURRENTLY"),
     * 单线程消费: ORDERLY("ORDERLY");
     * @return 消费模式
     */
    String consumeMode() default MessageExtConst.CONSUME_MODE_CONCURRENTLY;

    /**
     * @return 任务超时配置，分钟
     */
    long consumeTimeoutMinutes() default 15;

    /**
     * @return 任务成功前最大重试消费次数
     */
    int maxReconsumeTimes() default -1;  // -1是DefaultMQPushConsumer的默认配置，指16次
}
