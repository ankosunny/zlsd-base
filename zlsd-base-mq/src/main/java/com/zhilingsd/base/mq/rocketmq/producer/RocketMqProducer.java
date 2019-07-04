package com.zhilingsd.base.mq.rocketmq.producer;

import com.alibaba.fastjson.JSONObject;
import com.zhilingsd.base.mq.MqException;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.io.UnsupportedEncodingException;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 生产者
 */
public class RocketMqProducer implements Producer {

    /**
     * 默认生产组名
     */
    private final String DEFAULT_GROUP_NAME = "ZLSD_PRODUCER_DEFAULT_GROUP";


    private DefaultMQProducer producer;

    private final AtomicBoolean started = new AtomicBoolean(false);
    private final AtomicBoolean closed = new AtomicBoolean(false);

    public RocketMqProducer(String namesrvAddr, String producerGroupName, Integer sendMsgTimeout, Integer retryTimes) {
        producer = new DefaultMQProducer(null == producerGroupName ? DEFAULT_GROUP_NAME : producerGroupName);
        producer.setNamesrvAddr(namesrvAddr);
        producer.setInstanceName("producer#" + UUID.randomUUID().toString());
        if (null != sendMsgTimeout) {
            producer.setSendMsgTimeout(sendMsgTimeout);
        }
        if (null != retryTimes) {
            producer.setRetryTimesWhenSendFailed(retryTimes);
        }
    }

    @Override
    public void start() {
        if (this.started.compareAndSet(false, true)) {
            try {
                this.producer.start();
            } catch (Exception e) {
                throw new MqException(e.getMessage(),e);
            }
        }
    }

    @Override
    public void shutdown() {
        if (this.closed.compareAndSet(false, true)) {
            producer.shutdown();
        }
    }


    /**
     * 延迟等级
     * 服务默认配置为：1s 5s 10s 30s 1m 2m 3m 4m 5m 6m 7m 8m 9m 10m 20m 30m 1h 2h
     * 请填写对应序号来确定延迟发送时间
     * 例如，需要延迟10s，则 delayLevel = 3
     */
    @Override
    public SendResult synSend(String topic,String tag,String msg ,Integer delayLevel) throws UnsupportedEncodingException {
        Message message = new Message(topic, tag, msg.getBytes(RemotingHelper.DEFAULT_CHARSET));
        if (delayLevel>0){
            message.setDelayTimeLevel(delayLevel);
        }
        return synSend(message);
    }

    @Override
    public SendResult synSend(String topic, String tag, String msg) throws UnsupportedEncodingException {
        Message message = new Message(topic, tag, msg.getBytes(RemotingHelper.DEFAULT_CHARSET));
        return synSend(message);
    }

    @Override
    public void asynSend(String topic, String tag, String msg) throws UnsupportedEncodingException {
        Message message = new Message(topic, tag, msg.getBytes(RemotingHelper.DEFAULT_CHARSET));
        asynSend(message);
    }

    @Override
    public void asynSend(String topic,String tag,String msg ,Integer delayLevel) throws UnsupportedEncodingException {
        Message message = new Message(topic, tag, msg.getBytes(RemotingHelper.DEFAULT_CHARSET));
        if (delayLevel>0){
            message.setDelayTimeLevel(delayLevel);
        }
        asynSend(message);
    }

    private SendResult synSend(Message message){
        try {
            SendResult result = producer.send(message);
            return result;
        } catch (MQClientException e) {
            e.printStackTrace();
            throw new MqException(e.getMessage(),e);
        } catch (RemotingException e) {
            e.printStackTrace();
            throw new MqException(e.getMessage(),e);
        } catch (MQBrokerException e) {
            e.printStackTrace();
            throw new MqException(e.getMessage(),e);
        } catch (InterruptedException e) {
            e.printStackTrace();
            throw new MqException(e.getMessage(),e);
        }finally {
            producer.shutdown();
        }
    }

    private void asynSend(Message message){
        try {
            producer.send(message, new SendCallback() {
                @Override
                public void onSuccess(SendResult sendResult) {
                    System.out.printf("发送成功：" + sendResult.getMsgId());
                }

                @Override
                public void onException(Throwable e) {
                    System.out.printf("发送失败：" + JSONObject.toJSONString(e));
                }
            });
        } catch (MQClientException e) {
            e.printStackTrace();
            throw new MqException(e.getMessage(),e);
        } catch (RemotingException e) {
            e.printStackTrace();
            throw new MqException(e.getMessage(),e);
        } catch (InterruptedException e) {
            e.printStackTrace();
            throw new MqException(e.getMessage(),e);
        }finally {
            producer.shutdown();
        }
    }











}
