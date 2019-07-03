package com.zhilingsd.base.common.emuns.billmanage;

import com.google.common.collect.Lists;
import com.zhilingsd.base.common.bean.KeyValueBean;

import java.util.List;

public enum ExpColBatchSearchTypeEunm {
    BILL_CODE("bill_code","案件编码"),
    ACCOUNT("account","账号"),
    CARD_NO("card_no","卡号"),
    CERTIFICATE_NO("certificate_no","身份证"),
    PRODUCT_NUM("product_num","产品代码")
    ;
    private String code;
    private String value;

    ExpColBatchSearchTypeEunm(String code, String value) {
        this.code = code;
        this.value = value;
    }

    public static String getValueByCode(String code) {
        for (ExpColBatchSearchTypeEunm osEnum : ExpColBatchSearchTypeEunm.values()) {
            if (code.equals(osEnum.getCode())) {
                return osEnum.getValue();
            }
        }
        return "";
    }

    public static List<KeyValueBean> initParam(){
        List<KeyValueBean> initParamList = Lists.newArrayList();
        for (ExpColBatchSearchTypeEunm osEnum: ExpColBatchSearchTypeEunm.values()){
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
