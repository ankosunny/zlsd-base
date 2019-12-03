package com.zhilingsd.base.mq.producer;

import com.alibaba.fastjson.JSONObject;
import com.zhilingsd.base.mq.enums.DelayTimeLevelEnum;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.util.UUID;

/**
 * @author yuboliang
 * @date 2019-10-17
 * MQ生产者
 */
@Slf4j
@Data
public class RocketMqProducer implements Producer {

    private DefaultMQProducer producer;

    private Integer retryNum;

    @Override
    public void syncSendDelay(String topic, String tag, Object body, DelayTimeLevelEnum delayTimeLevelEnum) {
        String msgJson = JSONObject.toJSONString(body);
        String key = UUID.randomUUID().toString();
        Message message = new Message(topic, tag, key, msgJson.getBytes());
        message.setDelayTimeLevel(delayTimeLevelEnum.getLevel());
        log.info("发送消息至RocketMQ:topic={} tag={} key={} body={}", topic, tag, key, msgJson);
        syncSend(message);
    }

    @Override
    public void syncSend(String topic, String tag, Object body) {
        String msgJson = JSONObject.toJSONString(body);
        String key = UUID.randomUUID().toString();
        Message message = new Message(topic, tag, key, msgJson.getBytes());
        log.info("发送消息至RocketMQ:topic={} tag={} key={} body={}", topic, tag, key, msgJson);
        syncSend(message);
    }

    @Override
    public void asyncSend(String topic, String tag, Object body) {
        String msgJson = JSONObject.toJSONString(body);
        String key = UUID.randomUUID().toString();
        Message message = new Message(topic, tag, key, msgJson.getBytes());
        log.info("发送消息至RocketMQ:topic={} tag={} key={} body={}", topic, tag, key, msgJson);
        asyncSend(message);
    }

    @Override
    public void asyncSendWithRoute(String topic, String tag, Object body, Long routeId) {
        String msgJson = JSONObject.toJSONString(body);
        String key = UUID.randomUUID().toString();
        Message message = new Message(topic, tag, key, msgJson.getBytes());
        log.info("发送消息至RocketMQ:topic={} tag={} key={} body={}", topic, tag, key, msgJson);
        asyncSendWithRoute(message, routeId);
    }


    private void syncSend(Message message) {
        SendResult sendResult = null;
        Integer retryCount = 0;
        while (retryCount < retryNum && sendResult == null) {
            try {
                sendResult = producer.send(message);
            } catch (MQClientException | RemotingException | MQBrokerException | InterruptedException ignore) {
                log.warn("Topic:{}，第{}次发送消息时，出现异常", message.getTopic(), retryCount + 1, ignore);
            } finally {
                retryCount++;
            }
        }
    }

    private void asyncSend(Message message) {
        try {
            producer.send(message, new SendCallback() {
                @Override
                public void onSuccess(SendResult sendResult) {
                    log.info("发送成功：{}", sendResult.getMsgId());
                }

                @Override
                public void onException(Throwable e) {
                    log.warn("发送失败", e);
                }
            });
        } catch (MQClientException | RemotingException | InterruptedException ignore) {
            log.warn("生产者发送消息时，MQ出现异常", ignore);
        }
    }

    private void asyncSendWithRoute(Message message, Long routeId) {
        try {
            producer.send(message, (mqs, msg, arg) -> {
                Long tmpRouteId = (Long) arg;
                long index = tmpRouteId % mqs.size();
                return mqs.get((int) index);
            }, routeId, new SendCallback() {
                @Override
                public void onSuccess(SendResult sendResult) {
                    log.info("发送成功：{}", sendResult.getMsgId());
                }

                @Override
                public void onException(Throwable e) {
                    log.warn("发送失败", e);
                }
            });
        } catch (MQClientException | RemotingException | InterruptedException ignore) {
            log.warn("生产者发送消息时，MQ出现异常", ignore);
        }
    }
}
