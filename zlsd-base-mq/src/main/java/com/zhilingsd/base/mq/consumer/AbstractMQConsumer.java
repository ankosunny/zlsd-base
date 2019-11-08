package com.zhilingsd.base.mq.consumer;

import com.alibaba.fastjson.JSONObject;
import com.zhilingsd.base.mq.config.MessageExtConst;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.util.Assert;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * @author yuboliang
 * @date 2019-10-17
 * RocketMQ消费抽象基类
 */
@Slf4j
public abstract class AbstractMQConsumer<T> {

    /**
     * 反序列化解析消息
     *
     * @param messageExt  消息体
     * @return 序列化结果
     */
    protected T parseMessage(MessageExt messageExt) {
        if (messageExt == null || messageExt.getBody() == null) {
            return null;
        }
        final Type type = this.getMessageType();
        if (type instanceof Class) {
            try {
                return JSONObject.parseObject(new String(messageExt.getBody()), type);
            } catch (Exception ignore) {
                log.error("msgId: {}, tags : {}, keys : {}, parse message json fail",
                        messageExt.getMsgId(), messageExt.getTags(), messageExt.getKeys(), ignore);
            }
        } else {
            log.warn("Parse msg error. msg: {}, msgId: {}, tags : {}, keys : {},",
                    messageExt, messageExt.getMsgId(), messageExt.getTags(), messageExt.getKeys());
        }
        return null;
    }

    protected Map<String, Object> parseExtParam(MessageExt message) {
        Map<String, Object> extMap = new HashMap<>();

        // parse message property
        extMap.put(MessageExtConst.PROPERTY_TOPIC, message.getTopic());
        extMap.putAll(message.getProperties());

        // parse messageExt property
        extMap.put(MessageExtConst.PROPERTY_EXT_BORN_HOST, message.getBornHost());
        extMap.put(MessageExtConst.PROPERTY_EXT_BORN_TIMESTAMP, message.getBornTimestamp());
        extMap.put(MessageExtConst.PROPERTY_EXT_COMMIT_LOG_OFFSET, message.getCommitLogOffset());
        extMap.put(MessageExtConst.PROPERTY_EXT_MSG_ID, message.getMsgId());
        extMap.put(MessageExtConst.PROPERTY_EXT_PREPARED_TRANSACTION_OFFSET, message.getPreparedTransactionOffset());
        extMap.put(MessageExtConst.PROPERTY_EXT_QUEUE_ID, message.getQueueId());
        extMap.put(MessageExtConst.PROPERTY_EXT_QUEUE_OFFSET, message.getQueueOffset());
        extMap.put(MessageExtConst.PROPERTY_EXT_RECONSUME_TIMES, message.getReconsumeTimes());
        extMap.put(MessageExtConst.PROPERTY_EXT_STORE_HOST, message.getStoreHost());
        extMap.put(MessageExtConst.PROPERTY_EXT_STORE_SIZE, message.getStoreSize());
        extMap.put(MessageExtConst.PROPERTY_EXT_STORE_TIMESTAMP, message.getStoreTimestamp());
        extMap.put(MessageExtConst.PROPERTY_EXT_SYS_FLAG, message.getSysFlag());
        extMap.put(MessageExtConst.PROPERTY_EXT_BODY_CRC, message.getBodyCRC());

        return extMap;
    }

    /**
     * 解析消息类型
     *
     * @return 消息类型
     */
    protected Type getMessageType() {
        Type superType = this.getClass().getGenericSuperclass();
        if (superType instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) superType;
            Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
            Assert.isTrue(actualTypeArguments.length == 1, "Number of type arguments must be 1");
            return actualTypeArguments[0];
        } else {
            // 如果没有定义泛型，解析为Object
            return Object.class;
//            throw new RuntimeException("Unkown parameterized type.");
        }
    }
}
