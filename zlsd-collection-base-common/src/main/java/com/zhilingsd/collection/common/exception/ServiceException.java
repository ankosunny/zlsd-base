package com.zhilingsd.collection.common.exception;


import com.zhilingsd.collection.common.emuns.ExceptionCodeEnum;

/**
 * Created by chenzongbo on 2017/12/13.
 */
public class ServiceException extends BaseException {

    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public ServiceException(String code) {
        this.code = code;
    }

    public ServiceException(String code, Throwable throwable) {
        super(throwable);
        this.code = code;
    }

    public ServiceException(String code, String message) {
        super(message);
        this.code = code;
    }

    public ServiceException(String code, Throwable throwable, String message) {
        super(message, throwable);
        this.code = code;
    }

    public ServiceException(ExceptionCodeEnum errorCode) {
        super(errorCode.getMsg());
        this.code = errorCode.getCode();
    }
}
