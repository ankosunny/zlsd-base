package com.zhilingsd.base.redis;

import com.zhilingsd.base.redis.exception.RedisOperateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

import static org.apache.commons.lang3.exception.ExceptionUtils.getStackTrace;

@Component
public class RedisExtService  {

    private final static Logger log = LoggerFactory.getLogger(RedisService.class);

    @Value("${zlsd.base.redis.expire.seconds:300}")
    private int expireSeconds;

    @Resource
    private RedisTemplate redisTemplate;

    /**
     * 设置原始数据
     * */
    public void setOriginData(Object key, int value, int time) {
        try {

            if (time > 0) {
                redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
            } else if (time < 0) {
                redisTemplate.opsForValue().set(key, value, expireSeconds, TimeUnit.SECONDS);
            }
        } catch (Exception e) {
            log.error("Redis set 异常，堆栈信息：{}", getStackTrace(e));
            throw new RedisOperateException("Redis set 异常:" + e.getMessage());
        }
    }

    public Double getOriginData(Object key) throws Exception {
        try {

            Object object = redisTemplate.opsForValue().get(key);
            if (object != null){
                return Double.parseDouble(object.toString());
            }
            return null;
        } catch (Exception e) {
            log.error("Redis get 异常，堆栈信息：{}", getStackTrace(e));
            throw new RedisOperateException("Redis get 异常:" + e.getMessage());
        }
    }

    public Double increment(Object key, double delta) {
        return redisTemplate.opsForValue().increment(key,delta);
//       return redisTemplate.getConnectionFactory().getConnection().incrBy(
//                redisTemplate.getKeySerializer().serialize(key), delta);
    }

    public void delete(Object key) {
        try {
            redisTemplate.delete((String) key);
        } catch (Exception e) {
            log.error("Redis delete 异常，堆栈信息：{}", getStackTrace(e));
            throw new RedisOperateException("Redis delete 异常:" + e.getMessage());
        }
    }
}
