package com.zhilingsd.base.common.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.zhilingsd.base.common.bean.AppUserInfo;
import com.zhilingsd.base.common.utils.AppUtil;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;


/**
 * feign拦截器
 * @author yuboliang
 * @date 2020-2-24 14:35:13
 * */
@Slf4j
public class FeignTechRequestInterceptor implements RequestInterceptor {
    /**
     * Session
     * */
    private final static String SESSION = "session";
    /**
     * 当前操作人ID
     * */
    private final static String OPERATOR_ID = "operatorId";

    @Override
    public void apply(RequestTemplate requestTemplate) {
        AppUserInfo appUserInfo = AppUtil.getAppUserInfo();
        if (appUserInfo == null) {
            return;
        }

        log.debug("用户信息{}", JSONObject.toJSONString(appUserInfo));

        requestTemplate.header(SESSION, appUserInfo.getSession());
        requestTemplate.header(OPERATOR_ID, String.valueOf(appUserInfo.getOperatorId()));
    }
}
