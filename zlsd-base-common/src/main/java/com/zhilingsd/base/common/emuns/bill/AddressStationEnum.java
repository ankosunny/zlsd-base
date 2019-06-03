package com.zhilingsd.base.common.emuns.bill;

import com.google.common.collect.Lists;
import com.zhilingsd.base.common.bean.KeyValueBean;

import java.util.List;

/**
 * ^---^---^---^---^---^---^---^
 * --v---v---v---v---v---v---v--
 *
 * @author zou.cp
 * @version 1.0
 * @Description
 * @createTime 2019年06月03日 17:14*
 * log.info()
 */
public enum AddressStationEnum {

    //personHomeAddr：个人家庭地址
    //personCompAddr：个人单位地址
    // personBirthAddr:个人户籍地
    // personBillAddr：个人账务地址
    // personFrequentAddr：个人常用地址
    // others:其他

    //常量表字段
    PERSONHOMEADDR("personHomeAddr", "个人家庭地址"),
    PERSONCOMPADDR("personCompAddr","个人单位地址"),
    PERSONBIRTHADDR("personBirthAddr", "个人户籍地"),
    PERSONBILLADDR("personBillAddr","个人账务地址"),
    PERSONFREQUENTADDR("personFrequentAddr","个人常用地址"),
    OTHERS("others","其他"),
    ;

    private String code;
    private String value;

    AddressStationEnum(String code, String value) {
        this.code = code;
        this.value = value;
    }

    public static String getValueByCode(String code) {
        for (AddressStationEnum osEnum : AddressStationEnum.values()) {
            if (code.equals(osEnum.getCode())) {
                return osEnum.getValue();
            }
        }
        return "";
    }

    public static List<KeyValueBean> initParam(){
        List<KeyValueBean> initParamList = Lists.newArrayList();
        for (AddressStationEnum osEnum: AddressStationEnum.values()){
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
