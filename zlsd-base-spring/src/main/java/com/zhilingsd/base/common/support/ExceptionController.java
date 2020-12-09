package com.zhilingsd.base.common.support;

import com.zhilingsd.base.common.result.CommonResult;
import org.springframework.boot.autoconfigure.web.AbstractErrorController;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.ErrorViewResolver;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.List;

/**
 * 功能描述:重写spring mvc处理404类型错误，返回值
 *
 * @auther: 吞星
 * @date: 2019/6/22-11:56
 */
@ApiIgnore
@Controller
@RequestMapping("${server.error.path:${error.path:/error}}")
public class ExceptionController extends AbstractErrorController {

    private final ErrorProperties errorProperties;

    public static final String ERROR_MESSAGE_404 = "路径错误:";


    public ExceptionController(ErrorAttributes errorAttributes,
                               ErrorProperties errorProperties) {
        this(errorAttributes, errorProperties,
                Collections.emptyList());
    }

    public ExceptionController(ErrorAttributes errorAttributes,
                               ErrorProperties errorProperties, List<ErrorViewResolver> errorViewResolvers) {
        super(errorAttributes, errorViewResolvers);
        Assert.notNull(errorProperties, "ErrorProperties must not be null");
        this.errorProperties = errorProperties;
    }

    @Override
    public String getErrorPath() {
        return this.errorProperties.getPath();
    }

    @RequestMapping
    @ResponseBody
    public CommonResult error(HttpServletRequest request) {
        CommonResult result = new CommonResult();
        result.setCode(404);
        result.setMsg(ERROR_MESSAGE_404 + request.getRequestURL());
        result.setSysTime(String.valueOf(System.currentTimeMillis()));
        return result;
    }

    protected boolean isIncludeStackTrace(HttpServletRequest request,
                                          MediaType produces) {
        ErrorProperties.IncludeStacktrace include = getErrorProperties().getIncludeStacktrace();
        if (include == ErrorProperties.IncludeStacktrace.ALWAYS) {
            return true;
        }
        if (include == ErrorProperties.IncludeStacktrace.ON_TRACE_PARAM) {
            return getTraceParameter(request);
        }
        return false;
    }

    /**
     * Provide access to the error properties.
     *
     * @return the error properties
     */
    protected ErrorProperties getErrorProperties() {
        return this.errorProperties;
    }

}
