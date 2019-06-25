package com.zhilingsd.base.common.emuns;

import com.google.common.collect.Lists;
import com.zhilingsd.base.common.bean.KeyValueBean;

import java.util.List;

/**
 * 催记类型状态
 *
 * @Author: 刘行
 * @DateTime: 2019/5/9 19:17
 */
public enum CollectionMarkTypeEnum {
    PHONE_COLLECTION("diancui", "电催"),
    WORK_ORDER("baojing", "报警"),
    VISIT("waifang", "外访"),
    LETTER("xinhan", "信函");

    private String code;
    private String value;

    CollectionMarkTypeEnum(String code, String value) {
        this.code = code;
        this.value = value;
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

    public static String getValueByCode(String code) {
        for (CollectionMarkTypeEnum osEnum : CollectionMarkTypeEnum.values()) {
            if (osEnum.getCode() == code) {
                return osEnum.getValue();
            }
        }
        return "";
    }

    public static List<KeyValueBean> initParam() {
        List<KeyValueBean> initParamList = Lists.newArrayList();
        for (CollectionMarkTypeEnum osEnum : CollectionMarkTypeEnum.values()) {
            KeyValueBean keyValueBean = KeyValueBean.builder().code(osEnum.getCode() + "").name(osEnum.getValue()).build();
            initParamList.add(keyValueBean);
        }
        return initParamList;
    }
}