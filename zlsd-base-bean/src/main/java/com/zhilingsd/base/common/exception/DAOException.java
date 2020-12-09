package com.zhilingsd.base.common.exception;

import com.zhilingsd.base.common.IReturnCode;
import com.zhilingsd.base.common.emuns.ReturnCode;

/**
 * Created by chenzongbo on 2017/12/13.
 */
public class DAOException extends BaseException {

    private int code;

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public void setCode(int code) {
        this.code = code;
    }

    public DAOException(IReturnCode iReturnCode){
        this.code = iReturnCode.getCode();
        this.setMessage(iReturnCode.getMsg());
    }

    public DAOException(String message) {
        super(ReturnCode.BUSINESS_ERROR.getCode(), message);
    }


    public DAOException(int code, Throwable throwable) {
        super(throwable);
        this.code = code;
        this.setMessage(throwable.getMessage());
    }

    public DAOException(int code, String message) {
        super(message);
        this.code = code;
        this.setMessage(message);
    }

    public DAOException(int code, Throwable throwable, String message) {
        super(message, throwable);
        this.code = code;
        this.setMessage(message);
    }
}
