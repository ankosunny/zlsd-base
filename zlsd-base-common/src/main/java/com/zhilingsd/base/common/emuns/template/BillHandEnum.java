package com.zhilingsd.base.common.emuns.template;

import com.google.common.collect.Lists;
import com.zhilingsd.base.common.bean.KeyValueBean;

import java.util.List;

/**
 * 案件手别描述
 *
 * @Author: 刘行
 * @DateTime: 2019/5/9 19:17
 */
public enum BillHandEnum {
    SHOUBIE("shoubie", "手别"),
    ZHANGLING("zhangling", "账龄");

    private String code;
    private String value;

    BillHandEnum(String code, String value) {
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
        for (BillHandEnum osEnum : BillHandEnum.values()) {
            if (osEnum.getCode() == code) {
                return osEnum.getValue();
            }
        }
        return "";
    }

    public static List<KeyValueBean> initParam() {
        List<KeyValueBean> initParamList = Lists.newArrayList();
        for (BillHandEnum osEnum : BillHandEnum.values()) {
            KeyValueBean keyValueBean = KeyValueBean.builder().code(osEnum.getCode() + "").name(osEnum.getValue()).build();
            initParamList.add(keyValueBean);
        }
        return initParamList;
    }
}