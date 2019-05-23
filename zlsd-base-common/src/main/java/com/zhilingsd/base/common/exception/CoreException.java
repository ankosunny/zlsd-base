package com.zhilingsd.base.common.exception;

import com.zhilingsd.base.common.emuns.BaseResultCodeEnum;

/**
 * 接口异常
 * @author linmenghuai
 * @date 2019-5-22 11:26:58
 * */
public class CoreException extends BaseException {
    private static final long serialVersionUID = -9103217173522466144L;

    public CoreException() {
    }

    public CoreException(BaseResultCodeEnum resultCodeEnum) {
        super(resultCodeEnum.getCode(), resultCodeEnum.getMsg());
    }

    public CoreException(String message) {
        super(BaseResultCodeEnum.BUSINESS_ERROR.getCode(), message);
    }

    public CoreException(BaseResultCodeEnum resultCodeEnum, String message) {
        super(resultCodeEnum.getCode(), message);
    }

    public CoreException(String code, String message) {
        super(code, message);
    }

    public CoreException(String code, String message, Throwable cause) {
        super(code, message, cause);
    }

    public CoreException(String code, String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(code, message, cause, enableSuppression, writableStackTrace);
    }
}
