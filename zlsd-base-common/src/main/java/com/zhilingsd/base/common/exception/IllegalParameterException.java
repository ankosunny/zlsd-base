package com.zhilingsd.base.common.exception;


import com.zhilingsd.base.common.bean.IReturnCode;
import com.zhilingsd.base.common.emuns.ReturnCode;

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

    public IllegalParameterException(IReturnCode iReturnCode) {
        super(iReturnCode.getCode(), iReturnCode.getMsg());
    }

    public IllegalParameterException(String message) {
        super(ReturnCode.ERROR_117.getCode(), message);
    }

    public IllegalParameterException(ReturnCode resultCodeEnum, String message) {
        super(resultCodeEnum.getCode(), message);
    }

    public IllegalParameterException(int code, String message) {
        super(code, message);
    }

    public IllegalParameterException(int code, String message, Throwable cause) {
        super(code, message, cause);
    }

    public IllegalParameterException(int code, String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(code, message, cause, enableSuppression, writableStackTrace);
    }
}
