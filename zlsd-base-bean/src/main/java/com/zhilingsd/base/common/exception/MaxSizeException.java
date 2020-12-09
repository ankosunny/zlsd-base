package com.zhilingsd.base.common.exception;

import com.zhilingsd.base.common.IReturnCode;

public class MaxSizeException extends BaseException {

    public MaxSizeException(){
    }
    public MaxSizeException(IReturnCode iReturnCode) {
        super(iReturnCode.getCode(), iReturnCode.getMsg());
    }
    public MaxSizeException(IReturnCode iReturnCode, String message) {
        super(iReturnCode.getCode(), message);
    }
    public MaxSizeException(int code, String message) {
        super(code, message);
    }
    public MaxSizeException(int code, String message, Throwable cause) {
        super(code, message, cause);
    }
}
