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
    /**
     * 同步延迟发送
     * */
    SendResult synSend(String topic, String tag, String msg , Integer delayLevel) throws UnsupportedEncodingException;
    /**
     * 同步发送
     * */
    SendResult synSend(String topic, String tag, String msg) throws UnsupportedEncodingException;
   // void asynSend(String topic,String tag,String msg ,Integer delayLevel) throws UnsupportedEncodingException;
    /**
     * 异步发送
     * */
    void asynSend(String topic,String tag,String msg) throws UnsupportedEncodingException;
}
