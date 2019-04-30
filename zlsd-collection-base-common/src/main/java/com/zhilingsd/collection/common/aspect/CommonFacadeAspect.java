package com.zhilingsd.collection.common.aspect;

import com.zhilingsd.collection.common.constants.AppConstants;
import com.zhilingsd.collection.utils.AppUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import java.util.Enumeration;
import java.util.List;

/**
 * 接口公共服务处理
 * @author linmenghuai
 * @date 2019-4-30 16:23:32
 * */
@Order(1)
@Aspect
@Component
public class CommonFacadeAspect {

    @Autowired
    protected Validator validator;

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

        Object[] args = jp.getArgs();//获取方法参数值

        if (args != null) {
            for (Object arg : args) {
                ValidationResult validationResult = beanValidatorFail(arg);
                if(!validationResult.getSuccess()){
                    System.out.println(validationResult.getErrMsg());
                }
            }
        }

        Object obj = jp.proceed();
        return obj;
    }

    /**
     * 参数校验
     */
    private <T> ValidationResult  beanValidatorFail(T object, Class<?>... groups) {
        //ValidationResult为自己封装的对象
        ValidationResult validationResult = new ValidationResult();
        try{
            BeanValidators.validateWithException(validator, object, groups);
        }catch(ConstraintViolationException ex){
            List<String> errMsgs = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
            validationResult.setSuccess(false);
            validationResult.setErrMsg(errMsgs.get(0));
        }
        return validationResult;
    }
}
