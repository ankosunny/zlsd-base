package com.zhilingsd.base.common.emuns.callcenter;

public enum CallTypeEnum {
    CALLED("called","被叫"),
    CALLING("calling","主叫"),
    INTERNAL("internal","内部呼叫"),
    ;
    /** 状态码 **/
    private String code;
    /** 状态描述 **/
    private String description;


    CallTypeEnum(String code, String description) {
        this.code = code;
        this.description = description;
    }


    public static CallTypeEnum find(String code) {
        for (CallTypeEnum frs : CallTypeEnum.values()) {
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
