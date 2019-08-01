package com.zhilingsd.base.redis.lock;

import com.zhilingsd.base.common.emuns.ReturnCode;
import com.zhilingsd.base.common.exception.BusinessException;
import com.zhilingsd.base.common.exception.ServiceException;
import com.zhilingsd.base.redis.constants.RedisLockKeyConstants;
import com.zhilingsd.base.redis.exception.RedisLockException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Component
public class RedisLockAspect {

    private static Logger logger = LoggerFactory.getLogger(RedisLockAspect.class);

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Around("@annotation(LockMethod)")
    public Object addLock(ProceedingJoinPoint joinPoint) throws Throwable {
        logger.debug("拦截方法成功...");
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        LockMethod methodLock = method.getAnnotation(LockMethod.class);
        if (methodLock == null) {
            throw new RedisLockException("配置参数错误");
        }

        //锁的key
        String key = RedisLockKeyConstants.PREFIX_KEY + methodLock.key() + "_lock";
        logger.info("key:" + key);
        // 竞争进程 最大的等待时间
        long maxSleepMills = methodLock.maxSleepMills();
        //获得锁超时时间（秒）
        int expire = methodLock.expireTime();
        // 竞争进程是否等待
        //Boolean isWait = methodLock.toWait();

        Object obj = null;
        RedisLock redisLock3 = new RedisLock(redisTemplate, key, expire, maxSleepMills);
        try {
            //long now = System.currentTimeMillis();
            if (redisLock3.lock()) {
                logger.debug("获取分布式锁成功");
                obj = joinPoint.proceed();
            } else {
                logger.debug("获取分布式锁失败");
                throw new ServiceException(ReturnCode.SYSTEM_ERROR.getCode(),"获取分布式锁失败");
            }
        } catch (Exception e) {
            logger.info(e.getMessage(), e);
            //把捕获到的异常抛出去
            throw e;
        } finally {
            redisLock3.unlock();
        }

        return obj;
    }
}
