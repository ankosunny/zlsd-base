package com.zhilingsd.base.mq.rocketmq.producer;

import org.apache.rocketmq.client.producer.SendResult;

import java.io.UnsupportedEncodingException;

/**
 * 生产者接口
 * @author linmenghuai
 * */
public interface Producer {
    void start();
    void shutdown();
    SendResult synSend(String topic, String tag, String msg , Integer delayLevel) throws UnsupportedEncodingException;
    SendResult synSend(String topic, String tag, String msg) throws UnsupportedEncodingException;
    void asynSend(String topic,String tag,String msg ,Integer delayLevel) throws UnsupportedEncodingException;
    void asynSend(String topic,String tag,String msg) throws UnsupportedEncodingException;
}
