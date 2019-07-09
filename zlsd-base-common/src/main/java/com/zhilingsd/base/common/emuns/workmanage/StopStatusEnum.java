package com.zhilingsd.base.common.emuns.workmanage;

import com.google.common.collect.Lists;
import com.zhilingsd.base.common.bean.KeyValueBean;
import com.zhilingsd.base.common.emuns.BillStatusEnum;

import java.util.List;

/**
 * @description: 案件停催和手机号停催标识
 * @author: tangj
 * @create: 2019-07-09 17:47
 **/
public enum StopStatusEnum {

    IS_BILL_STOP("1", "是"),
    ISNOT_BILL_STOP("0", "否"),
    IS_PHONE_STOP("1", "是"),
    ISNOT_PHONE_STOP("0", "否"),
            ;
    private String code;
    private String value;

    StopStatusEnum(String code, String value) {
        this.code = code;
        this.value = value;
    }

    public static String getValueByCode(String code) {
        for (StopStatusEnum osEnum : StopStatusEnum.values()) {
            if (osEnum.getCode().equals(code)) {
                return osEnum.value;
            }
        }
        return "";
    }

    public static StopStatusEnum getByCode(String code) {
        for (StopStatusEnum osEnum : StopStatusEnum.values()) {
            if (osEnum.getCode().equals(code)) {
                return osEnum;
            }
        }
        return null;
    }

    public static List<KeyValueBean> initParam() {
        List<KeyValueBean> allotStatusList = Lists.newArrayList();
        for (StopStatusEnum osEnum : StopStatusEnum.values()) {
            KeyValueBean keyValueBean = KeyValueBean.builder().code(osEnum.getCode() + "").name(osEnum.getValue()).build();
            allotStatusList.add(keyValueBean);
        }
        return allotStatusList;
    }

    public String getCode() {
        return code;
    }

    public String getValue() {
        return value;
    }
}
