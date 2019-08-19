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
import com.zhilingsd.base.common.utils.SerializeUtil;
import com.zhilingsd.base.redis.exception.RedisOperateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.Serializable;
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
    private RedisTemplate<String, byte[]> redisTemplate;

    @Override
    public void set(Serializable key, Serializable value) {
        set(key, value, expireSeconds);
    }

    @Override
    public void set(Serializable key, Serializable value, int time) {
        try {
            if (time > 0) {
                redisTemplate.opsForValue().set((String) key,
                        SerializeUtil.serialize(value), time, TimeUnit.SECONDS);
            } else if (time < 0) {
                redisTemplate.opsForValue().set((String) key,
                        SerializeUtil.serialize(value), expireSeconds, TimeUnit.SECONDS);
            }
        } catch (Exception e) {
            log.error("Redis set 异常，堆栈信息：{}", getStackTrace(e));
            throw new RedisOperateException("Redis set 异常:" + e.getMessage());
        }
    }

    @Override
    public Serializable get(Serializable key) {
        try {
            byte[] value = redisTemplate.opsForValue().get(key);
            if (value == null) {
                return null;
            } else {
                return (Serializable) SerializeUtil.unSerialize(value);
            }
        } catch (Exception e) {
            log.error("Redis get 异常，堆栈信息：{}", getStackTrace(e));
            throw new RedisOperateException("Redis get 异常:" + e.getMessage());
        }
    }

    @Override
    public void delete(Serializable key) {
        try {
            redisTemplate.delete((String) key);
        } catch (Exception e) {
            log.error("Redis delete 异常，堆栈信息：{}", getStackTrace(e));
            throw new RedisOperateException("Redis delete 异常:" + e.getMessage());
        }
    }

    @Override
    public Boolean isKeyExist(Serializable key) {
        try {
            return redisTemplate.hasKey((String) key);
        } catch (Exception e) {
            log.error("Redis isKeyExist 异常，堆栈信息：{}", getStackTrace(e));
            throw new RedisOperateException("Redis isKeyExist 异常:" + e.getMessage());
        }
    }

    @Override
    public Boolean setnx(Serializable key, Serializable value) {
        return setnx(key, value, expireSeconds);
    }

    @Override
    public synchronized Boolean setnx(Serializable key, Serializable value, int time) {
        try {
            Boolean success = redisTemplate.opsForValue().setIfAbsent((String) key,
                    SerializeUtil.serialize(value));
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
    public Boolean expire(Serializable key, int time) {
        try {
            return redisTemplate.expire((String) key, time, TimeUnit.SECONDS);
        } catch (Exception e) {
            log.error("Redis expire 异常，堆栈信息：{}", getStackTrace(e));
            throw new RedisOperateException("Redis expire 异常:" + e.getMessage());
        }
    }

    @Override
    public Long lpush(Serializable key, Serializable value) {
        return lpush(key, value, expireSeconds);
    }

    @Override
    public synchronized Long lpush(Serializable key, Serializable value, int time) {
        try {
            Long length = redisTemplate.opsForList().leftPush((String) key,
                    SerializeUtil.serialize(value));
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
    public Long rpush(Serializable key, Serializable value) {
        return rpush(key, value, expireSeconds);
    }

    @Override
    public Long rpush(Serializable key, Serializable value, int time) {
        try {
            Long length = redisTemplate.opsForList().rightPush((String) key,
                    SerializeUtil.serialize(value));
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
    public Serializable lpop(Serializable key) {
        try {
            byte[] value = redisTemplate.opsForList().leftPop((String) key);
            if (value == null) {
                return null;
            } else {
                return (Serializable) SerializeUtil.unSerialize(value);
            }
        } catch (Exception e) {
            log.error("Redis lpop 异常，堆栈信息：{}", getStackTrace(e));
            throw new RedisOperateException("Redis lpop 异常:" + e.getMessage());
        }
    }

    @Override
    public Serializable rpop(Serializable key) {
        try {
            byte[] value = redisTemplate.opsForList().rightPop((String) key);
            if (value == null) {
                return null;
            } else {
                return (Serializable) SerializeUtil.unSerialize(value);
            }
        } catch (Exception e) {
            log.error("Redis rpop 异常，堆栈信息：{}", getStackTrace(e));
            throw new RedisOperateException("Redis rpop 异常:" + e.getMessage());
        }
    }

    @Override
    public List<Object> lget(Serializable key) {
        try {
            List<byte[]> range = redisTemplate.opsForList().range((String) key, 0, -1);
            List<Object> result = new ArrayList<>();
            for (byte[] value : range) {
                result.add(SerializeUtil.unSerialize(value));
            }
            return result;
        } catch (Exception e) {
            log.error("Redis lget 异常，堆栈信息：{}", getStackTrace(e));
            throw new RedisOperateException("Redis lget 异常:" + e.getMessage());
        }
    }

    @Override
    public Long lLength(Serializable key) {
        try {
            return redisTemplate.opsForList().size((String) key);
        } catch (Exception e) {
            log.error("Redis lLength 异常，堆栈信息：{}", getStackTrace(e));
            throw new RedisOperateException("Redis lLength 异常:" + e.getMessage());
        }
    }

    @Override
    public Long lremove(Serializable key, Serializable value) {
        try {
            return redisTemplate.opsForList().remove((String) key, 0,
                    new String(SerializeUtil.serialize(value)));
        } catch (Exception e) {
            log.error("Redis lremove 异常，堆栈信息：{}", getStackTrace(e));
            throw new RedisOperateException("Redis lremove 异常:" + e.getMessage());
        }
    }

    @Override
    public void hset(Serializable key, String field, Serializable value) {
        hset(key, field, value, expireSeconds);
    }

    @Override
    public synchronized void hset(Serializable key, String field, Serializable value,
                                  int expireTime) {
        try {
            redisTemplate.opsForHash().put((String) key, field,
                    SerializeUtil.serialize(value));
            expire(key, expireTime);
        } catch (Exception e) {
            log.error("Redis hset 异常，堆栈信息：{}", getStackTrace(e));
            throw new RedisOperateException("Redis hset 异常:" + e.getMessage());
        }
    }

    @Override
    public Serializable hget(Serializable key, String field) {
        try {
            Object o = redisTemplate.opsForHash().get((String) key, field);
            if (o == null) {
                return null;
            } else {
                return (Serializable) SerializeUtil.unSerialize(((byte[]) o));
            }
        } catch (Exception e) {
            log.error("Redis hget 异常，堆栈信息：{}", getStackTrace(e));
            throw new RedisOperateException("Redis hget 异常:" + e.getMessage());
        }
    }

    @Override
    public Boolean hsetnx(Serializable key, String field, Serializable value) {
        return hsetnx(key, field, value, expireSeconds);
    }

    @Override
    public synchronized Boolean hsetnx(Serializable key, String field, Serializable value,
                                       int expireSeconds) {
        try {
            Boolean success = redisTemplate.opsForHash().putIfAbsent((String) key, field,
                    new String(SerializeUtil.serialize(value)));
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
    public Set<Object> hkeys(Serializable key) {
        try {
            return redisTemplate.opsForHash().keys((String) key);
        } catch (Exception e) {
            log.error("Redis hkeys 异常，堆栈信息：{}", getStackTrace(e));
            throw new RedisOperateException("Redis hkeys 异常:" + e.getMessage());
        }
    }

    @Override
    public void hmset(Serializable key, Map fieldValues) {
        hmset(key, fieldValues, expireSeconds);
    }

    @Override
    public synchronized void hmset(Serializable key, Map fieldValues, int expireSeconds) {
        try {
            Map<String, Serializable> request = new HashMap<>();
            for (String field : ((Map<String, Serializable>) fieldValues).keySet()) {
                request.put(field, SerializeUtil.serialize(fieldValues.get(field)));
            }
            redisTemplate.opsForHash().putAll((String) key, request);
            expire(key, expireSeconds);
        } catch (Exception e) {
            log.error("Redis hmset 异常，堆栈信息：{}", getStackTrace(e));
            throw new RedisOperateException("Redis hmset 异常:" + e.getMessage());
        }
    }

    @Override
    public List<Serializable> hmget(Serializable key, String... fields) {
        List<Serializable> result = new ArrayList<>();
        try {
            for (String field : fields) {
                result.add(
                        (Serializable) SerializeUtil.unSerialize(
                                redisTemplate.<String, byte[]>opsForHash().get((String) key, field)));
            }
            return result;
        } catch (Exception e) {
            log.error("Redis hmget 异常，堆栈信息：{}", getStackTrace(e));
            throw new RedisOperateException("Redis hmget 异常:" + e.getMessage());
        }
    }

    @Override
    public Long hdel(Serializable key, String... fields) {
        try {
            return redisTemplate.opsForHash().delete((String) key, fields);
        } catch (Exception e) {
            log.error("Redis hdel 异常，堆栈信息：{}", getStackTrace(e));
            throw new RedisOperateException("Redis hdel 异常:" + e.getMessage());
        }
    }

    /*************************Set*************************************/
    @Override
    public void sAdd(Serializable key, Serializable value) {
        sAdd(key, value, -1);
    }

    @Override
    public void sAdd(Serializable key, Serializable value, int time) {
        try {
            redisTemplate.opsForSet().add((String) key, SerializeUtil.serialize(value));
            redisTemplate.expire((String) key, time > 0 ? time : expireSeconds, TimeUnit.SECONDS);
        } catch (Throwable e) {
            log.error("sAdd key[{}] 异常，堆栈信息：{}", key, getStackTrace(e));
            throw new RedisOperateException("Redis sAdd 异常:" + e.getMessage());
        }
    }


    @Override
    public Serializable sPop(Serializable key) {
        try {
            byte[] value = redisTemplate.opsForSet().pop((String) key);
            return value == null ? null : (Serializable) SerializeUtil.unSerialize(value);
        } catch (Throwable e) {
            log.error("sPop key[{}] 异常，堆栈信息：{}", key, getStackTrace(e));
            return null;
        }
    }

    @Override
    public Set<Serializable> sMembers(Serializable key) {
        try {
            Set<byte[]> value = redisTemplate.opsForSet().members((String) key);
            Set<Serializable> result = new HashSet<>();
            if (value == null) {
                return null;
            } else {
                Iterator<byte[]> iterator = value.iterator();
                while (iterator.hasNext()) {
                    result.add((Serializable) SerializeUtil.unSerialize(iterator.next()));
                }

                return result;
            }
        } catch (Throwable e) {
            log.error("sMembers key[{}] 异常，堆栈信息：{}", key, getStackTrace(e));
            return null;
        }
    }

    @Override
    public Boolean sIsMember(Serializable key, Serializable value) {
        try {
            return redisTemplate.opsForSet().isMember((String) key, SerializeUtil.serialize(value));
        } catch (Throwable e) {
            log.error("sIsMember key[{}] 异常，堆栈信息：{}", key, getStackTrace(e));
            return false;
        }
    }

    @Override
    public Long sRem(Serializable key, Serializable value) {
        try {
            return redisTemplate.opsForSet().remove((String) key, SerializeUtil.serialize(value));
        } catch (Throwable e) {
            log.error("sRem key[{}] 异常，堆栈信息：{}", key, getStackTrace(e));
            return -1L;
        }
    }

    @Override
    public Boolean zAdd(Serializable key, Serializable value, Long score) {
        try {
            return redisTemplate.opsForZSet().add((String)key, SerializeUtil.serialize(value), score);
        } catch (Throwable e) {
            log.error("zAdd key[{}] 异常，堆栈信息：{}", key, getStackTrace(e));
            return false;
        }
    }

    @Override
    public Boolean zAdd(Serializable key, Serializable value, Long score, int time) {
        try {
            boolean res = redisTemplate.opsForZSet().add((String)key, SerializeUtil.serialize(value), score);
            redisTemplate.expire((String) key, time > 0 ? time : expireSeconds, TimeUnit.SECONDS);
            return res;
        } catch (Throwable e) {
            log.error("zAdd key[{}] 异常，堆栈信息：{}", key, getStackTrace(e));
            return false;
        }
    }

    @Override
    public Integer zLenCount(Serializable key) {
        try {
            return redisTemplate.opsForZSet().zCard((String)key).intValue();
        } catch (Throwable e) {
            log.error("zLenCount key[{}] 异常，堆栈信息：{}", key, getStackTrace(e));
            return 0;
        }
    }

    @Override
    public Integer zLenCountBetween(Serializable key, Long start, Long end) {
        try {
            return redisTemplate.opsForZSet().rangeByScore((String)key, start, end).size();
        } catch (Throwable e) {
            log.error("zLenCountBetween key[{}] 异常，堆栈信息：{}", key, getStackTrace(e));
            return 0;
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
