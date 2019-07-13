package com.zhilingsd.base.common.emuns.bill;

/**
 *  电催 电话状态
 * @program 智灵时代广州研发中心
 * @author ant man(tuzhen)
 * @create 2019/7/5 17:02
 **/
public enum PhoneStatusEnum {
    UNKNOWN("weizhi","未知"),
    VALID("youxiao","有效"),
    INVALID("wuxiao","无效");

    /** 状态码 **/
    private String code;
    /** 状态描述 **/
    private String description;

    PhoneStatusEnum(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public static PhoneStatusEnum find(String code) {
        for (PhoneStatusEnum recordType : PhoneStatusEnum.values()) {
            if (recordType.getCode().equals(code)) {
                return recordType;
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
