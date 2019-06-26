package com.zhilingsd.base.common.exception;

import com.zhilingsd.base.common.emuns.ReturnCode;

public class MaxSizeException extends BaseException {

    public MaxSizeException(){
    }
    public MaxSizeException(ReturnCode resultCodeEnum) {
        super(resultCodeEnum.getCode(), resultCodeEnum.getMsg());
    }
    public MaxSizeException(ReturnCode resultCodeEnum, String message) {
        super(resultCodeEnum.getCode(), message);
    }
    public MaxSizeException(int code, String message) {
        super(code, message);
    }
    public MaxSizeException(int code, String message, Throwable cause) {
        super(code, message, cause);
    }
}
