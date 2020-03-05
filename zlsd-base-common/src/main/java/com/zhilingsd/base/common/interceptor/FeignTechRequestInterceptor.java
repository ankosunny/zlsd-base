package com.zhilingsd.base.common.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.zhilingsd.base.common.bean.AppUserInfo;
import com.zhilingsd.base.common.utils.AppUtil;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

import static com.zhilingsd.base.common.constants.TechConstant.*;


/**
 * feign拦截器
 * @author yuboliang
 * @date 2020-2-24 14:35:13
 * */
@Slf4j
public class FeignTechRequestInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate requestTemplate) {
        AppUserInfo appUserInfo = AppUtil.getAppUserInfo();
        if (appUserInfo == null) {
            return;
        }

        log.debug("用户信息{}", JSONObject.toJSONString(appUserInfo));

        Optional.ofNullable(appUserInfo.getSession()).ifPresent(tmp -> requestTemplate.header(SESSION, tmp));
        Optional.ofNullable(appUserInfo.getOperatorId()).ifPresent(tmp -> requestTemplate.header(OPERATOR_ID, String.valueOf(tmp)));
        Optional.ofNullable(appUserInfo.getAccount()).ifPresent(tmp -> requestTemplate.header(ACCOUNT, tmp));
        Optional.ofNullable(appUserInfo.getMerchantId()).ifPresent(tmp -> requestTemplate.header(MERCHANT_ID, String.valueOf(tmp)));
        Optional.ofNullable(appUserInfo.getPlatform()).ifPresent(tmp -> requestTemplate.header(PLATFORM, tmp));
    }
}
