package com.zhilingsd.base.common.emuns;

/**
 * ^---^---^---^---^---^---^---^
 * --v---v---v---v---v---v---v--
 *
 * @author zou.cp
 * @version 1.0
 * @className com.zhilingsd.enums.java
 * @Description 手别
 * @createTime 2019年04月22日 12:13*
 * log.info()
 */
public enum BatchTimesEnum {

    BATCH_TIMES_1(1, "一手"),
    BATCH_TIMES_2(2, "二手"),
    BATCH_TIMES_3(3, "三手"),
    BATCH_TIMES_4(4, "四手"),
    BATCH_TIMES_5(5, "五手"),
    BATCH_TIMES_6(6, "六手"),
    BATCH_TIMES_7(7, "七手"),
    BATCH_TIMES_8(8, "八手"),
    BATCH_TIMES_9(9, "九手"),
    ;
    private int code;
    private String value;

    BatchTimesEnum(int code, String value) {
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
        for (BatchTimesEnum osEnum : BatchTimesEnum.values()) {
            if (osEnum.getCode() == code) {
                return osEnum.getValue();
            }
        }
        return "";
    }

}
