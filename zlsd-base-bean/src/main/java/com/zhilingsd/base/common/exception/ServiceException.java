package com.zhilingsd.base.common.exception;


/**
 * Created by chenzongbo on 2017/12/13.
 */
public class ServiceException extends BaseException {

    private int code;

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public void setCode(int code) {
        this.code = code;
    }

    public ServiceException(int code) {
        this.code = code;
    }

    public ServiceException(int code, Throwable throwable) {
        super(throwable);
        this.code = code;
    }

    public ServiceException(int code, String message) {
        super(message);
        this.code = code;
    }

    public ServiceException(int code, Throwable throwable, String message) {
        super(message, throwable);
        this.code = code;
    }

}
