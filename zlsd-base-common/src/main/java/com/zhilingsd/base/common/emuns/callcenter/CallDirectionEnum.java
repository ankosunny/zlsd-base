package com.zhilingsd.base.common.emuns.callcenter;

/**
 * 呼叫类型
 */
public enum CallDirectionEnum {

    CALL_IN("1","呼入"),
    CALL_OUT("2","呼出");

    private String code;

    private String desc;


    CallDirectionEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
