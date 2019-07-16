package com.zhilingsd.base.common.emuns.agent;

/**
 * 密码状态
 * @author: yuboliang
 * @date: 2019-07-16
 **/
public enum PwdStatusEnum {
    NORMAL("normal","正常"),
    RESET("reset","重置"),
    ;

    /** 状态码 **/
    private String code;
    /** 状态描述 **/
    private String description;


    PwdStatusEnum(String code, String description) {
        this.code = code;
        this.description = description;
    }


    public static PwdStatusEnum find(int code) {
        for (PwdStatusEnum frs : PwdStatusEnum.values()) {
            if (frs.getCode().equals(code)) {
                return frs;
            }
        }
        return null;
    }
    public static String findByCode(String code) {
        for (PwdStatusEnum frs : PwdStatusEnum.values()) {
            if (frs.getCode().equals(code)) {
                return frs.getDescription();
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
