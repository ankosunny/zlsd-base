package com.zhilingsd.base.mq.consumer;

import com.alibaba.fastjson.JSONObject;
import com.zhilingsd.base.mq.annotation.MQConsumer;
import com.zhilingsd.base.mq.bean.MQTestBean;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author: yuboliang
 * @date:
 **/
@Component
@MQConsumer(consumerGroup = "test_consumer_group", topic = "test_topic")
public class TestConsumer extends AbstractMQPushConsumer<MQTestBean> {

    @Override
    public boolean process(MQTestBean message, Map extMap) {
        System.out.println(JSONObject.toJSONString(message));
        return true;
    }
}