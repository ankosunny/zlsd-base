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
    void syncSendDelay(String topic, String tag, Object body, DelayTimeLevelEnum delayTimeLevelEnum);

    /**
     * 同步发送
     * */
    void syncSend(String topic, String tag, Object body);

    /**
     * 异步发送
     * */
    void asyncSend(String topic, String tag, Object body);

    /**
     * 异步发送到并指定路由
     */
    void asyncSendWithRoute(String topic, String tag, Object body, Long routeId);
}
