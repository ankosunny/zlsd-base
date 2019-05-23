package com.zhilingsd.base.common.interceptor;

import com.zhilingsd.base.common.utils.AppUtil;
import feign.RequestInterceptor;
import feign.RequestTemplate;

import java.util.Objects;

/**
 * feign拦截器
 * @author linmenghuai
 * @date 2019-5-14 20:00:40
 * */
public class FeignBasicRequestInterceptor implements RequestInterceptor {

    /**
     * 当前操作人ID
     * */
    private final static String OPERATOR_ID = "operatorId";
    /**
     * 催收机构ID
     * */
    private final static String COLLECTION_COMPANY_ID = "collectionCompanyId";


    @Override
    public void apply(RequestTemplate requestTemplate) {
        if (Objects.nonNull(AppUtil.getAppAgentInfo().getOperatorId()) && Objects.nonNull(AppUtil.getAppAgentInfo().getCollectionCompanyId())){
            requestTemplate.header(OPERATOR_ID, AppUtil.getAppAgentInfo().getOperatorId()+"");
            requestTemplate.header(COLLECTION_COMPANY_ID, AppUtil.getAppAgentInfo().getCollectionCompanyId()+"");
        }
    }
}

