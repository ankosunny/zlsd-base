package com.zhilingsd.base.common.utils;

import com.zhilingsd.base.common.bean.AppAgentInfo;

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

    public static void setAppAgentInfo(AppAgentInfo appAgentInfo) {
        ThreadContextUtil.getInstance().set("appAgentInfoKey", appAgentInfo);
    }

    public static AppAgentInfo getAppAgentInfo() {
        return (AppAgentInfo) ThreadContextUtil.getInstance().get("appAgentInfoKey");
    }

}
