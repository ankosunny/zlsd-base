package com.zhilingsd.base.common.aspect;

import com.alibaba.fastjson.JSONObject;
import com.zhilingsd.base.common.bean.AppAgentInfo;
import com.zhilingsd.base.common.emuns.BaseResultCodeEnum;
import com.zhilingsd.base.common.exception.BusinessException;
import com.zhilingsd.base.common.exception.ServiceException;
import com.zhilingsd.base.common.utils.AppUtil;
import com.zhilingsd.base.common.utils.IPUtils;
import com.zhilingsd.base.common.utils.JsonUtils;
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
 *
 * @author linmenghuai
 * @date 2019-4-30 16:23:32
 */
@Order(1)
@Aspect
@Component
@Slf4j
public class CommonFacadeAspect {

    //    @Autowired
//    protected Validator validator;
    private static final String PRE_TAG = "************** ";

    @Around(value = "@annotation(com.zhilingsd.base.common.annotation.CommonFacade)")
    public Object before(ProceedingJoinPoint jp) throws Throwable {
        long startTime = System.currentTimeMillis();
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        StringBuffer sb = new StringBuffer();
        sb.append("\n" + PRE_TAG + "URL : " + request.getRequestURL().toString());
        sb.append("\n" + PRE_TAG + "HTTP_METHOD : " + request.getMethod());
        sb.append("\n" + PRE_TAG + "IP地址 : " + IPUtils.getRemortIP(request));
        sb.append("\n" + PRE_TAG + jp.getSignature().getDeclaringTypeName() + "." + jp.getSignature().getName());
        sb.append("\n" + PRE_TAG + "接口入参 : " + JsonUtils.toJsonString(jp.getArgs()));
        log.info(sb.toString());
        // 创建AppAgentInfo对象，如果有字段为空则抛出异常
        String session = Optional.ofNullable(request.getHeader("session")).orElseThrow(() -> new ServiceException(BaseResultCodeEnum.SYSTEM_ERROR.getCode(), "请求头session不能为空"));
        String operatorId = Optional.ofNullable(request.getHeader("operatorId")).orElseThrow(() -> new ServiceException(BaseResultCodeEnum.SYSTEM_ERROR.getCode(), "请求头operatorId不能为空"));
        String collectionCompanyId = Optional.ofNullable(request.getHeader("collectionCompanyId")).orElseThrow(() -> new ServiceException(BaseResultCodeEnum.SYSTEM_ERROR.getCode(), "请求头collectionCompanyId不能为空"));
        AppAgentInfo agentInfo = new AppAgentInfo(Long.parseLong(operatorId), Long.parseLong(collectionCompanyId), session);
        if (agentInfo != null) {
            AppUtil.setAppAgentInfo(agentInfo);
        }
        log.info("当前登录人基础信息：" + JSONObject.toJSONString(agentInfo));
        Object obj = jp.proceed();

        StringBuffer sbreturn = new StringBuffer();
        sbreturn.append("\n" + PRE_TAG + jp.getSignature().getDeclaringTypeName() + "." + jp.getSignature().getName());
        sbreturn.append("\n" + PRE_TAG +  " 接口返回 : " + obj);
        sbreturn.append("\n" + PRE_TAG + " 花费时间 : " + (System.currentTimeMillis() - startTime) + "ms");
        log.info(sbreturn.toString());
        return obj;
    }


//    /**
//     * 参数校验
//     */
//    private <T> ValidationResult  beanValidatorFail(T object, Class<?>... groups) {
//        //ValidationResult为自己封装的对象
//        ValidationResult validationResult = new ValidationResult();
//        validationResult.setSuccess(true);
//        try{
//            BeanValidators.validateWithException(validator, object, groups);
//        }catch(ConstraintViolationException ex){
//            List<String> errMsgs = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
//            validationResult.setSuccess(false);
//            validationResult.setErrMsg(errMsgs.get(0));
//        }
//        return validationResult;
//    }

}
