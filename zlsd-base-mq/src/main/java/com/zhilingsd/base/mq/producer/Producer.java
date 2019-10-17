package com.zhilingsd.base.mq.producer;

import com.zhilingsd.base.mq.enums.DelayTimeLevelEnum;

/**
 * 生产者接口
 * @author linmenghuai
 * */
public interface Producer {

    /**
     * 同步延迟发送
     * */
    void synSendDelay(String topic, String tag, Object body, DelayTimeLevelEnum delayTimeLevelEnum);

    /**
     * 同步发送
     * */
    void synSend(String topic, String tag, Object body);

    /**
     * 异步发送
     * */
    void asynSend(String topic, String tag, Object body);
}
