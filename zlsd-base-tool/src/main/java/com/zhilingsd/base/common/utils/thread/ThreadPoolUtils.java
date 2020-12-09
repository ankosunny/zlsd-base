package com.zhilingsd.base.common.utils.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 线程池工具类
 *
 * @author luziliang
 */
public class ThreadPoolUtils {
    /**
     * 定义核心线程数，并行线程数
     */
    private static int CORE_POOL_SIZE = 4;
    /**
     * 线程池最大线程数：除了正在运行的线程额外保存多少个线程
     */
    private static int MAX_POOL_SIZE = 8;
    /**
     * 额外线程空闲状态生存时间
     */
    private static int KEEP_ALIVE_TIME = 5000;
    /**
     * 队列
     */
    private static LinkedBlockingQueue<Runnable> linkedBlockingQueue = new LinkedBlockingQueue<>();
    /**
     * 线程池
     */
    private static ExecutorService executor;

    static {
        executor = new ThreadPoolExecutor(CORE_POOL_SIZE, MAX_POOL_SIZE, KEEP_ALIVE_TIME,
                TimeUnit.SECONDS, linkedBlockingQueue);
    }

    private ThreadPoolUtils() {
    }

    public static void execute(Runnable runnable) {
        executor.execute(runnable);
    }

}
