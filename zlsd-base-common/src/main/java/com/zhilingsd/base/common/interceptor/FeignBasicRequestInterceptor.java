package com.zhilingsd.base.common.interceptor;

import com.zhilingsd.base.common.utils.AppUtil;
import feign.RequestInterceptor;
import feign.RequestTemplate;

/**
 * feign拦截器
 * @author linmenghuai
 * @date 2019-5-14 20:00:40
 * */
public class FeignBasicRequestInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate requestTemplate) {
        requestTemplate.header("operatorId", AppUtil.getAppAgentInfo().getOperatorId()+"");
        requestTemplate.header("collectionCompanyId", AppUtil.getAppAgentInfo().getCollectionCompanyId()+"");
        requestTemplate.header("collectionGroupId", AppUtil.getAppAgentInfo().getCollectionGroupId()+"");
    }
}

