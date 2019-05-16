package com.zhilingsd.base.common.emuns;

import com.google.common.collect.Lists;
import com.zhilingsd.base.common.bean.KeyValueBean;

import java.util.List;

/**
 * 催记电话类型状态
 *
 * @Author: 刘行
 * @DateTime: 2019/5/9 19:17
 */
public enum CollectionPhoneOriginEnum {
    SELF_PHONE(1, "致电本人"),
    CONTACT_NOT_BANK(2, "银行未提供联系人信息"),
    CALL_CONTACT(3, "致电联系人");

    private int code;
    private String value;

    CollectionPhoneOriginEnum(int code, String value) {
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
        for (CollectionPhoneOriginEnum osEnum : CollectionPhoneOriginEnum.values()) {
            if (osEnum.getCode() == code) {
                return osEnum.getValue();
            }
        }
        return "";
    }

    public static List<KeyValueBean> initParam() {
        List<KeyValueBean> initParamList = Lists.newArrayList();
        for (CollectionPhoneOriginEnum osEnum : CollectionPhoneOriginEnum.values()) {
            KeyValueBean keyValueBean = KeyValueBean.builder().code(osEnum.getCode() + "").name(osEnum.getValue()).build();
            initParamList.add(keyValueBean);
        }
        return initParamList;
    }
}