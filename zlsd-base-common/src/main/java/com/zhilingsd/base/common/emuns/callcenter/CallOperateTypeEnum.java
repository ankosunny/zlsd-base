package com.zhilingsd.base.common.emuns.callcenter;

/**
 * 呼叫操作类型
 * */
public enum CallOperateTypeEnum {
    CALL("call","拨打"),
    RING_OFF("ring_off","挂断"),
    ;

    /** 状态码 **/
    private String code;
    /** 状态描述 **/
    private String description;


    CallOperateTypeEnum(String code, String description) {
        this.code = code;
        this.description = description;
    }


    public static CallOperateTypeEnum find(int code) {
        for (CallOperateTypeEnum frs : CallOperateTypeEnum.values()) {
            if (frs.getCode().equals(code)) {
                return frs;
            }
        }
        return null;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}
