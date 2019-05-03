/**
 * Software License Declaration.
 *
 * wandaph.com, Co,. Ltd.
 * Copyright © 2017 All Rights Reserved.
 *
 * Copyright Notice
 * This documents is provided to wandaph contracting agent or authorized programmer only.
 * This source code is written and edited by wandaph Co,.Ltd Inc specially for financial
 * business contracting agent or authorized cooperative company, in order to help them to
 * install, programme or central control in certain project by themselves independently.
 *
 * Disclaimer
 * If this source code is needed by the one neither contracting agent nor authorized programmer
 * during the use of the code, should contact to wandaph Co,. Ltd Inc, and get the confirmation
 * and agreement of three departments managers  - Research Department, Marketing Department and
 * Production Department.Otherwise wandaph will charge the fee according to the programme itself.
 *
 * Any one,including contracting agent and authorized programmer,cannot share this code to
 * the third party without the agreement of wandaph. If Any problem cannot be solved in the
 * procedure of programming should be feedback to wandaph Co,. Ltd Inc in time, Thank you!
 */
package com.zhilingsd.base.common.aspect;


import com.zhilingsd.base.common.annotation.WebLogger;
import com.zhilingsd.base.common.utils.JsonUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import javax.servlet.http.HttpServletRequest;

/**
 * 
 * @author linguangliang
 * @version Id:  WebLogAspect.java, v 0.1 2018年6月29日 下午3:58:53 linguangliang Exp $
 */
@Aspect
@Order(1)
@Component
public class WebLogAspect {


    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(WebLogAspect.class);

    ThreadLocal<Long> startTime = new ThreadLocal<>();

    private static final String PRE_TAG = "************** ";

    @Around(value = "execution( * com.zhilingsd..*.*(..))" +
            "&& @annotation(webLogger)")
    public Object webLog(ProceedingJoinPoint jp, WebLogger webLogger) throws Throwable {
        startTime.set(System.currentTimeMillis());
        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        StringBuffer sb = new StringBuffer();
        sb.append("\n" + PRE_TAG + "URL : " + request.getRequestURL().toString());
        sb.append("\n" + PRE_TAG + "HTTP_METHOD : " + request.getMethod());
        sb.append("\n" + PRE_TAG + "IP地址 : " + request.getRemoteAddr());
        sb.append("\n" + PRE_TAG + "接口描述 : " + webLogger.description());
        sb.append("\n" + PRE_TAG + jp.getSignature().getDeclaringTypeName() + "." + jp.getSignature().getName());
        sb.append("\n" + PRE_TAG + "接口入参 : " + JsonUtils.toJsonString(jp.getArgs()));
        logger.info(sb.toString());
        Object returnRes = jp.proceed();
        StringBuffer sbreturn = new StringBuffer();
        sbreturn.append("\n" + PRE_TAG + jp.getSignature().getDeclaringTypeName() + "." + jp.getSignature().getName());
        sbreturn.append("\n" + PRE_TAG +  " 接口返回 : " + returnRes);
        sbreturn.append("\n" + PRE_TAG + " 花费时间 : " + (System.currentTimeMillis() - startTime.get()) + "ms");
        logger.info(sbreturn.toString());
        return returnRes;
    }
}