package com.zhilingsd.base.mq.producer;

import com.alibaba.fastjson.JSONObject;
import com.zhilingsd.base.mq.enums.DelayTimeLevelEnum;
import com.zhilingsd.base.mq.enums.SendStatusEnum;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
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
    public SendStatusEnum syncSendDelay(String topic, String tag, Object body, DelayTimeLevelEnum delayTimeLevelEnum) {
        String msgJson = JSONObject.toJSONString(body);
        String key = UUID.randomUUID().toString();
        Message message = new Message(topic, tag, key, msgJson.getBytes());
        message.setDelayTimeLevel(delayTimeLevelEnum.getLevel());
        log.info("发送消息至RocketMQ:topic={} tag={} key={} body={}", topic, tag, key, msgJson);
        return syncSend(message);
    }

    @Override
    public SendStatusEnum syncSend(String topic, String tag, Object body) {
        String msgJson = JSONObject.toJSONString(body);
        String key = UUID.randomUUID().toString();
        Message message = new Message(topic, tag, key, msgJson.getBytes());
        log.info("发送消息至RocketMQ:topic={} tag={} key={} body={}", topic, tag, key, msgJson);
        return syncSend(message);
    }

    @Override
    public SendStatusEnum syncSendWithRoute(String topic, String tag, Object body, Long routeId) {
        String msgJson = JSONObject.toJSONString(body);
        String key = UUID.randomUUID().toString();
        Message message = new Message(topic, tag, key, msgJson.getBytes());
        log.info("发送消息至RocketMQ:topic={} tag={} key={} body={}", topic, tag, key, msgJson);
        return syncSendWithRoute(message, routeId);
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
    public void asyncSendWithCallBack(String topic, String tag, Object body, SendCallback callback) {
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


    /**
     * 同步发送
     * @param message
     * @return
     */
    private SendStatusEnum syncSend(Message message) {
        Integer retryCount = 0;
        while (retryCount < retryNum) {
            try {
                SendResult sendResult = producer.send(message);
                SendStatus sendStatus = sendResult.getSendStatus();
                return SendStatusEnum.valueOf(sendStatus.name());
            } catch (MQClientException | RemotingException | MQBrokerException | InterruptedException ignore) {
                log.warn("Topic:{}，第{}次发送消息时，出现异常", message.getTopic(), retryCount + 1, ignore);
            } finally {
                retryCount++;
            }
        }

        return SendStatusEnum.SEND_FAILED;
    }

    /**
     * 同步发送，手工指定路由
     * @param message
     * @param routeId
     * @return
     */
    private SendStatusEnum syncSendWithRoute(Message message, Long routeId) {
        Integer retryCount = 0;
        while (retryCount < retryNum) {
            try {
                SendResult sendResult = producer.send(message, (mqs, msg, arg) -> {
                    Long tmpRouteId = (Long) arg;
                    long index = tmpRouteId % mqs.size();
                    return mqs.get((int) index);
                }, routeId);
                SendStatus sendStatus = sendResult.getSendStatus();
                return SendStatusEnum.valueOf(sendStatus.name());
            } catch (MQClientException | RemotingException | MQBrokerException | InterruptedException ignore) {
                log.warn("Topic:{}，第{}次发送消息时，出现异常", message.getTopic(), retryCount + 1, ignore);
            } finally {
                retryCount++;
            }
        }

        return SendStatusEnum.SEND_FAILED;
    }

    /**
     * 异步发送
     * @param message
     */
    private void asyncSend(Message message) {
        try {
            producer.send(message, new SendCallback() {
                @Override
                public void onSuccess(SendResult sendResult) {
                    log.info("发送成功：{}", sendResult.getMsgId());
                }

                @Override
                public void onException(Throwable e) {
                    log.warn("发送失败，开始同步发送", e);
                    syncSend(message);
                }
            });
        } catch (MQClientException | RemotingException | InterruptedException ignore) {
            log.warn("生产者发送消息时，MQ出现异常", ignore);
        }
    }

    /**
     * 异步发送，自行处理回调
     * @param message
     */
    private void asyncSendWithCallBack(Message message, SendCallback callback) {
        try {
            producer.send(message, callback);
        } catch (MQClientException | RemotingException | InterruptedException ignore) {
            log.warn("生产者发送消息时，MQ出现异常", ignore);
        }
    }

    /**
     * 异步发送，手工指定路由
     * @param message
     * @param routeId
     */
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
                    log.warn("发送失败，开始同步发送", e);
                    syncSendWithRoute(message, routeId);
                }
            });
        } catch (MQClientException | RemotingException | InterruptedException ignore) {
            log.warn("生产者发送消息时，MQ出现异常", ignore);
        }
    }
}
