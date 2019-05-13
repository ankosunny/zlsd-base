package com.zhilingsd.base.zk.exception;

public class ZKLockTimeoutException extends RuntimeException {
    public ZKLockTimeoutException(String msg) {
        super(msg);
    }
}
