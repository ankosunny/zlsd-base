package com.zhilingsd.base.common.exception;

import com.zhilingsd.base.common.emuns.ReturnCode;
import com.zhilingsd.base.common.result.CollectionResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.UnauthorizedException;
import org.mybatis.spring.MyBatisSystemException;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;

/**
 * Description: 统一异常处理
 *
 * @Author zengkai
 * @Date 2019/1/31 16:27
 * Exception                       HTTP Status Code
 * ConversionNotSupportedException         500 (Internal Server Error)
 * HttpMessageNotWritableException         500 (Internal Server Error)
 * HttpMediaTypeNotSupportedException      415 (Unsupported Media Type)
 * HttpMediaTypeNotAcceptableException     406 (Not Acceptable)
 * HttpRequestMethodNotSupportedException  405 (Method Not Allowed)
 * NoSuchRequestHandlingMethodException    404 (Not Found)
 * TypeMismatchException                   400 (Bad Request)
 * HttpMessageNotReadableException         400 (Bad Request)
 * MissingServletRequestParameterException 400 (Bad Request)
 */
@RestControllerAdvice
@Slf4j
public class MyExceptionHandler {

    /**
     * shiro权限验证异常
     */
    @ExceptionHandler(value = UnauthorizedException.class)
    public CollectionResult unauthorizedExceptionHandler(UnauthorizedException ex) {
        ex.printStackTrace();
        log.error("shiro权限验证异常,异常信息：{}",ex.toString());
        return CollectionResult.failed(ReturnCode.ERROR_4003.getCode(), ReturnCode.ERROR_4003.getMsg());
    }

    /**
     * mybatis异常
     */
    @ExceptionHandler(value = MyBatisSystemException.class)
    public CollectionResult myBatisSystemException(MyBatisSystemException ex) {
        ex.printStackTrace();
        log.error("mybatis异常,异常信息：{}",ex.toString());
        return CollectionResult.failed(ReturnCode.ERROR_500.getCode(), ex.toString());
    }


    /**
     * mysql异常
     */
    @ExceptionHandler(value = DataIntegrityViolationException.class)
    public CollectionResult DataIntegrityViolationException(DataIntegrityViolationException ex) {
        ex.printStackTrace();
        log.error("sql错误,异常信息：{}",ex.toString());
        return CollectionResult.failed(ReturnCode.ERROR_500.getCode(), ex.toString());
    }

    /**
     * 自定义业务异常
     */
    @ExceptionHandler(BusinessExceptionSZ.class)
    public CollectionResult businessException(BusinessExceptionSZ ex) {
        ex.printStackTrace();
        log.error("业务异常,异常信息：{}",ex.toString());
        return CollectionResult.failed(ex.getCode(), ex.getMsg());
    }

    /**
     * 参数校验异常
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public CollectionResult methodArgumentNotValidException(MethodArgumentNotValidException ex) {
        ex.printStackTrace();
        log.error("参数校验异常,异常信息：{}",ex.toString());
        FieldError fieldError = ex.getBindingResult().getFieldError();
        return CollectionResult.failed(ReturnCode.ERROR_500.getCode(), fieldError.getDefaultMessage());
    }


    /**
     * 空指针异常
     */
    @ExceptionHandler(NullPointerException.class)
    public CollectionResult nullPointerExceptionHandler(NullPointerException ex) {
        ex.printStackTrace();
        log.error("空指针异常,异常信息：{}",ex.toString());
        return CollectionResult.failed(ReturnCode.ERROR_500.getCode(), ex.toString());
    }

    /**
     * 类型转换异常
     */
    @ExceptionHandler(ClassCastException.class)
    public CollectionResult classCastExceptionHandler(ClassCastException ex) {
        ex.printStackTrace();
        log.error("类型转换异常,异常信息：{}",ex.toString());
        return CollectionResult.failed(ReturnCode.ERROR_500.getCode(), ex.toString());
    }

    /**
     * IO异常
     */
    @ExceptionHandler(IOException.class)
    public CollectionResult iOExceptionHandler(IOException ex) {
        ex.printStackTrace();
        log.error("IO异常,异常信息：{}",ex.toString());
        return CollectionResult.failed(ReturnCode.ERROR_500.getCode(), ex.toString());
    }

    /**
     * 未知方法异常
     */
    @ExceptionHandler(NoSuchMethodException.class)
    public CollectionResult noSuchMethodExceptionHandler(NoSuchMethodException ex) {
        ex.printStackTrace();
        log.error("未知方法异常,异常信息：{}",ex.toString());
        return CollectionResult.failed(ReturnCode.ERROR_500.getCode(), ex.toString());
    }

    /**
     * 数组越界异常
     */
    @ExceptionHandler(IndexOutOfBoundsException.class)
    public CollectionResult indexOutOfBoundsExceptionHandler(IndexOutOfBoundsException ex) {
        ex.printStackTrace();
        log.error("数组越界异常,异常信息：{}",ex.toString());
        return CollectionResult.failed(ReturnCode.ERROR_500.getCode(), ex.toString());
    }

    /**
     * 400错误
     */
    @ExceptionHandler({HttpMessageNotReadableException.class})
    public CollectionResult requestNotReadable(HttpMessageNotReadableException ex) {
        ex.printStackTrace();
        log.error("HttpMessageNotReadableException,异常信息：{}",ex.toString());
        return CollectionResult.failed(ReturnCode.ERROR_400.getCode(), ex.toString());
    }

    /**
     * 400错误
     */
    @ExceptionHandler({TypeMismatchException.class})
    public CollectionResult requestTypeMismatch(TypeMismatchException ex) {
        ex.printStackTrace();
        log.error("TypeMismatchException,异常信息：{}",ex.toString());
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
        log.error("系统错误,异常信息：{}",ex.toString());
        return CollectionResult.failed(ReturnCode.ERROR_500.getCode(), ex.toString());
    }
}
