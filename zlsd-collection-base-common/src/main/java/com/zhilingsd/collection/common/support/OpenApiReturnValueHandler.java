package com.zhilingsd.collection.common.support;

import com.zhilingsd.collection.common.annotation.GetSingleResult;
import com.zhilingsd.collection.common.emuns.BaseResultCodeEnum;
import com.zhilingsd.collection.common.util.result.SingleResult;
import org.springframework.core.MethodParameter;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.Map;

/**
 * @author jacky
 * @date 2018/4/12
 */
public class OpenApiReturnValueHandler implements HandlerMethodReturnValueHandler {

    private final HandlerMethodReturnValueHandler delegate;


    public OpenApiReturnValueHandler(HandlerMethodReturnValueHandler delegate) {
        this.delegate = delegate;
    }

    /**
     * @param methodParameter
     * @return
     */
    @Override
    public boolean supportsReturnType(MethodParameter methodParameter) {
        Class<?> declaringClass = methodParameter.getDeclaringClass();
        RestController restController = declaringClass.getAnnotation(RestController.class);
        ResponseBody responseBody = declaringClass.getAnnotation(ResponseBody.class);
        GetSingleResult classSingleResult = declaringClass.getAnnotation(GetSingleResult.class);
        GetSingleResult methodGetSingleResult = methodParameter.getMethodAnnotation(GetSingleResult.class);
        ResponseBody methodResponseBody = methodParameter.getMethodAnnotation(ResponseBody.class);
        return restController != null || responseBody != null || classSingleResult != null || methodGetSingleResult != null || methodResponseBody != null;
    }

    @Override
    public void handleReturnValue(Object returnValue,
                                  MethodParameter methodParameter,
                                  ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest) throws Exception {
        Object finalResult = returnValue;
        SingleResult result = new SingleResult<Object>();
        //获得返回值类型
        Class<?> returnValueType = methodParameter.getParameterType();
        GetSingleResult methodSingleResult = methodParameter.getMethodAnnotation(GetSingleResult.class);
        Class<?> declaringClass = methodParameter.getDeclaringClass();
        GetSingleResult classSingleResult = declaringClass.getAnnotation(GetSingleResult.class);
        if (!void.class.isAssignableFrom(returnValueType)) {
            // 不是SingleResult、Map、Model等类型的返回值，需要包裹为Response类型
            if (!SingleResult.class.isAssignableFrom(returnValueType) && !Map.class.isAssignableFrom(returnValueType)
                    && !Model.class.isAssignableFrom(returnValueType) && (methodSingleResult != null || classSingleResult != null)) {
                result.setSuccess(true);
                result.setCode(BaseResultCodeEnum.SUCCESS.getCode());
                result.setMsg(BaseResultCodeEnum.SUCCESS.getMsg());
                result.setData(returnValue);
                result.setSysTime(String.valueOf(System.currentTimeMillis()));
                finalResult = result;
            } else {
                //不包装
                finalResult = returnValue;
            }
        }else {
            result.setSuccess(true);
            result.setCode(BaseResultCodeEnum.SUCCESS.getCode());
            result.setMsg(BaseResultCodeEnum.SUCCESS.getMsg());
            result.setSysTime(String.valueOf(System.currentTimeMillis()));
            finalResult = result;
        }
        delegate.handleReturnValue(finalResult, methodParameter, mavContainer, webRequest);
    }
}
