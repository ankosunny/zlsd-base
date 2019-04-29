package com.zhilingsd.collection.common.aspect;

import com.zhilingsd.collection.common.constants.AppConstants;
import com.zhilingsd.collection.utils.AppUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

@Order(1)
@Aspect
@Component
public class CommonFacadeAspect {

    @Around(value = "@annotation(com.zhilingsd.collection.common.annotation.CommonFacade)")
    public Object before(ProceedingJoinPoint jp) throws Throwable {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()){
            String name = headerNames.nextElement();
            String value = request.getHeader(name);
            if (AppConstants.CREATOR.equalsIgnoreCase(name) || AppConstants.MODIFIER.equalsIgnoreCase(name)){
                AppUtil.setHeader(name,value);
            }
        }
        Object obj = jp.proceed();
        return obj;
    }
}
