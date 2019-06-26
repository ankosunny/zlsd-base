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
 * @Description 案件自身状态
 * @createTime 2019年04月19日 14:33*
 * log.info()
 */
public enum BillStatusEnum {

    BILL_NOT_ALLOT(0, "新案件"),
    BILL_NOT_FLOW(1, "待跟进"),
    BILL_FLOW(2, "跟进中"),
    BILL_BACK(3, "已退案"),
    BILL_REVOKE(4, "已撤案");

    private int code;
    private String value;

    BillStatusEnum(int code, String value) {
        this.code = code;
        this.value = value;
    }

    public static String getValueByCode(int code) {
        for (BillStatusEnum osEnum : BillStatusEnum.values()) {
            if (osEnum.getCode() == code) {
                return osEnum.getValue();
            }
        }
        return "";
    }

    public static List<KeyValueBean> initParam() {
        List<KeyValueBean> allotStatusList = Lists.newArrayList();
        for (BillStatusEnum osEnum : BillStatusEnum.values()) {
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
