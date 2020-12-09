package com.zhilingsd.base.common.exception;

/**
 * 基础异常类
 *
 * @author zhangrong67
 * @version 1.0
 * @since 1.0 2017年12月12日 21:20
 */
public class BaseException extends RuntimeException {
    private static final long serialVersionUID = 1218030605042529457L;

    private int code;

    private String message;

    public BaseException() {
        super();
    }
    
    public BaseException(Throwable cause) {
        super(cause);
    }
    public BaseException(String message, Throwable cause) {
        super(message, cause);
    }

    public BaseException(String message) {
        super(message);
        this.message = message;
    }
    public BaseException(int code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public BaseException(int code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
        this.message = message;
    }

    public BaseException(int code, String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
