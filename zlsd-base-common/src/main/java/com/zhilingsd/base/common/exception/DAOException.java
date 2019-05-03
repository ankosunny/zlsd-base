package com.zhilingsd.base.common.exception;

import com.zhilingsd.base.common.emuns.ExceptionCodeEnum;

/**
 * Created by chenzongbo on 2017/12/13.
 */
public class DAOException extends BaseException {

    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public DAOException(String code) {
        this.code = code;
    }

    public DAOException(String code, Throwable throwable) {
        super(throwable);
        this.code = code;
        this.setMessage(throwable.getMessage());
    }

    public DAOException(String code, String message) {
        super(message);
        this.code = code;
        this.setMessage(message);
    }

    public DAOException(String code, Throwable throwable, String message) {
        super(message, throwable);
        this.code = code;
        this.setMessage(message);
    }

    public DAOException(ExceptionCodeEnum errorCode) {
        super(errorCode.getMsg());
        this.code = errorCode.getCode();
    }
}
