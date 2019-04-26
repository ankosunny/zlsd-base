package com.zhilingsd.collection.common.exception;
import com.zhilingsd.collection.common.emuns.BaseResultCodeEnum;

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

    public BusinessException(BaseResultCodeEnum resultCodeEnum) {
        super(resultCodeEnum.getCode(), resultCodeEnum.getMsg());
    }

    public BusinessException(String message) {
        super(BaseResultCodeEnum.BUSINESS_ERROR.getCode(), message);
    }

    public BusinessException(BaseResultCodeEnum resultCodeEnum, String message) {
        super(resultCodeEnum.getCode(), message);
    }

    public BusinessException(String code, String message) {
        super(code, message);
    }

    public BusinessException(String code, String message, Throwable cause) {
        super(code, message, cause);
    }

    public BusinessException(String code, String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(code, message, cause, enableSuppression, writableStackTrace);
    }
}
