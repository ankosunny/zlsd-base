package com.zhilingsd.base.common.exception;

import com.zhilingsd.base.common.emuns.BaseResultCodeEnum;

public class QueryMaxSizeException extends BaseException {

    public QueryMaxSizeException(){
    }
    public QueryMaxSizeException(BaseResultCodeEnum resultCodeEnum) {
        super(resultCodeEnum.getCode(), resultCodeEnum.getMsg());
    }
    public QueryMaxSizeException(BaseResultCodeEnum resultCodeEnum, String message) {
        super(resultCodeEnum.getCode(), message);
    }
    public QueryMaxSizeException(String code, String message) {
        super(code, message);
    }
    public QueryMaxSizeException(String code, String message, Throwable cause) {
        super(code, message, cause);
    }
}
