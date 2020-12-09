package com.zhilingsd.base.common.utils.thread;

import com.alibaba.ttl.TransmittableThreadLocal;

import java.util.Map;

/**
 * 系统线程上下文工具
 * @author linmenghuai
 * @date 2019-4-29 20:44:11
 * */
public class ThreadContextUtil extends AbstractThreadContext{

    private static ThreadLocal<Map<String,Object>> threadContext = new TransmittableThreadLocal<>();
    private volatile static ThreadContextUtil app = null;

    private ThreadContextUtil(){}

    public static synchronized ThreadContextUtil getInstance() {
        if (app == null) {
            synchronized (ThreadContextUtil.class) {
                if (app == null) {
                    app = new ThreadContextUtil();
                }
            }
        }
        return app;
    }

    @Override
    protected ThreadLocal<Map<String, Object>> getThreadContext() {
        return threadContext;
    }

}
