package com.zhilingsd.base.common.aspect;

import com.zhilingsd.base.common.bean.AppAgentInfo;
import com.zhilingsd.base.common.constants.AppConstants;
import com.zhilingsd.base.common.emuns.BaseResultCodeEnum;
import com.zhilingsd.base.common.exception.BusinessException;
import com.zhilingsd.base.common.utils.AppUtil;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
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

    @Around(value = "@annotation(com.zhilingsd.base.common.annotation.CommonFacade)")
    public Object before(ProceedingJoinPoint jp) throws Throwable {
        Signature signature = jp.getSignature();
        String mvcInterface = signature.getDeclaringTypeName();
        String mvcMethod = signature.getName();
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        Enumeration<String> headerNames = request.getHeaderNames();
        // 创建AppAgentInfo对象，如果有字段为空则抛出异常
        String operatorId = request.getHeader("operatorId");
        String collectionCompanyId = request.getHeader("collectionCompanyId");
        String collectionGroupId = request.getHeader("collectionGroupId");
        String resourceId = request.getHeader("resourceId");
        AppAgentInfo agentInfo = null;
        // 判空操作
        if (StringUtils.isNotEmpty(operatorId) && StringUtils.isNotEmpty(collectionCompanyId)) {
            agentInfo = new AppAgentInfo(Long.parseLong(operatorId), Long.parseLong(collectionCompanyId));
        } else {
            throw new BusinessException(BaseResultCodeEnum.METHOD_ARGUMENT_NOT_VALID_ERROR.getCode(), "接口:"+mvcInterface+";方法:"+mvcMethod+";AppAgentInfo is null");
        }
        if(StringUtils.isNotEmpty(collectionGroupId)){
            agentInfo.setCollectionGroupId(Long.parseLong(collectionGroupId));
        }
        if (StringUtils.isNotEmpty(resourceId)){
           agentInfo.setResourceId(Long.parseLong(resourceId));
        }
        if (agentInfo != null) {
            AppUtil.setAppAgentInfo(agentInfo);
        }
        Object[] args = jp.getArgs();//获取方法参数值
        if (args != null) {
            for (Object arg : args) {
                ValidationResult validationResult = beanValidatorFail(arg);
                if(!validationResult.getSuccess()){
                   throw new BusinessException(BaseResultCodeEnum.METHOD_ARGUMENT_NOT_VALID_ERROR.getCode(),validationResult.getErrMsg());
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
        validationResult.setSuccess(true);
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
