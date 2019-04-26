package com.zhilingsd.collection.common.emuns;

/**
 * 签名类型
 *
 * @author zhangrong67
 * @version 1.0
 * @since 1.0 2018年01月25日 16:01
 */
public enum SignTypeEnum {
    /**
     * md5
     */
    MD5("md5"),;

    /**
     * 签名类型
     */
    private String code;

    SignTypeEnum(String code) {
        this.code = code;
    }

    public static SignTypeEnum find(String code) {
        for (SignTypeEnum frs : SignTypeEnum.values()) {
            if (frs.getCode().equals(code)) {
                return frs;
            }
        }
        return null;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
