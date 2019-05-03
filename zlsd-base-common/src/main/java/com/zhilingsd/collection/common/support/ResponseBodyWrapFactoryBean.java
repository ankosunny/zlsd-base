package com.zhilingsd.collection.common.support;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor;
import java.util.ArrayList;
import java.util.List;

/**
 * @author jacky
 * @date 2018/4/12
 */
public class ResponseBodyWrapFactoryBean implements InitializingBean {

    @Autowired
    private RequestMappingHandlerAdapter adapter;

    /**
     * 初始化ResponseBodyWrapFactoryBean对象之后，执行这个方法
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
                OpenApiReturnValueHandler decorator = new OpenApiReturnValueHandler(handler);
                //用自定义的OpenApiReturnValueHandler替换掉原来的RequestResponseBodyMethodProcessor类型处理器
                handlers.set(i, decorator);
                break;
            }
        }
    }
}
