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
public enum CollectionOriginTypeEnum {
    ORIGIN_RECORD("originRecord", "原始催记"),
    WORK_ORDER("workOrder", "工单");

    private String code;
    private String value;

    CollectionOriginTypeEnum(String code, String value) {
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
        for (CollectionOriginTypeEnum osEnum : CollectionOriginTypeEnum.values()) {
            if (osEnum.code.equals(code)) {
                return osEnum.getValue();
            }
        }
        return "";
    }

    public static List<KeyValueBean> initParam() {
        List<KeyValueBean> initParamList = Lists.newArrayList();
        for (CollectionOriginTypeEnum osEnum : CollectionOriginTypeEnum.values()) {
            KeyValueBean keyValueBean = KeyValueBean.builder().code(osEnum.getCode() + "").name(osEnum.getValue()).build();
            initParamList.add(keyValueBean);
        }
        return initParamList;
    }
}