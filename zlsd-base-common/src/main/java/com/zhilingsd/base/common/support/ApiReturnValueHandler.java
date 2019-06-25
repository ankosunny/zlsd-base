package com.zhilingsd.base.common.support;

import com.zhilingsd.base.common.annotation.GetSingleResult;
import com.zhilingsd.base.common.emuns.BaseResultCodeEnum;
import com.zhilingsd.base.common.result.SingleResult;
import org.springframework.core.MethodParameter;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 *
 * 功能描述:api返回值处理器
 * @auther: 吞星
 * @date: 2019/6/22-11:54
 */
public class ApiReturnValueHandler implements HandlerMethodReturnValueHandler {

    private final HandlerMethodReturnValueHandler handlerMethodReturnValueHandler;


    public ApiReturnValueHandler(HandlerMethodReturnValueHandler handlerMethodReturnValueHandler) {
        this.handlerMethodReturnValueHandler = handlerMethodReturnValueHandler;
    }

    /**
     *
     * 功能描述:
     * @param: [methodParameter]
     * @return: boolean
     * @auther: 吞星
     * @date: 2019/6/22-11:54
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

    /**
     *
     * 功能描述:
     * @param: [returnValue, methodParameter, mavContainer, webRequest]
     * @return: void
     * @auther: 吞星
     * @date: 2019/6/22-11:54
     */
    @Override
    public void handleReturnValue(Object returnValue,
                                  MethodParameter methodParameter,
                                  ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest) throws Exception {
        Object handleResult = null;
        SingleResult<Object> result = new SingleResult();
        //获得返回值类型
        Class<?> returnValueType = methodParameter.getParameterType();
        GetSingleResult methodSingleResult = methodParameter.getMethodAnnotation(GetSingleResult.class);
        Class<?> declaringClass = methodParameter.getDeclaringClass();
        GetSingleResult classSingleResult = declaringClass.getAnnotation(GetSingleResult.class);
        if (!void.class.isAssignableFrom(returnValueType)) {
            // 不是SingleResult、Map、Model等类型的返回值，需要包裹为Response类型
            if (!SingleResult.class.isAssignableFrom(returnValueType)
                    && !Model.class.isAssignableFrom(returnValueType)
                    && (methodSingleResult != null || classSingleResult != null)) {
                result.setCode(BaseResultCodeEnum.SUCCESS.getCode());
                result.setMsg(BaseResultCodeEnum.SUCCESS.getMsg());
                result.setData(returnValue);
                result.setSysTime(String.valueOf(System.currentTimeMillis()));
                handleResult = result;
            } else {
                //不包装
                handleResult = returnValue;
            }
        } else {
            result.setCode(BaseResultCodeEnum.SUCCESS.getCode());
            result.setMsg(BaseResultCodeEnum.SUCCESS.getMsg());
            result.setSysTime(String.valueOf(System.currentTimeMillis()));
            handleResult = result;
        }
        handlerMethodReturnValueHandler.handleReturnValue(handleResult, methodParameter, mavContainer, webRequest);
    }
}
