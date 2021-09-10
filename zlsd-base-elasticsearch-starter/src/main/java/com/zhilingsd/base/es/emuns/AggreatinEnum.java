package com.zhilingsd.base.es.emuns;

/**
 * @author: longhui
 * @description: 质检对象
 * @create: 2020/2/27 17:50
 */
public enum AggreatinEnum {
    COUNT("value_count", "总数"),
    AVG("avg", "平均数"),
    SUM("sum", "求和"),
    MAX("max", "最大值"),
    MIN("min", "最小值"),
    /*类似mysql distinct*/
    CARDINALITY("cardinality","根据值去重");

    private String code;
    private String value;

    AggreatinEnum(String code, String value) {
        this.code = code;
        this.value = value;
    }

    public static String getValueByCode(String code) {
        for (AggreatinEnum osEnum : AggreatinEnum.values()) {
            if (osEnum.getCode().equals(code)) {
                return osEnum.value;
            }
        }
        return null;
    }

    public static AggreatinEnum getByCode(String code) {
        for (AggreatinEnum osEnum : AggreatinEnum.values()) {
            if (osEnum.getCode().equals(code)) {
                return osEnum;
            }
        }
        return null;
    }

    public String getCode() {
        return code;
    }

    public String getValue() {
        return value;
    }

}
