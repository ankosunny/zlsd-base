package com.zhilingsd.base.redis.exception;

/**
 * redis锁异常
 *
 */
public class RedisLockException extends RuntimeException {

    /**
     *
     */
    private static final long serialVersionUID = -5537931749639972842L;
    private String code;

    public RedisLockException(String code) {
        this.code = code;
    }

    public RedisLockException(String code, Throwable throwable) {
        super(throwable);
        this.code = code;
    }

    public RedisLockException(String code, String message) {
        super(message);
        this.code = code;
    }

    public RedisLockException(String code, Throwable throwable, String message) {
        super(message, throwable);
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

 /*   public RedisLockException(ExceptionCodeEnum errorCode) {
        super(errorCode.getMsg());
        this.code = errorCode.getCode();
    }*/
}
