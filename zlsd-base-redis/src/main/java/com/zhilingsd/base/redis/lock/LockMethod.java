package com.zhilingsd.base.redis.lock;

import java.lang.annotation.*;

/**
 * 分布式锁注解，加在需要分布式锁的方法前
 * 其中：key是必填，其他根据需要选填
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LockMethod {

    /**
     * 锁的key
     * 本参数是必写选项<br/>
     */
    String key() default "redisLock";

    /**
     * 持锁时间，超时时间，持锁超过此时间自动丢弃锁<br/>
     * 单位是秒,默认20秒<br/>
     * 如果为0表示永远不释放锁，知道过程执行完自动释放锁，
     * 在设置为0的情况下toWait为true是没有意义的<br/>
     * 但是没有比较强的业务要求下，不建议设置为0
     */
    int expireTime() default 20;

    /**
     * 当获取锁失败，是继续等待还是放弃  ,默认不等待
     */
    boolean toWait() default false;

    /**
     * 锁获取等待超时时间：<br/>
     * 没有获取到锁的情况下且toWait()为true继续等待，最大等待时间
     * 单位毫秒,默认3秒钟，如果设置为0即为没有超时时间，一直获取下去；
     *
     * @return
     */
    long maxSleepMills() default 3 * 1000;

}