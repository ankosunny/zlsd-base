package com.zhilingsd.base.mq.enums;

/**
 * RocektMQ发送状态，原生请参考org.apache.rocketmq.client.producer.SendStatus
 * @author: yuboliang
 * @date: 2020/2/28
 **/
public enum SendStatusEnum {
    /**
     * 发送成功
     */
    SEND_OK,

    /**
     * 发送成功，但刷盘超时，SendStatus.FLUSH_DISK_TIMEOUT
     */
    FLUSH_DISK_TIMEOUT,

    /**
     * 发送成功，但刷同步slave超时，SendStatus.FLUSH_SLAVE_TIMEOUT
     */
    FLUSH_SLAVE_TIMEOUT,

    /**
     * 发送成功，但刷salve无法识别，SendStatus.SLAVE_NOT_AVAILABLE
     */
    SLAVE_NOT_AVAILABLE,

    /**
     * 发送失败，完全没发给broker
     */
    SEND_FAILED;
}
