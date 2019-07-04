package com.zhilingsd.base.common.emuns.agent;

/**
 * 身份类型
 * @author linmenghuai
 * @date 2019-5-14 11:35:24
 * */
public enum EnumIdentityType {
    MANAGER("manager","管理"),
    STAFF("staff","职工"),
    ;

    /** 状态码 **/
    private String code;
    /** 状态描述 **/
    private String description;


    EnumIdentityType(String code, String description) {
        this.code = code;
        this.description = description;
    }


    public static EnumIdentityType find(int code) {
        for (EnumIdentityType frs : EnumIdentityType.values()) {
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
