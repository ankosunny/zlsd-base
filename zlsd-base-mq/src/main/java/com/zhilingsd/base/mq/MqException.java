package com.zhilingsd.base.mq;

public class MqException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public MqException() {
        super();

    }

    public MqException(String message, Throwable throwable) {
        super(message, throwable);

    }

    public MqException(String message) {
        super(message);

    }

    public MqException(Throwable throwable) {
        super(throwable);

    }

}