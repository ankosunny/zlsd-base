package com.zhilingsd.base.common.emuns.bill;

public enum CollectionPhoneOriginEnum {

    ONESELF("oneself","致电本人"),
    SELF_QUERY("self_query","银行未提供联系人信息"),
    BANK_PROVIDE("bank_provide","致电联系人");

    /** 状态码 **/
    private String code;
    /** 状态描述 **/
    private String description;

    CollectionPhoneOriginEnum(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public static CollectionPhoneOriginEnum find(String code) {
        for (CollectionPhoneOriginEnum record : CollectionPhoneOriginEnum.values()) {
            if (record.getCode().equals(code)) {
                return record;
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
