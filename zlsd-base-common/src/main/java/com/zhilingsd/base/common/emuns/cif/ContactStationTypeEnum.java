package com.zhilingsd.base.common.emuns.cif;

import com.google.common.collect.Lists;
import com.zhilingsd.base.common.bean.KeyValueBean;

import java.util.List;

public enum ContactStationTypeEnum {
    TELCONTACTSTATION("tel","电话联系人"),
    EMAILCONTACTSTATION("email","邮箱联系人"),
    FAXCONTACTSTATION("fax","传真联系人"),
    ADDRCONTACTSTATION("addr","地址联系人"),
    ;


    private String code;
    private String value;

    ContactStationTypeEnum(String code, String value) {
        this.code = code;
        this.value = value;
    }

    public static String getValueByCode(String code) {
        for (ContactStationTypeEnum osEnum : ContactStationTypeEnum.values()) {
            if (code.equals(osEnum.getCode())) {
                return osEnum.getValue();
            }
        }
        return "";
    }

    public static List<KeyValueBean> initParam(){
        List<KeyValueBean> initParamList = Lists.newArrayList();
        for (ContactStationTypeEnum osEnum: ContactStationTypeEnum.values()){
            KeyValueBean keyValueBean = KeyValueBean.builder().code(osEnum.getCode()+"").name(osEnum.getValue()).build();
            initParamList.add(keyValueBean);
        }
        return initParamList;
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
