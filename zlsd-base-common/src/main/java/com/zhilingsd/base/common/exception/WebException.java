package com.zhilingsd.base.common.exception;

/**
 * 此类是针对控制层向外抛出的异常，非控制层不要向上抛此异常
 * @author 吞星
 */
public class WebException extends BaseException {
  
    private static final long serialVersionUID = 1L;

    private int code;

    private String message;

    public WebException() {
        super();
    }

    public WebException(int code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public WebException(int code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
        this.message = message;
    }

    public WebException(int code, String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(code,message, cause, enableSuppression, writableStackTrace);
        this.code = code;
        this.message = message;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public void setMessage(String message) {
        this.message = message;
    }

}
