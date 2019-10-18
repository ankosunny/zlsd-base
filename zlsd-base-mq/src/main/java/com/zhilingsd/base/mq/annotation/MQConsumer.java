package com.zhilingsd.base.mq.annotation;

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
}
