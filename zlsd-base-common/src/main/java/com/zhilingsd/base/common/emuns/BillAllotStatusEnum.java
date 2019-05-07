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
 * @Description 案件分配状态
 * @createTime 2019年04月19日 14:33*
 * log.info()
 */
public enum BillAllotStatusEnum {

    BILL_NOT_ALLOT(0, "未分配"),
    BILL_ALLOTING(1, "分配中"),
    BILL_ALLOT(2, "已分配");

    private int code;
    private String value;

    BillAllotStatusEnum(int code, String value) {
        this.code = code;
        this.value = value;
    }

    public static String getValueByCode(int code) {
        for (BillAllotStatusEnum osEnum : BillAllotStatusEnum.values()) {
            if (osEnum.getCode() == code) {
                return osEnum.getValue();
            }
        }
        return "";
    }

    public static List<KeyValueBean> initAllotStatus() {
        List<KeyValueBean> allotStatusList = Lists.newArrayList();
        for (BillAllotStatusEnum osEnum : BillAllotStatusEnum.values()) {
            KeyValueBean keyValueBean = KeyValueBean.builder().code(osEnum.getCode() + "").name(osEnum.getValue()).build();
            allotStatusList.add(keyValueBean);
        }
        return allotStatusList;
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

}