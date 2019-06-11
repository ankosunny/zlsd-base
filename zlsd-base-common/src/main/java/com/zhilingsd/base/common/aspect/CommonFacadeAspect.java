package com.zhilingsd.base.common.aspect;

import com.zhilingsd.base.common.bean.AppAgentInfo;
import com.zhilingsd.base.common.emuns.BaseResultCodeEnum;
import com.zhilingsd.base.common.exception.BusinessException;
import com.zhilingsd.base.common.exception.ServiceException;
import com.zhilingsd.base.common.utils.AppUtil;
import lombok.extern.slf4j.Slf4j;
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
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * 接口公共服务处理
 * @author linmenghuai
 * @date 2019-4-30 16:23:32
 * */
@Order(1)
@Aspect
@Component
@Slf4j
public class CommonFacadeAspect {

    @Autowired
    protected Validator validator;

    @Around(value = "@annotation(com.zhilingsd.base.common.annotation.CommonFacade)")
    public Object before(ProceedingJoinPoint jp) throws Throwable {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        // 创建AppAgentInfo对象，如果有字段为空则抛出异常
        String session = Optional.ofNullable(request.getHeader("session")).orElseThrow(() -> new ServiceException(BaseResultCodeEnum.SYSTEM_ERROR.getCode(), "请求头session不能为空"));
        String operatorId = Optional.ofNullable(request.getHeader("operatorId")).orElseThrow(() -> new ServiceException(BaseResultCodeEnum.SYSTEM_ERROR.getCode(), "请求头operatorId不能为空"));
        String collectionCompanyId = Optional.ofNullable(request.getHeader("collectionCompanyId")).orElseThrow(() -> new ServiceException(BaseResultCodeEnum.SYSTEM_ERROR.getCode(), "请求头collectionCompanyId不能为空"));
        log.info("当前登录session:{}",Objects.isNull(session)?"无信息":session);
        log.info("当前登录operatorId：{}", Objects.isNull(operatorId)?"无信息":operatorId);
        log.info("当前登录collectionCompanyId：{}", Objects.isNull(collectionCompanyId)?"无信息":collectionCompanyId);
        AppAgentInfo  agentInfo = new AppAgentInfo(Long.parseLong(operatorId), Long.parseLong(collectionCompanyId),session);
        if (agentInfo != null) {
            AppUtil.setAppAgentInfo(agentInfo);
        }
//        Object[] args = jp.getArgs();//获取方法参数值
//        if (args != null) {
//            for (Object arg : args) {
//                ValidationResult validationResult = beanValidatorFail(arg);
//                if(!validationResult.getSuccess()){
//                   throw new BusinessException(BaseResultCodeEnum.METHOD_ARGUMENT_NOT_VALID_ERROR.getCode(),validationResult.getErrMsg());
//                }
//            }
//        }
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
