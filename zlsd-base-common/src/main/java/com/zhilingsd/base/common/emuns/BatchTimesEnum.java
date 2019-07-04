package com.zhilingsd.base.common.emuns;

import com.google.common.collect.Lists;
import com.zhilingsd.base.common.bean.KeyValueBean;

import java.util.List;

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

    BATCH_TIMES_1("1", "一手"),
    BATCH_TIMES_2("2", "二手"),
    BATCH_TIMES_3("3", "三手"),
    BATCH_TIMES_4("4", "四手"),
    BATCH_TIMES_5("5", "五手"),
    BATCH_TIMES_6("6", "六手"),
    BATCH_TIMES_7("7", "七手"),
    BATCH_TIMES_8("8", "八手"),
    BATCH_TIMES_9("9", "九手"),
    ;
    private String code;
    private String value;

    BatchTimesEnum(String code, String value) {
        this.code = code;
        this.value = value;
    }

    public String getCode() {
        return code;
    }

    public String getValue() {
        return value;
    }

    public static String getValueByCode(String code) {
        for (BatchTimesEnum osEnum : BatchTimesEnum.values()) {
            if (osEnum.getCode().equals(code)) {
                return osEnum.value;
            }
        }
        return "";
    }

    public static BatchTimesEnum getByCode(int code) {
        for (BatchTimesEnum osEnum : BatchTimesEnum.values()) {
            if (osEnum.getCode().equals(code)) {
                return osEnum;
            }
        }
        return null;
    }

    public static List<KeyValueBean> initParam(){
        List<KeyValueBean> initParamList = Lists.newArrayList();
        for (BatchTimesEnum osEnum: BatchTimesEnum.values()){
            KeyValueBean keyValueBean = KeyValueBean.builder().code(osEnum.getCode()+"").name(osEnum.getValue()).build();
            initParamList.add(keyValueBean);
        }
        return initParamList;
    }

}
