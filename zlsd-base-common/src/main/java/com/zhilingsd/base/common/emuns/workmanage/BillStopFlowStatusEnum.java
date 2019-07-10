package com.zhilingsd.base.common.emuns.workmanage;

import com.google.common.collect.Lists;
import com.zhilingsd.base.common.bean.KeyValueBean;

import java.util.List;

/**
 * @description: 案件停催流水状态
 * @author: tangj
 * @create: 2019-07-09 17:47
 **/
public enum BillStopFlowStatusEnum {

    BILL_STOP("stoping", "停催中"),
    BILL_CANCEL_STOP("notstop", "未停催"),
    ;
    private String code;
    private String value;

    BillStopFlowStatusEnum(String code, String value) {
        this.code = code;
        this.value = value;
    }

    public static String getValueByCode(String code) {
        for (BillStopFlowStatusEnum osEnum : BillStopFlowStatusEnum.values()) {
            if (osEnum.getCode().equals(code)) {
                return osEnum.value;
            }
        }
        return "";
    }

    public static BillStopFlowStatusEnum getByCode(String code) {
        for (BillStopFlowStatusEnum osEnum : BillStopFlowStatusEnum.values()) {
            if (osEnum.getCode().equals(code)) {
                return osEnum;
            }
        }
        return null;
    }

    public static List<KeyValueBean> initParam() {
        List<KeyValueBean> allotStatusList = Lists.newArrayList();
        for (BillStopFlowStatusEnum osEnum : BillStopFlowStatusEnum.values()) {
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
