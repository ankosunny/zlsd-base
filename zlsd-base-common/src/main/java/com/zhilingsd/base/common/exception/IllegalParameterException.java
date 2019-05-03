package com.zhilingsd.base.common.exception;


import com.zhilingsd.base.common.emuns.BaseResultCodeEnum;

/**
 * 非法参数异常
 *
 * @author zhangrong67
 * @version 1.0
 * @since 1.0 2017年12月12日 21:21
 */
public class IllegalParameterException extends BaseException {
    private static final long serialVersionUID = -1755602348515168270L;

    public IllegalParameterException() {
        super();
    }

    public IllegalParameterException(BaseResultCodeEnum resultCodeEnum) {
        super(resultCodeEnum.getCode(), resultCodeEnum.getMsg());
    }

    public IllegalParameterException(String message) {
        super(BaseResultCodeEnum.ILLEGAL_ARGUMENT.getCode(), message);
    }

    public IllegalParameterException(BaseResultCodeEnum resultCodeEnum, String message) {
        super(resultCodeEnum.getCode(), message);
    }

    public IllegalParameterException(String code, String message) {
        super(code, message);
    }

    public IllegalParameterException(String code, String message, Throwable cause) {
        super(code, message, cause);
    }

    public IllegalParameterException(String code, String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(code, message, cause, enableSuppression, writableStackTrace);
    }
}
