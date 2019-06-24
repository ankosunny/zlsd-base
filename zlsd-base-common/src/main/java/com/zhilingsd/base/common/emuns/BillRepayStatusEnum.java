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
public enum BillRepayStatusEnum {

    BILL_NOT_REPAY("notRepay", "未还款"),
    BILL_SOME_REPAY("someRepay", "部分还款"),
    BILL_ALL_REPAY("allRepay", "已还款");

    private String code;
    private String value;

    BillRepayStatusEnum(String code, String value) {
        this.code = code;
        this.value = value;
    }

    public static BillRepayStatusEnum getByCode(int code) {
        for (BillRepayStatusEnum osEnum : BillRepayStatusEnum.values()) {
            if (osEnum.getCode().equals(code)) {
                return osEnum;
            }
        }
        return null;
    }

    public static List<KeyValueBean> initParam() {
        List<KeyValueBean> allotStatusList = Lists.newArrayList();
        for (BillRepayStatusEnum osEnum : BillRepayStatusEnum.values()) {
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
