package com.zhilingsd.base.common.utils;

/**
 * 系统工具
 * @author linmenghuai
 * @date 2019-4-29 20:30:01
 * */
public class AppUtil {

    private volatile static AppUtil app = null;

    private AppUtil() {}

    public static synchronized AppUtil getInstance() {
        if (app == null) {
            synchronized (AppUtil.class) {
                if (app == null) {
                    app = new AppUtil();
                }
            }
        }
        return app;
    }

    public static void setHeader(String key, String value) {
        ThreadContextUtil.getInstance().set(key, value);
    }

    public static String getHeader(String key) {
        return (String) ThreadContextUtil.getInstance().get(key);
    }

}
