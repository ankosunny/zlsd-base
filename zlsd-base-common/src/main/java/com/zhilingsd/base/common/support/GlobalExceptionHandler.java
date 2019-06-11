package com.zhilingsd.base.common.support;

import com.zhilingsd.base.common.emuns.BaseResultCodeEnum;
import com.zhilingsd.base.common.emuns.ReturnCode;
import com.zhilingsd.base.common.exception.*;
import com.zhilingsd.base.common.result.CollectionResult;
import com.zhilingsd.base.common.result.CommonResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.UnauthorizedException;
import org.mybatis.spring.MyBatisSystemException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
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
import java.io.IOException;
import java.util.stream.Collectors;

/**
 * 统一异常处理
 * @author jacky
 * @date 2018/4/12
 *
 *
 *  * ConversionNotSupportedException         500 (Internal Server Error)
 *  * HttpMessageNotWritableException         500 (Internal Server Error)
 *  * HttpMediaTypeNotSupportedException      415 (Unsupported Media Type)
 *  * HttpMediaTypeNotAcceptableException     406 (Not Acceptable)
 *  * HttpRequestMethodNotSupportedException  405 (Method Not Allowed)
 *  * NoSuchRequestHandlingMethodException    404 (Not Found)
 *  * TypeMismatchException                   400 (Bad Request)
 *  * HttpMessageNotReadableException         400 (Bad Request)
 *  * MissingServletRequestParameterException 400 (Bad Request)
 */
@Slf4j
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
    private CommonResult returnErr(String errMsg, String code,Exception e) {
        LOGGER.error(e.getMessage(), e);
        CommonResult result = new CommonResult();
        result.setCode(code);
        result.setMsg(errMsg);
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
        return returnErr("系统异常","500",e);
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

        BindingResult bindingResult = e.getBindingResult();
        StringBuffer errorMesssage = new StringBuffer("参数校验错误:");
        if(null != bindingResult && !CollectionUtils.isEmpty(bindingResult.getFieldErrors())){
            String message =  bindingResult.getFieldErrors().stream().map(m -> m.getDefaultMessage()).collect(Collectors.joining(","));
            errorMesssage.append(message);
        }
        return returnErr(errorMesssage.toString(),BaseResultCodeEnum.METHOD_ARGUMENT_NOT_VALID_ERROR.getCode(),e);
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
    /**
     * 捕获处理IllegalParameterException
     * @param e
     * @return
     * @throws Exception
     */
    @ExceptionHandler(value = MaxSizeException.class)
    public CommonResult  illegalParameterErrorHandler(MaxSizeException e) throws Exception {
        return returnErr(e,"illegalParameter异常");
    }





    /**
     * shiro权限验证异常
     */
    @ExceptionHandler(value = UnauthorizedException.class)
    public CollectionResult unauthorizedExceptionHandler(UnauthorizedException ex) {
        ex.printStackTrace();
        log.error("shiro权限验证异常,异常信息：",ex);
        return CollectionResult.failed(ReturnCode.ERROR_4003.getCode(), ReturnCode.ERROR_4003.getMsg());
    }

    /**
     * mybatis异常
     */
    @ExceptionHandler(value = MyBatisSystemException.class)
    public CollectionResult myBatisSystemException(MyBatisSystemException ex) {
        ex.printStackTrace();
        log.error("mybatis异常,异常信息：",ex);
        return CollectionResult.failed(ReturnCode.ERROR_500.getCode(), ex.toString());
    }


    /**
     * mysql异常
     */
    @ExceptionHandler(value = DataIntegrityViolationException.class)
    public CollectionResult DataIntegrityViolationException(DataIntegrityViolationException ex) {
        ex.printStackTrace();
        log.error("sql错误,异常信息：",ex);
        return CollectionResult.failed(ReturnCode.ERROR_500.getCode(), ex.toString());
    }

    /**
     * 自定义业务异常
     */
    @ExceptionHandler(BusinessExceptionSZ.class)
    public CollectionResult businessException(BusinessExceptionSZ ex) {
        ex.printStackTrace();
        log.error("业务异常,异常信息：",ex);
        return CollectionResult.failed(ex.getCode(), ex.getMsg());
    }

//    /**
//     * 参数校验异常
//     */
//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public CollectionResult methodArgumentNotValidException(MethodArgumentNotValidException ex) {
//        ex.printStackTrace();
//        log.error("参数校验异常,异常信息：",ex);
//        FieldError fieldError = ex.getBindingResult().getFieldError();
//        return CollectionResult.failed(ReturnCode.ERROR_500.getCode(), fieldError.getDefaultMessage());
//    }


    /**
     * 空指针异常
     */
    @ExceptionHandler(NullPointerException.class)
    public CollectionResult nullPointerExceptionHandler(NullPointerException ex) {
        ex.printStackTrace();
        log.error("空指针异常,异常信息：",ex);
        return CollectionResult.failed(ReturnCode.ERROR_500.getCode(), ex.toString());
    }

    /**
     * 类型转换异常
     */
    @ExceptionHandler(ClassCastException.class)
    public CollectionResult classCastExceptionHandler(ClassCastException ex) {
        ex.printStackTrace();
        log.error("类型转换异常,异常信息：",ex);
        return CollectionResult.failed(ReturnCode.ERROR_500.getCode(), ex.toString());
    }

    /**
     * IO异常
     */
    @ExceptionHandler(IOException.class)
    public CollectionResult iOExceptionHandler(IOException ex) {
        ex.printStackTrace();
        log.error("IO异常,异常信息：",ex);
        return CollectionResult.failed(ReturnCode.ERROR_500.getCode(), ex.toString());
    }

    /**
     * 未知方法异常
     */
    @ExceptionHandler(NoSuchMethodException.class)
    public CollectionResult noSuchMethodExceptionHandler(NoSuchMethodException ex) {
        ex.printStackTrace();
        log.error("未知方法异常,异常信息：",ex);
        return CollectionResult.failed(ReturnCode.ERROR_500.getCode(), ex.toString());
    }

    /**
     * 数组越界异常
     */
    @ExceptionHandler(IndexOutOfBoundsException.class)
    public CollectionResult indexOutOfBoundsExceptionHandler(IndexOutOfBoundsException ex) {
        ex.printStackTrace();
        log.error("数组越界异常,异常信息：",ex);
        return CollectionResult.failed(ReturnCode.ERROR_500.getCode(), ex.toString());
    }

//    /**
//     * 400错误
//     */
//    @ExceptionHandler({HttpMessageNotReadableException.class})
//    public CollectionResult requestNotReadable(HttpMessageNotReadableException ex) {
//        ex.printStackTrace();
//        log.error("HttpMessageNotReadableException,异常信息：",ex);
//        return CollectionResult.failed(ReturnCode.ERROR_400.getCode(), ex.toString());
//    }

    /**
     * 400错误
     */
    @ExceptionHandler({TypeMismatchException.class})
    public CollectionResult requestTypeMismatch(TypeMismatchException ex) {
        ex.printStackTrace();
        log.error("TypeMismatchException,异常信息：",ex);
        return CollectionResult.failed(ReturnCode.ERROR_400.getCode(), ex.toString());
    }

//    /**
//     * 400错误
//     */
//    @ExceptionHandler({MissingServletRequestParameterException.class})
//    public CollectionResult requestMissingServletRequest(MissingServletRequestParameterException ex){
//        return CollectionResult.failed(ReturnCode.ERROR_400.getCode(),"MissingServletRequest");
//    }
//
//    /**
//     * 405错误
//     */
//    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
//    public CollectionResult request405(HttpRequestMethodNotSupportedException ex){
//        return CollectionResult.failed(405,"请求方法不支持");
//    }
//
//    /**
//     * 406错误
//     */
//    @ExceptionHandler({HttpMediaTypeNotAcceptableException.class})
//    public CollectionResult request406(HttpMediaTypeNotAcceptableException ex){
//        return CollectionResult.failed(406,"请求方式不支持");
//    }


    /**
     * 500错误
     */
    @ExceptionHandler({ConversionNotSupportedException.class, RuntimeException.class, HttpMessageNotWritableException.class})
    public CollectionResult server500(Exception ex) {
        ex.printStackTrace();
        log.error("系统错误,异常信息：",ex);
        return CollectionResult.failed(ReturnCode.ERROR_500.getCode(), ex.toString());
    }

}
