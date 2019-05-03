package com.zhilingsd.collection.common.emuns;

/**
 * ^---^---^---^---^---^---^---^
 * --v---v---v---v---v---v---v--
 *
 * @author 刘行
 * @version 1.0
 * @className com.zhilingsd.enums.java
 * @Description 手别
 * @createTime 2019年04月22日 12:13*
 * log.info()
 */
public enum NotStanTemplateEnum {

    LETTER(1, "函件"),
    CERTIFICATE(2, "凭证")
    ;
    private int code;
    private String value;

    NotStanTemplateEnum(int code, String value) {
        this.code = code;
        this.value = value;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public static String getValueByCode(int code) {
        for (NotStanTemplateEnum osEnum : NotStanTemplateEnum.values()) {
            if (osEnum.getCode() == code) {
                return osEnum.getValue();
            }
        }
        return "";
    }

}
