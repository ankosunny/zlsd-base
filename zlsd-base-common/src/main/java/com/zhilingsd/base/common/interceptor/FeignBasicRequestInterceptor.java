package com.zhilingsd.base.common.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.zhilingsd.base.common.emuns.ReturnCode;
import com.zhilingsd.base.common.exception.ServiceException;
import com.zhilingsd.base.common.utils.AppUtil;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;


/**
 * feign拦截器
 * @author linmenghuai
 * @date 2019-5-14 20:00:40
 * */
@Slf4j
public class FeignBasicRequestInterceptor implements RequestInterceptor {

    /**
     * 当前操作人ID
     * */
    private final static String OPERATOR_ID = "operatorId";
    /**
     * 催收机构ID
     * */
    private final static String COLLECTION_COMPANY_ID = "collectionCompanyId";
    /**
     * 催收机构ID
     * */
    private final static String COLLECTION_GROUP_ID = "collectionGroupId";
    /**
     * Session
     * */
    private final static String SESSION = "session";

    @Override
    public void apply(RequestTemplate requestTemplate) {
        if (Objects.isNull(AppUtil.getAppAgentInfo())) {
            log.info("拦截器没有当前登录用户信息");
            throw new ServiceException(ReturnCode.BUSINESS_ERROR.getCode(),"请重新登录!");
        }
        log.debug("用户信息{}", JSONObject.toJSONString(AppUtil.getAppAgentInfo()));

        if (Objects.nonNull(AppUtil.getAppAgentInfo().getOperatorId()) && Objects.nonNull(AppUtil.getAppAgentInfo().getCollectionCompanyId())
                &&Objects.nonNull(AppUtil.getAppAgentInfo().getSession())){
            requestTemplate.header(OPERATOR_ID, AppUtil.getAppAgentInfo().getOperatorId()+"");
            requestTemplate.header(COLLECTION_COMPANY_ID, AppUtil.getAppAgentInfo().getCollectionCompanyId()+"");
            requestTemplate.header(SESSION, AppUtil.getAppAgentInfo().getSession());
            requestTemplate.header(COLLECTION_GROUP_ID, AppUtil.getAppAgentInfo().getCollectionGroupId()+"");
        }
    }
}
