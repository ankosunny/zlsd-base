package com.zhilingsd.base.es.emuns;

/**
 * @author: longhui
 * @description: 质检对象
 * @create: 2020/2/27 17:50
 */
public enum AggreatinEnum {
    TERMS("terms", "桶聚合"),
    NESTED("nested", "嵌套聚合"),
    COUNT("count", "总数"),
    AVG("avg", "平均数"),
    SUM("sum", "求和"),
    MAX("max", "最大值"),
    MIN("min", "最小值");

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
