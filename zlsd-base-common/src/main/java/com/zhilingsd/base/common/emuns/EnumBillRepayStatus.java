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
 * @Description 案件回款状态
 * @createTime 2019年04月19日 14:33*
 * log.info()
 */
public enum EnumBillRepayStatus {

    BILL_NOT_REPAY("not_repay", "未还款"),
    BILL_SOME_REPAY("some_repay", "部分还款"),
    BILL_ALL_REPAY("all_repay", "已还款");

    private String code;
    private String value;

    EnumBillRepayStatus(String code, String value) {
        this.code = code;
        this.value = value;
    }

    public static String getByCode(String code) {
        for (EnumBillRepayStatus osEnum : EnumBillRepayStatus.values()) {
            if (osEnum.getCode().equals(code)) {
                return osEnum.value;
            }
        }
        return null;
    }

    public static List<KeyValueBean> initParam() {
        List<KeyValueBean> allotStatusList = Lists.newArrayList();
        for (EnumBillRepayStatus osEnum : EnumBillRepayStatus.values()) {
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
