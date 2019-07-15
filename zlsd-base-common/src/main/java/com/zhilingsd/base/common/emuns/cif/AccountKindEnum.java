package com.zhilingsd.base.common.emuns.cif;

/**
 * Created by chenzongbo on 2019/7/11.
 */
public enum AccountKindEnum {
    ORG("org", "对公"), //对公
    PERSON("person", "个人")  //个人
    ;

    private String code;


    private String msg;

    AccountKindEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    /**
     * 根据value获取对应的枚举
     */
    public static AccountKindEnum getEnumByCode(String value) {
        if (value == null) {
            return null;
        }
        for (AccountKindEnum tEnum : values()) {
            if (value.equals(tEnum.getCode())) {
                return tEnum;
            }
        }
        return null;
    }


    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

}
