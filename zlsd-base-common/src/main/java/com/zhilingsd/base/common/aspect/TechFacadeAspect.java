package com.zhilingsd.base.common.aspect;

import com.alibaba.fastjson.JSONObject;
import com.zhilingsd.base.common.annotation.TechFacade;
import com.zhilingsd.base.common.bean.AppUserInfo;
import com.zhilingsd.base.common.emuns.ReturnCode;
import com.zhilingsd.base.common.exception.ServiceException;
import com.zhilingsd.base.common.utils.AppUtil;
import com.zhilingsd.base.common.utils.IPUtils;
import com.zhilingsd.base.common.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Optional;

import static com.zhilingsd.base.common.constants.TechConstant.*;

/**
 * 接口公共服务处理
 *
 * @author linmenghuai
 * @date 2019-4-30 16:23:32
 */
@Order(1)
@Aspect
@Component
@Slf4j
public class TechFacadeAspect {

    private static final String PRE_TAG = "************** ";


    @Around(value = "@annotation(com.zhilingsd.base.common.annotation.TechFacade)")
    public Object before(ProceedingJoinPoint jp) throws Throwable {
        long startTime = System.currentTimeMillis();
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        Boolean printArgs = isPrintArgs(jp);
        // 请求参数
        printRequestLog(jp, request, printArgs);

        // 创建AppUserInfo
        processAppUserInfo(jp, request);

        // 执行代码流程
        Object obj;
        try {
            obj = jp.proceed();
        } finally {
            AppUtil.clean();
        }

        // 响应参数
        printResponseLog(jp, startTime, printArgs, obj);
        return obj;
    }

    /**
     * 创建AppUserInfo
     * @param jp
     * @param request
     */
    private void processAppUserInfo(ProceedingJoinPoint jp, HttpServletRequest request) {
        Boolean needLogin = isNeedLogin(jp);
        if (!needLogin) {
            return;
        }

        String session = Optional.ofNullable(request.getHeader(SESSION)).orElseThrow(() -> new ServiceException(ReturnCode.BUSINESS_ERROR.getCode(), "请求头session不能为空"));
        Long operatorId = Optional.ofNullable(request.getHeader(OPERATOR_ID)).map(Long::parseLong).orElseThrow(() -> new ServiceException(ReturnCode.SYSTEM_ERROR.getCode(), "请求头operatorId不能为空"));
        String account = Optional.ofNullable(request.getHeader(ACCOUNT)).orElseThrow(() -> new ServiceException(ReturnCode.SYSTEM_ERROR.getCode(), "请求头account不能为空"));
        Long merchantId = Optional.ofNullable(request.getHeader(MERCHANT_ID)).filter(StringUtils::isNotBlank).map(Long::parseLong).orElse(null);
        String platform = Optional.ofNullable(request.getHeader(PLATFORM)).orElse(null);

        AppUserInfo appUserInfo = new AppUserInfo();
        appUserInfo.setSession(session);
        appUserInfo.setOperatorId(operatorId);
        appUserInfo.setAccount(account);
        appUserInfo.setMerchantId(merchantId);
        appUserInfo.setPlatform(platform);
        AppUtil.setAppUserInfo(appUserInfo);
        log.info("当前登录人基础信息：" + JSONObject.toJSONString(appUserInfo));
    }

    /**
     * 打印请求参数
     * @param jp
     * @param request
     * @param printArgs
     */
    private void printRequestLog(ProceedingJoinPoint jp, HttpServletRequest request, Boolean printArgs) {
        StringBuilder requestMsg = new StringBuilder();
        requestMsg.append("\n" + PRE_TAG + "URL : ").append(request.getRequestURL().toString());
        requestMsg.append("\n" + PRE_TAG + "HTTP_METHOD : ").append(request.getMethod());
        requestMsg.append("\n" + PRE_TAG + "IP地址 : ").append(IPUtils.getRemortIP(request));
        requestMsg.append("\n" + PRE_TAG).append(jp.getSignature().getDeclaringTypeName()).append(".").append(jp.getSignature().getName());


        if (printArgs) {
            String jsonString = JsonUtils.toJsonString(jp.getArgs());
            if (jsonString.length() < 1000) {
                requestMsg.append("\n" + PRE_TAG + "接口入参 : ").append(jsonString);
            }
        } else {
            requestMsg.append("\n" + PRE_TAG + "接口入参 : 不打印入参");
        }
        log.info(requestMsg.toString());
    }

    /**
     * 打印响应参数
     * @param jp
     * @param startTime
     * @param printArgs
     * @param obj
     */
    private void printResponseLog(ProceedingJoinPoint jp, long startTime, Boolean printArgs, Object obj) {
        StringBuilder responseMsg = new StringBuilder();
        responseMsg.append("\n" + PRE_TAG).append(jp.getSignature().getDeclaringTypeName()).append(".").append(jp.getSignature().getName());

        if (printArgs) {
            String toJsonString = JsonUtils.toJsonString(obj);
            if (toJsonString.length() < 1000) {
                responseMsg.append("\n" + PRE_TAG + " 接口返回 : ").append(toJsonString);
            }
        } else {
            responseMsg.append("\n" + PRE_TAG + "接口返回 : 不打印返回");
        }
        responseMsg.append("\n" + PRE_TAG + " 花费时间 : ").append(System.currentTimeMillis() - startTime).append("ms");
        log.info(responseMsg.toString());
    }

    private Boolean isPrintArgs(ProceedingJoinPoint jp) {
        // 方法签名
        MethodSignature signature = (MethodSignature) jp.getSignature();
        // 获取方法
        Method method = signature.getMethod();
        // 获取注解
        TechFacade annotation = method.getAnnotation(TechFacade.class);

        return annotation.needPrintArgs();
    }

    private Boolean isNeedLogin(ProceedingJoinPoint jp) {
        // 方法签名
        MethodSignature signature = (MethodSignature) jp.getSignature();
        // 获取方法
        Method method = signature.getMethod();
        // 获取注解
        TechFacade annotation = method.getAnnotation(TechFacade.class);

        return annotation.needLogin();
    }
}
