package com.zhilingsd.base.mq.consumer;

import com.alibaba.fastjson.JSONObject;
import com.zhilingsd.base.mq.annotation.MQConsumer;
import com.zhilingsd.base.mq.bean.MQTestBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author: yuboliang
 * @date:
 **/
@Slf4j
@Component
@MQConsumer(consumerGroup = "test_consumer_group", topic = "test_topic", consumeTimeoutMinutes = 1, maxReconsumeTimes = 3)
public class TestConsumer extends AbstractMQPushConsumer<MQTestBean> {

    @Override
    public boolean process(MQTestBean message, Map extMap) {
//        System.out.println(JSONObject.toJSONString(message));
        log.info("receiveMsg:{}", JSONObject.toJSONString(message));

        // 测试timeout，重试
        // 间隔2分钟，重试3次，共消费4次

        try {
            Thread.sleep(60 * 1000 * 10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("handle msg done, msg:{}", JSONObject.toJSONString(message));
        return true;
    }
}