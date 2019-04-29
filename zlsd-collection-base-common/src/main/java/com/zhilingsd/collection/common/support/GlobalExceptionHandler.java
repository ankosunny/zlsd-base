package com.zhilingsd.collection.common.support;

import com.zhilingsd.collection.common.exception.*;
import com.zhilingsd.collection.common.util.result.CommonResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;

/**
 * 统一异常处理
 * @author jacky
 * @date 2018/4/12
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);


    /**
     * 应用到所有@RequestMapping注解方法，在其执行之前初始化数据绑定器
     * @param binder
     */
    @InitBinder
    public void initBinder(WebDataBinder binder) {}

    private CommonResult returnErr(Exception e, String errMsg, String code) {
        //LOGGER.error(errMsg + e.toString());
    	LOGGER.error(e.getMessage(), e);
        CommonResult result = new CommonResult();
        result.setCode(code);
        result.setMsg(e.getMessage());
        result.setSysTime(String.valueOf(System.currentTimeMillis()));
        return  result;
    }
    private CommonResult returnErr(BaseException e, String errMsg) {
    	LOGGER.error(e.getMessage(), e);
        CommonResult result = new CommonResult();
        result.setCode(e.getCode());
        result.setMsg(e.getMessage());
        result.setSysTime(String.valueOf(System.currentTimeMillis()));
        return  result;
    }
    /**
     * 全局异常捕获
     * @param e
     * @return
     */
    @ExceptionHandler(value = Exception.class)
    public CommonResult errorHandler(Exception e) {
        return returnErr(e,"内部服务器异常","500");
    }

    /**
     * 缺少请求参数异常处理
     * 400 - Bad Request
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public CommonResult handleMissingServletRequestParameterException(MissingServletRequestParameterException e) {
        return returnErr(e,"缺少请求参数","400");
    }

    /**
     * 参数解析失败
     * 400 - Bad Request
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public CommonResult handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        return returnErr(e,"参数解析失败","400");

    }

    /**
     * 400 - Bad Request
     * 参数验证失败
     * @param e
     * @return
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public CommonResult handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {      
        return returnErr(e,"参数验证失败","400");
    }

    /**
     * 400 - Bad Request
     * 参数绑定失败
     * @param e
     * @return
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BindException.class)
    public CommonResult handleBindException(BindException e) {
        return returnErr(e,"参数绑定失败","400");
    }

    /**
     *
     * 400 - Bad Request
     * 参数验证失败
     * @param e
     * @return
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public CommonResult handleServiceException(ConstraintViolationException e) {
        return returnErr(e,"约束违反异常","400");
    }

    /**
     * 400 - Bad Request
     * 参数验证失败
     * @param e
     * @return
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ValidationException.class)
    public CommonResult handleValidationException(ValidationException e) {
        return returnErr(e,"参数验证异常","400");
    }

    /**
     * 415 - UNSUPPORTED_MEDIA_TYPE
     * @param e
     * @return
     */
    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public CommonResult handleHttpMediaTypeNotSupportedException(Exception e) {
        return returnErr(e,"不支持当前媒体类型","400");
    }

    /**
     * 统一处理405异常
     * @param e
     * @return
     */
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public CommonResult handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        return returnErr(e,"请求方式不支持异常","405");
    }


    /**
     * 捕获处理WebException
     * @param e
     * @return
     * @throws Exception
     */
    @ExceptionHandler(value = WebException.class)
    public CommonResult webErrorHandler(WebException e) throws Exception {
        return returnErr(e,"请求web异常");
    }

   

    /**
     * 捕获处理DAOException
     * @param e
     * @return
     * @throws Exception
     */
    @ExceptionHandler(value = DAOException.class)
    public CommonResult daoErrorHandler(DAOException e) throws Exception {
        return returnErr(e,"请求dao异常");
    }

    /**
     * 捕获处理BaseException
     * @param e
     * @return
     * @throws Exception
     */
    @ExceptionHandler(value = BaseException.class)
    public CommonResult baseErrorHandler(BaseException e) throws Exception {
        return returnErr(e,"base异常");
    }

    /**
     * 捕获处理ServiceException
     * @param e
     * @return
     * @throws Exception
     */
    @ExceptionHandler(value = ServiceException.class)
    public CommonResult serviceErrorHandler(ServiceException e) throws Exception {
        return returnErr(e,"service异常");
    }

    /**
     * 捕获处理BusinessException
     * @param e
     * @return
     * @throws Exception
     */
    @ExceptionHandler(value = BusinessException.class)
    public CommonResult bussinessErrorHandler(BusinessException e) throws Exception {
        return returnErr(e,"bussiness异常");
    }

    /**
     * 捕获处理IllegalParameterException
     * @param e
     * @return
     * @throws Exception
     */
    @ExceptionHandler(value = IllegalParameterException.class)
    public CommonResult  illegalParameterErrorHandler(IllegalParameterException e) throws Exception {
        return returnErr(e,"illegalParameter异常");
    }


}
