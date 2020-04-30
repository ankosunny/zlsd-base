package com.zhilingsd.base.mq.producer;

import com.zhilingsd.base.mq.enums.DelayTimeLevelEnum;
import com.zhilingsd.base.mq.enums.SendStatusEnum;
import org.apache.rocketmq.client.producer.SendCallback;

/**
 * 生产者接口
 * @author linmenghuai
 * */
public interface Producer {

    /**
     * 同步延迟发送
     * @param topic topic
     * @param tag tag
     * @param body 消息体
     * @param delayTimeLevelEnum 延迟级别
     * @return 发送结果
     */
    SendStatusEnum syncSendDelay(String topic, String tag, Object body, DelayTimeLevelEnum delayTimeLevelEnum);

    /**
     * 同步发送
     * @param topic topic
     * @param tag tag
     * @param body 消息体
     * @return 发送结果
     */
    SendStatusEnum syncSend(String topic, String tag, Object body);

    /**
     * 同步发送并指定路由
     * @param topic topic
     * @param tag tag
     * @param body 消息体
     * @param routeId 路由ID
     * @return 发送结果
     */
    SendStatusEnum syncSendWithRoute(String topic, String tag, Object body, Long routeId);

    /**
     * 异步发送（发送失败会转成同步发送）
     * @param topic topic
     * @param tag tag
     * @param body 消息体
     */
    void asyncSend(String topic, String tag, Object body);

    /**
     * 异步发送并自行处理回调（发送失败【不会】转成同步发送）
     * @param topic topic
     * @param tag tag
     * @param body 消息体
     * @param callback 回调函数
     */
    void asyncSendWithCallBack(String topic, String tag, Object body, SendCallback callback);

    /**
     * 异步发送到并指定路由（发送失败会转成同步发送）
     * @param topic topic
     * @param tag tag
     * @param body 消息体
     * @param routeId 路由ID
     */
    void asyncSendWithRoute(String topic, String tag, Object body, Long routeId);
}
