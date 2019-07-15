package com.zhilingsd.base.common.emuns.cif;

/**
 * Created by chenzongbo on 2019/7/11.
 */
public enum AccountStatusEnum {
    UNVERIFIED("unverified", "未验证"), //未验证
    VERIFIED("verified", "生效"),  //生效
    EXPIRED("expired", "失效")  //失效
    ;

    private String code;


    private String msg;

    AccountStatusEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    /**
     * 根据value获取对应的枚举
     */
    public static AccountStatusEnum getEnumByCode(String value) {
        if (value == null) {
            return null;
        }
        for (AccountStatusEnum tEnum : values()) {
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
