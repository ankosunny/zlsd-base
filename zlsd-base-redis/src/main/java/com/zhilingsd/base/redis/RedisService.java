/**
 * Software License Declaration.
 * <p>
 * zhilingsd.com, Co,. Ltd.
 * Copyright © 2017 All Rights Reserved.
 * <p>
 * Copyright Notice
 * This documents is provided to zhilingsd contracting agent or authorized programmer only.
 * This source code is written and edited by zhilingsd Co,.Ltd Inc specially for financial
 * business contracting agent or authorized cooperative company, in order to help them to
 * install, programme or central control in certain project by themselves independently.
 * <p>
 * Disclaimer
 * If this source code is needed by the one neither contracting agent nor authorized programmer
 * during the use of the code, should contact to zhilingsd Co,. Ltd Inc, and get the confirmation
 * and agreement of three departments managers  - Research Department, Marketing Department and
 * Production Department.Otherwise zhilingsd will charge the fee according to the programme itself.
 * <p>
 * Any one,including contracting agent and authorized programmer,cannot share this code to
 * the third party without the agreement of zhilingsd. If Any problem cannot be solved in the
 * procedure of programming should be feedback to zhilingsd Co,. Ltd Inc in time, Thank you!
 */
package com.zhilingsd.base.redis;

import com.zhilingsd.base.cache.Cache;
import com.zhilingsd.base.redis.exception.RedisOperateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static org.apache.commons.lang3.exception.ExceptionUtils.getStackTrace;

/**
 * Redis服务
 *
 * @author zhangrong
 * @version Id: RedisService.java, v 0.1 2018/11/18 15:31 zhangrong Exp $$
 */
@Component
public class RedisService implements Cache {

    private final static Logger log = LoggerFactory.getLogger(RedisService.class);

    @Value("${zlsd.base.redis.expire.seconds:300}")
    private int expireSeconds;

    @Resource
    private RedisTemplate redisTemplate;

    @Override
    public void set(Object key, Object value) {
        set(key, value, expireSeconds);
    }

    @Override
    public void set(Object key, Object value, int time) {
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

    @Override
    public Object get(Object key) {
        try {
            return redisTemplate.opsForValue().get(key);
        } catch (Exception e) {
            log.error("Redis get 异常，堆栈信息：{}", getStackTrace(e));
            throw new RedisOperateException("Redis get 异常:" + e.getMessage());
        }
    }

    @Override
    public Double increment(Object key, double delta) {
        return redisTemplate.opsForValue().increment(key,delta);
    }

    @Override
    public void delete(Object key) {
        try {
            redisTemplate.delete(key);
        } catch (Exception e) {
            log.error("Redis delete 异常，堆栈信息：{}", getStackTrace(e));
            throw new RedisOperateException("Redis delete 异常:" + e.getMessage());
        }
    }

    @Override
    public Boolean isKeyExist(Object key) {
        try {
            return redisTemplate.hasKey(key);
        } catch (Exception e) {
            log.error("Redis isKeyExist 异常，堆栈信息：{}", getStackTrace(e));
            throw new RedisOperateException("Redis isKeyExist 异常:" + e.getMessage());
        }
    }

    @Override
    public Boolean setnx(Object key, Object value) {
        return setnx(key, value, expireSeconds);
    }

    @Override
    public synchronized Boolean setnx(Object key, Object value, int time) {
        try {
            Boolean success = redisTemplate.opsForValue().setIfAbsent(key, value);
            if (success) {
                expire(key, time);
            }
            return success;
        } catch (Exception e) {
            log.error("Redis setnx 异常，堆栈信息：{}", getStackTrace(e));
            throw new RedisOperateException("Redis setnx 异常:" + e.getMessage());
        }
    }

    @Override
    public Boolean expire(Object key, int time) {
        try {
            return redisTemplate.expire(key, time, TimeUnit.SECONDS);
        } catch (Exception e) {
            log.error("Redis expire 异常，堆栈信息：{}", getStackTrace(e));
            throw new RedisOperateException("Redis expire 异常:" + e.getMessage());
        }
    }

    @Override
    public Long lpush(Object key, Object value) {
        return lpush(key, value, expireSeconds);
    }

    @Override
    public synchronized Long lpush(Object key, Object value, int time) {
        try {
            Long length = redisTemplate.opsForList().leftPush(key, value);
            if (length > 0) {
                expire(key, time);
            }
            return length;
        } catch (Exception e) {
            log.error("Redis lpush 异常，堆栈信息：{}", getStackTrace(e));
            throw new RedisOperateException("Redis lpush 异常:" + e.getMessage());
        }
    }

    @Override
    public Long rpush(Object key, Object value) {
        return rpush(key, value, expireSeconds);
    }

    @Override
    public Long rpush(Object key, Object value, int time) {
        try {
            Long length = redisTemplate.opsForList().rightPush(key, value);
            if (length > 0) {
                expire(key, time);
            }
            return length;
        } catch (Exception e) {
            log.error("Redis rpush 异常，堆栈信息：{}", getStackTrace(e));
            throw new RedisOperateException("Redis rpush 异常:" + e.getMessage());
        }
    }

    @Override
    public Object lpop(Object key) {
        try {
            return redisTemplate.opsForList().leftPop(key);
        } catch (Exception e) {
            log.error("Redis lpop 异常，堆栈信息：{}", getStackTrace(e));
            throw new RedisOperateException("Redis lpop 异常:" + e.getMessage());
        }
    }

    @Override
    public Object rpop(Object key) {
        try {
            return redisTemplate.opsForList().rightPop(key);
        } catch (Exception e) {
            log.error("Redis rpop 异常，堆栈信息：{}", getStackTrace(e));
            throw new RedisOperateException("Redis rpop 异常:" + e.getMessage());
        }
    }

    @Override
    public List<Object> lget(Object key) {
        try {
            return redisTemplate.opsForList().range(key, 0, -1);
        } catch (Exception e) {
            log.error("Redis lget 异常，堆栈信息：{}", getStackTrace(e));
            throw new RedisOperateException("Redis lget 异常:" + e.getMessage());
        }
    }

    @Override
    public Long lLength(Object key) {
        try {
            return redisTemplate.opsForList().size(key);
        } catch (Exception e) {
            log.error("Redis lLength 异常，堆栈信息：{}", getStackTrace(e));
            throw new RedisOperateException("Redis lLength 异常:" + e.getMessage());
        }
    }

    @Override
    public Long lremove(Object key, Object value) {
        try {
            return redisTemplate.opsForList().remove(key, 0, value);
        } catch (Exception e) {
            log.error("Redis lremove 异常，堆栈信息：{}", getStackTrace(e));
            throw new RedisOperateException("Redis lremove 异常:" + e.getMessage());
        }
    }

    @Override
    public void hset(Object key, String field, Object value) {
        hset(key, field, value, expireSeconds);
    }

    @Override
    public synchronized void hset(Object key, String field, Object value,
                                  int expireTime) {
        try {
            redisTemplate.opsForHash().put(key, field, value);
            expire(key, expireTime);
        } catch (Exception e) {
            log.error("Redis hset 异常，堆栈信息：{}", getStackTrace(e));
            throw new RedisOperateException("Redis hset 异常:" + e.getMessage());
        }
    }

    @Override
    public Object hget(Object key, String field) {
        try {
            return redisTemplate.opsForHash().get(key, field);
        } catch (Exception e) {
            log.error("Redis hget 异常，堆栈信息：{}", getStackTrace(e));
            throw new RedisOperateException("Redis hget 异常:" + e.getMessage());
        }
    }

    @Override
    public Boolean hsetnx(Object key, String field, Object value) {
        return hsetnx(key, field, value, expireSeconds);
    }

    @Override
    public synchronized Boolean hsetnx(Object key, String field, Object value,
                                       int expireSeconds) {
        try {
            Boolean success = redisTemplate.opsForHash().putIfAbsent(key, field, value);
            if (success) {
                expire(key, expireSeconds);
            }
            return success;
        } catch (Exception e) {
            log.error("Redis hsetnx 异常，堆栈信息：{}", getStackTrace(e));
            throw new RedisOperateException("Redis hsetnx 异常:" + e.getMessage());
        }
    }

    @Override
    public Set<Object> hkeys(Object key) {
        try {
            return redisTemplate.opsForHash().keys(key);
        } catch (Exception e) {
            log.error("Redis hkeys 异常，堆栈信息：{}", getStackTrace(e));
            throw new RedisOperateException("Redis hkeys 异常:" + e.getMessage());
        }
    }

    @Override
    public void hmset(Object key, Map fieldValues) {
        hmset(key, fieldValues, expireSeconds);
    }

    @Override
    public synchronized void hmset(Object key, Map fieldValues, int expireSeconds) {
        try {
            Map<String, Object> request = new HashMap<>();
            for (String field : ((Map<String, Object>) fieldValues).keySet()) {
                request.put(field, fieldValues.get(field));
            }
            redisTemplate.opsForHash().putAll(key, request);
            expire(key, expireSeconds);
        } catch (Exception e) {
            log.error("Redis hmset 异常，堆栈信息：{}", getStackTrace(e));
            throw new RedisOperateException("Redis hmset 异常:" + e.getMessage());
        }
    }

    @Override
    public List<Object> hmget(Object key, String... fields) {
        List<Object> result = new ArrayList<>();
        try {
            for (String field : fields) {
                result.add(redisTemplate.opsForHash().get(key, field));
            }
            return result;
        } catch (Exception e) {
            log.error("Redis hmget 异常，堆栈信息：{}", getStackTrace(e));
            throw new RedisOperateException("Redis hmget 异常:" + e.getMessage());
        }
    }

    @Override
    public Long hdel(Object key, String... fields) {
        try {
            return redisTemplate.opsForHash().delete(key, fields);
        } catch (Exception e) {
            log.error("Redis hdel 异常，堆栈信息：{}", getStackTrace(e));
            throw new RedisOperateException("Redis hdel 异常:" + e.getMessage());
        }
    }

    /*************************Set*************************************/
    @Override
    public void sAdd(Object key, Object value) {
        sAdd(key, value, -1);
    }

    @Override
    public void sAdd(Object key, Object value, int time) {
        try {
            redisTemplate.opsForSet().add(key, value);
            redisTemplate.expire(key, time > 0 ? time : expireSeconds, TimeUnit.SECONDS);
        } catch (Throwable e) {
            log.error("sAdd key[{}] 异常，堆栈信息：{}", key, getStackTrace(e));
            throw new RedisOperateException("Redis sAdd 异常:" + e.getMessage());
        }
    }


    @Override
    public Object sPop(Object key) {
        try {
            return redisTemplate.opsForSet().pop(key);
        } catch (Throwable e) {
            log.error("sPop key[{}] 异常，堆栈信息：{}", key, getStackTrace(e));
            return null;
        }
    }

    @Override
    public Set<Object> sMembers(Object key) {
        try {
            Set<byte[]> value = redisTemplate.opsForSet().members(key);
            Set<Object> result = new HashSet<>();
            if (value == null) {
                return null;
            } else {
                Iterator<byte[]> iterator = value.iterator();
                while (iterator.hasNext()) {
                    result.add(iterator.next());
                }

                return result;
            }
        } catch (Throwable e) {
            log.error("sMembers key[{}] 异常，堆栈信息：{}", key, getStackTrace(e));
            return null;
        }
    }

    @Override
    public Boolean sIsMember(Object key, Object value) {
        try {
            return redisTemplate.opsForSet().isMember(key, value);
        } catch (Throwable e) {
            log.error("sIsMember key[{}] 异常，堆栈信息：{}", key, getStackTrace(e));
            return false;
        }
    }

    @Override
    public Long sRem(Object key, Object value) {
        try {
            return redisTemplate.opsForSet().remove(key, value);
        } catch (Throwable e) {
            log.error("sRem key[{}] 异常，堆栈信息：{}", key, getStackTrace(e));
            return -1L;
        }
    }

    @Override
    public Boolean zAdd(Object key, Object value, Long score) {
        try {
            return redisTemplate.opsForZSet().add(key, value, score);
        } catch (Throwable e) {
            log.error("zAdd key[{}] 异常，堆栈信息：{}", key, getStackTrace(e));
            return false;
        }
    }

    @Override
    public Boolean zAdd(Object key, Object value, Long score, int time) {
        try {
            boolean res = redisTemplate.opsForZSet().add(key, value, score);
            redisTemplate.expire(key, time > 0 ? time : expireSeconds, TimeUnit.SECONDS);
            return res;
        } catch (Throwable e) {
            log.error("zAdd key[{}] 异常，堆栈信息：{}", key, getStackTrace(e));
            return false;
        }
    }

    @Override
    public Integer zLenCount(Object key) {
        try {
            return redisTemplate.opsForZSet().zCard(key).intValue();
        } catch (Throwable e) {
            log.error("zLenCount key[{}] 异常，堆栈信息：{}", key, getStackTrace(e));
            return 0;
        }
    }

    @Override
    public Integer zLenCountBetween(Object key, Long start, Long end) {
        try {
            return redisTemplate.opsForZSet().rangeByScore(key, start, end).size();
        } catch (Throwable e) {
            log.error("zLenCountBetween key[{}] 异常，堆栈信息：{}", key, getStackTrace(e));
            return 0;
        }
    }



    @Override
    public Set<Object> zGet(Object key, double start, double end) {
        try {
            Set sets = redisTemplate.opsForZSet().reverseRangeByScore(key, start, end);
            if (sets == null || sets.size()==0){
                return null;
            }
            Set result = new HashSet();
            Iterator<byte[]> iterator = sets.iterator();
            while (iterator.hasNext()) {
                result.add(iterator.next());
            }
            return result;
        } catch (Throwable e) {
            log.error("zLenCountBetween key[{}] 异常，堆栈信息：{}", key, getStackTrace(e));
            return null;
        }
    }

    @Override
    public Long zRem(Object key, Object value) {
        try {
            return redisTemplate.opsForZSet().remove(key, value);
        } catch (Throwable e) {
            log.error("zRem key[{}] 异常，堆栈信息：{}", key, getStackTrace(e));
            return -1L;
        }
    }

    @Override
    public Double zIncrScore(Object key,Object value,double delta) {

        try {
            return redisTemplate.opsForZSet().incrementScore(key, value, delta);
        } catch (Throwable e) {
            log.error("zLenCountBetween key[{}] 异常，堆栈信息：{}", key, getStackTrace(e));
            return null;
        }
    }

    public int getExpireSeconds() {
        return expireSeconds;
    }

    public void setExpireSeconds(int expireSeconds) {
        this.expireSeconds = expireSeconds;
    }

    public RedisTemplate<String, byte[]> getRedisTemplate() {
        return redisTemplate;
    }

    public void setRedisTemplate(RedisTemplate<String, byte[]> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }
}
