package com.zhilingsd.collection.common.aspect;

/**
 * 检验结果
 * @author linmenghuai
 * @date 2019-4-30 18:02:12
 * */
public class ValidationResult {

    private Boolean isSuccess;
    private String errMsg;

    public Boolean getSuccess() {
        return isSuccess;
    }

    public void setSuccess(Boolean success) {
        isSuccess = success;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }
}
