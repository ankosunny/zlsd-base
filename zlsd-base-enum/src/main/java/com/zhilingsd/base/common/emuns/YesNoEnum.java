package com.zhilingsd.base.common.emuns;

/**
 * ^---^---^---^---^---^---^---^
 * --v---v---v---v---v---v---v--
 *
 * @author zou.cp
 * @version 1.0
 * @Description  yes or no  还款是否结算 是否有效 使用中
 * @createTime 2019年05月10日 14:51*
 * log.info()
 */
public enum YesNoEnum {

    /**
     */
    YES("yes","是"),
    NO("no","否"),
    ;

    /**
     */
    private String code;
    private String value;

    YesNoEnum(String code,String value) {
        this.code = code;
        this.value = value;
    }

    public static YesNoEnum find(String code) {
        for (YesNoEnum frs : YesNoEnum.values()) {
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

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
