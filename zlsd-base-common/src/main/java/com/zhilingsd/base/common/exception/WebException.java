package com.zhilingsd.base.common.exception;

/**
 * 此类是针对控制层向外抛出的异常，非控制层不要向上抛此异常
 * @author jacky
 */
public class WebException extends BaseException {
  
    private static final long serialVersionUID = 1L;

    private String code;

    private String message;

    public WebException() {
        super();
    }

    public WebException(String code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public WebException(String code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
        this.message = message;
    }

    public WebException(String code, String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(code,message, cause, enableSuppression, writableStackTrace);
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
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
