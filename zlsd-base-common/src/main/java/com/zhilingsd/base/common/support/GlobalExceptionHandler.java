package com.zhilingsd.base.common.support;

import com.zhilingsd.base.common.emuns.ReturnCode;
import com.zhilingsd.base.common.exception.*;
import com.zhilingsd.base.common.result.CollectionResult;
import com.zhilingsd.base.common.result.CommonResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
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
 *
 * @author 吞星
 * @date 2018/4/12
 * <p>
 * <p>
 * * ConversionNotSupportedException         500 (Internal Server Error)
 * * HttpMessageNotWritableException         500 (Internal Server Error)
 * * HttpMediaTypeNotSupportedException      415 (Unsupported Media Type)
 * * HttpMediaTypeNotAcceptableException     406 (Not Acceptable)
 * * HttpRequestMethodNotSupportedException  405 (Method Not Allowed)
 * * NoSuchRequestHandlingMethodException    404 (Not Found)
 * * TypeMismatchException                   400 (Bad Request)
 * * HttpMessageNotReadableException         400 (Bad Request)
 * * MissingServletRequestParameterException 400 (Bad Request)
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {


    /**
     * 应用到所有@RequestMapping注解方法，在其执行之前初始化数据绑定器
     *
     * @param binder
     */
    @InitBinder
    public void initBinder(WebDataBinder binder) {
    }

    private CommonResult returnErr(Exception e, String errMsg, int code) {
        log.error(e.getMessage(), e);
        CommonResult result = new CommonResult();
        result.setCode(code);
        result.setMsg(e.getMessage());
        result.setSysTime(String.valueOf(System.currentTimeMillis()));
        return result;
    }

    private CommonResult returnErr(BaseException e, String errMsg) {
        log.error(e.getMessage(), e);
        CommonResult result = new CommonResult();
        result.setCode(e.getCode());
        result.setMsg(e.getMessage());
        result.setSysTime(String.valueOf(System.currentTimeMillis()));
        return result;
    }

    private CommonResult returnErr(String errMsg, int code, Exception e) {
        log.error(e.getMessage(), e);
        CommonResult result = new CommonResult();
        result.setCode(code);
        result.setMsg(errMsg);
        result.setSysTime(String.valueOf(System.currentTimeMillis()));
        return result;
    }

    private CommonResult returnErr(int code, Exception e) {
        log.error(e.getMessage(), e);
        CommonResult result = new CommonResult();
        result.setCode(code);
        result.setMsg(e.getMessage());
        result.setSysTime(String.valueOf(System.currentTimeMillis()));
        return result;
    }

    private CommonResult returnErr(int code, Exception e, String errMsg) {
        log.error(e.getMessage(), e);
        CommonResult result = new CommonResult();
        result.setCode(code);
        result.setMsg(errMsg);
        result.setSysTime(String.valueOf(System.currentTimeMillis()));
        return result;
    }

    /**
     * 全局异常捕获
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = Exception.class)
    public CommonResult errorHandler(Exception e) {
        return returnErr(ReturnCode.ERROR_500.getCode(), e);
    }

    /**
     * 缺少请求参数异常处理
     * 400 - Bad Request
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public CommonResult handleMissingServletRequestParameterException(MissingServletRequestParameterException e) {
        return returnErr(ReturnCode.ERROR_400.getCode(), e);
    }

    /**
     * 参数解析失败
     * 400 - Bad Request
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public CommonResult handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        return returnErr(ReturnCode.ERROR_400.getCode(), e, "参数解析失败");

    }

    /**
     * 400 - Bad Request
     * 参数验证失败
     *
     * @param e
     * @return
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public CommonResult handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {

        BindingResult bindingResult = e.getBindingResult();
        StringBuilder errorMessage = new StringBuilder("参数校验错误:");
        if (null != bindingResult && !CollectionUtils.isEmpty(bindingResult.getFieldErrors())) {
            String message = bindingResult.getFieldErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining(","));
            errorMessage.append(message);
        }
        return returnErr(errorMessage.toString(), ReturnCode.ERROR_400.getCode(), e);
    }

    /**
     * 400 - Bad Request
     * 参数绑定失败
     *
     * @param e
     * @return
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BindException.class)
    public CommonResult handleBindException(BindException e) {
        BindingResult bindingResult = e.getBindingResult();
        StringBuilder errorMessage = new StringBuilder("参数校验错误:");
        if (null != bindingResult && !CollectionUtils.isEmpty(bindingResult.getFieldErrors())) {
            String message = bindingResult.getFieldErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining(","));
            errorMessage.append(message);
        }
        return returnErr(errorMessage.toString(), ReturnCode.ERROR_400.getCode(), e);
    }

    /**
     * 400 - Bad Request
     * 参数验证失败
     *
     * @param e
     * @return
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public CommonResult handleServiceException(ConstraintViolationException e) {
        return returnErr(ReturnCode.ERROR_400.getCode(), e);
    }

    /**
     * 400 - Bad Request
     * 参数验证失败
     *
     * @param e
     * @return
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ValidationException.class)
    public CommonResult handleValidationException(ValidationException e) {
        return returnErr(ReturnCode.ERROR_400.getCode(), e);
    }

    /**
     * 415 - UNSUPPORTED_MEDIA_TYPE
     *
     * @param e
     * @return
     */
    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public CommonResult handleHttpMediaTypeNotSupportedException(Exception e) {
        return returnErr(ReturnCode.ERROR_400.getCode(), e);
    }

    /**
     * 统一处理405异常
     *
     * @param e
     * @return
     */
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public CommonResult handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        return returnErr(ReturnCode.ERROR_405.getCode(), e);
    }


    /**
     * 捕获处理WebException
     *
     * @param e
     * @return
     * @throws Exception
     */
    @ExceptionHandler(value = WebException.class)
    public CommonResult webErrorHandler(WebException e) throws Exception {
        return returnErr(ReturnCode.ERROR_500.getCode(), e);
    }


    /**
     * 捕获处理DAOException
     *
     * @param e
     * @return
     * @throws Exception
     */
    @ExceptionHandler(value = DAOException.class)
    public CommonResult daoErrorHandler(DAOException e) throws Exception {
        return returnErr(ReturnCode.ERROR_500.getCode(), e);
    }

    /**
     * 捕获处理BaseException
     *
     * @param e
     * @return
     * @throws Exception
     */
    @ExceptionHandler(value = BaseException.class)
    public CommonResult baseErrorHandler(BaseException e) throws Exception {
        return returnErr(ReturnCode.UN_SUCCESS.getCode(), e);
    }

    /**
     * 捕获处理ServiceException
     *
     * @param e
     * @return
     * @throws Exception
     */
    @ExceptionHandler(value = ServiceException.class)
    public CommonResult serviceErrorHandler(ServiceException e) throws Exception {
        return returnErr(ReturnCode.UN_SUCCESS.getCode(), e);
    }


    /**
     * 捕获处理IllegalParameterException
     *
     * @param e
     * @return
     * @throws Exception
     */
    @ExceptionHandler(value = IllegalParameterException.class)
    public CommonResult illegalParameterErrorHandler(IllegalParameterException e) throws Exception {
        return returnErr(ReturnCode.ERROR_500.getCode(), e);
    }

    /**
     * 捕获处理IllegalParameterException
     *
     * @param e
     * @return
     * @throws Exception
     */
    @ExceptionHandler(value = MaxSizeException.class)
    public CommonResult illegalParameterErrorHandler(MaxSizeException e) throws Exception {
        return returnErr(ReturnCode.ERROR_500.getCode(), e);
    }


    /**
     * shiro权限验证异常
     */
    @ExceptionHandler(value = UnauthorizedException.class)
    public CollectionResult unauthorizedExceptionHandler(UnauthorizedException ex) {
        ex.printStackTrace();
        log.error("shiro权限验证异常,异常信息：", ex);
        return CollectionResult.failed(ReturnCode.ERROR_4003.getCode(), ReturnCode.ERROR_4003.getMsg());
    }


    /**
     * mysql异常
     */
    @ExceptionHandler(value = DataIntegrityViolationException.class)
    public CollectionResult DataIntegrityViolationException(DataIntegrityViolationException ex) {
        ex.printStackTrace();
        log.error("sql错误,异常信息：", ex);
        return CollectionResult.failed(ReturnCode.ERROR_500.getCode(), ex.toString());
    }

    /**
     * 功能描述：发生sql语法错误时候，不返回具体异常，避免直接报sql暴露给前端
     *
     * @param ex
     * @retun com.zhilingsd.base.common.result.CollectionResult
     * @auther 吞星（yangguojun）
     * @date 2020/11/11-18:32
     */
    @ExceptionHandler(value = BadSqlGrammarException.class)
    public CollectionResult DataIntegrityViolationException(BadSqlGrammarException ex) {
        ex.printStackTrace();
        log.error("sql错误,异常信息：", ex);
        return CollectionResult.failed(ReturnCode.ERROR_500.getCode(), "sql语法错误");
    }


    /**
     * 自定义业务异常
     */
    @ExceptionHandler(BusinessException.class)
    public CollectionResult businessException(BusinessException ex) {
        ex.printStackTrace();
        log.warn("业务异常,异常信息：", ex);
        return CollectionResult.failed(ex.getCode(), ex.getMessage());
    }


    /**
     * 空指针异常
     */
    @ExceptionHandler(NullPointerException.class)
    public CollectionResult nullPointerExceptionHandler(NullPointerException ex) {
        ex.printStackTrace();
        log.error("空指针异常,异常信息：", ex);
        return CollectionResult.failed(ReturnCode.ERROR_500.getCode(), ex.toString());
    }

    /**
     * 类型转换异常
     */
    @ExceptionHandler(ClassCastException.class)
    public CollectionResult classCastExceptionHandler(ClassCastException ex) {
        ex.printStackTrace();
        log.error("类型转换异常,异常信息：", ex);
        return CollectionResult.failed(ReturnCode.ERROR_500.getCode(), ex.toString());
    }

    /**
     * IO异常
     */
    @ExceptionHandler(IOException.class)
    public CollectionResult iOExceptionHandler(IOException ex) {
        ex.printStackTrace();
        log.error("IO异常,异常信息：", ex);
        return CollectionResult.failed(ReturnCode.ERROR_500.getCode(), ex.toString());
    }

    /**
     * 未知方法异常
     */
    @ExceptionHandler(NoSuchMethodException.class)
    public CollectionResult noSuchMethodExceptionHandler(NoSuchMethodException ex) {
        ex.printStackTrace();
        log.error("未知方法异常,异常信息：", ex);
        return CollectionResult.failed(ReturnCode.ERROR_500.getCode(), ex.toString());
    }

    /**
     * 数组越界异常
     */
    @ExceptionHandler(IndexOutOfBoundsException.class)
    public CollectionResult indexOutOfBoundsExceptionHandler(IndexOutOfBoundsException ex) {
        ex.printStackTrace();
        log.error("数组越界异常,异常信息：", ex);
        return CollectionResult.failed(ReturnCode.ERROR_500.getCode(), ex.toString());
    }


    /**
     * 400错误
     */
    @ExceptionHandler({TypeMismatchException.class})
    public CollectionResult requestTypeMismatch(TypeMismatchException ex) {
        ex.printStackTrace();
        log.error("TypeMismatchException,异常信息：", ex);
        return CollectionResult.failed(ReturnCode.ERROR_400.getCode(), ex.toString());
    }


    /**
     * 500错误
     */
    @ExceptionHandler({ConversionNotSupportedException.class, RuntimeException.class, HttpMessageNotWritableException.class})
    public CollectionResult server500(Exception ex) {
        ex.printStackTrace();
        log.error("系统错误,异常信息：", ex);
        return CollectionResult.failed(ReturnCode.ERROR_500.getCode(), ex.toString());
    }

}
