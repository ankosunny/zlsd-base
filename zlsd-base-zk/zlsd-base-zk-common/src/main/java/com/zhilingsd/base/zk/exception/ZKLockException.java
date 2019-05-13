package com.zhilingsd.base.zk.exception;

public class ZKLockException extends RuntimeException {

    public ZKLockException(String msg) {
        super(msg);
    }

    public ZKLockException(String msg, Throwable throwable) {
        super(msg, throwable);
    }
}
