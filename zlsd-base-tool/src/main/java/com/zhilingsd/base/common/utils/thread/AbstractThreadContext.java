package com.zhilingsd.base.common.utils.thread;

import com.google.common.base.Objects;
import com.google.common.collect.Maps;

import java.util.Map;

/**
 * 抽象线程上下文
 * @author linmenghuai
 * @date 2019-4-29 20:34:00
 * */
public abstract class AbstractThreadContext {

    protected abstract ThreadLocal<Map<String,Object>> getThreadContext();

    public void set(final String key,final Object o) {
        ThreadLocal<Map<String,Object>> threadContext = getThreadContext();
        Map<String,Object> map = threadContext.get();
        if (Objects.equal(null, map)) {
            map = Maps.newHashMap();
        }
        map.put(key, o);
        if (o instanceof String) {
            map.put((String) o, Thread.currentThread().getName());
        }
        threadContext.set(map);
        return ;
    }

    /**
     * 设置线程上下文键值
     *
     * @param key
     */
    public void removeKey(String key) {
        ThreadLocal<Map<String,Object>> threadContext = getThreadContext();
        Map<String,Object> map = threadContext.get();
        if (Objects.equal(null, map)) {
            map = Maps.newHashMap();
        }
        map.remove(key);
        threadContext.set(map);
        return ;
    }
    /**
     * 获得线程上下文对应key的值
     *
     * @param key
     * @return
     */
    public Object get(String key) {
        ThreadLocal<Map<String,Object>> threadContext = getThreadContext();
        Map<String,Object> map = threadContext.get();
        if (Objects.equal(null, map)) {
            return null;
        } else {
            return map.get(key);
        }
    }

    /**
     * 清楚线程上下文
     *
     * @return
     */
    public void clean() {
        ThreadLocal<Map<String,Object>> threadContext = getThreadContext();
        threadContext.remove();
    }
}
