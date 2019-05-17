package com.zhilingsd.base.common.exception;

import com.zhilingsd.base.common.emuns.BaseResultCodeEnum;

public class MaxSizeException extends BaseException {

    public MaxSizeException(){
    }
    public MaxSizeException(BaseResultCodeEnum resultCodeEnum) {
        super(resultCodeEnum.getCode(), resultCodeEnum.getMsg());
    }
    public MaxSizeException(BaseResultCodeEnum resultCodeEnum, String message) {
        super(resultCodeEnum.getCode(), message);
    }
    public MaxSizeException(String code, String message) {
        super(code, message);
    }
    public MaxSizeException(String code, String message, Throwable cause) {
        super(code, message, cause);
    }
}
