package com.zhilingsd.base.common.support;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor;
import java.util.ArrayList;
import java.util.List;


/**
 *
 * 功能描述:singleResult包装类
 * @param: []
 * @return: void
 * @auther: 吞星
 * @date: 2019/6/22-14:48
 */
public class SingleResultWrapFactoryBean implements InitializingBean {

    @Autowired
    private RequestMappingHandlerAdapter adapter;


    /**
     *
     * 功能描述:初始化ResponseBodyWrapFactoryBean对象之后，执行这个方法
     * @param: []
     * @return: void
     * @auther: 吞星
     * @date: 2019/6/22-14:48
     */
    @Override
    public void afterPropertiesSet() {
        List<HandlerMethodReturnValueHandler> returnValueHandlers = adapter.getReturnValueHandlers();
        List<HandlerMethodReturnValueHandler> handlers = new ArrayList(returnValueHandlers);
        decorateHandlers(handlers);
        adapter.setReturnValueHandlers(handlers);
    }


    private void decorateHandlers(List<HandlerMethodReturnValueHandler> handlers) {
        for(int i = 0;i<handlers.size();i++){
            HandlerMethodReturnValueHandler handler = handlers.get(i);
            if (handler instanceof RequestResponseBodyMethodProcessor) {
                ApiReturnValueHandler decorator = new ApiReturnValueHandler(handler);
                //用自定义的OpenApiReturnValueHandler替换掉原来的RequestResponseBodyMethodProcessor类型处理器
                handlers.set(i, decorator);
                break;
            }
        }
    }
}
