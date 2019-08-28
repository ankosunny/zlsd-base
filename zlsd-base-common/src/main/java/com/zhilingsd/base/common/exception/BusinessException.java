package com.zhilingsd.base.common.exception;

import com.zhilingsd.base.common.bean.IReturnCode;
import com.zhilingsd.base.common.emuns.ReturnCode;

/**
 * 业务异常
 *
 * @author zhangrong67
 * @version 1.0
 * @since 1.0 2017年12月12日 21:23
 */
public class BusinessException extends BaseException {
    private static final long serialVersionUID = -9103217173522466144L;

    public BusinessException() {
    }

    public BusinessException(IReturnCode iReturnCode) {
        super(iReturnCode.getCode(), iReturnCode.getMsg());
    }

    public BusinessException(String message) {
        super(ReturnCode.BUSINESS_ERROR.getCode(), message);
    }

    public BusinessException(IReturnCode iReturnCode, String message) {
        super(iReturnCode.getCode(), message);
    }

    public BusinessException(int code, String message) {
        super(code, message);
    }

    public BusinessException(int code, String message, Throwable cause) {
        super(code, message, cause);
    }

    public BusinessException(int code, String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(code, message, cause, enableSuppression, writableStackTrace);
    }
}
