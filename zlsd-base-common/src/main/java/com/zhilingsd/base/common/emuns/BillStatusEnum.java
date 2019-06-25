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

    BILL_NOT_ALLOT("notAllow", "未分配"),
    BILL_ALLOTING("allowing", "分配中"),
    BILL_ALLOTED("alloted", "已分配"),
    BILL_WAIT_FLOW("waitFlow", "待跟进"),
    BILL_FLOWING("flowing", "跟进中"),
    BILL_BACK("back", "已退案"),
    BILL_REVOKE("revoke", "已撤案"),
    BILL_STOP("stop", "停催"),
    ;
    private String code;
    private String value;

    BillStatusEnum(String code, String value) {
        this.code = code;
        this.value = value;
    }

    public static BillStatusEnum getValueByCode(String code) {
        for (BillStatusEnum osEnum : BillStatusEnum.values()) {
            if (osEnum.getCode().equals(code)) {
                return osEnum;
            }
        }
        return null;
    }

    public static List<KeyValueBean> initParam() {
        List<KeyValueBean> allotStatusList = Lists.newArrayList();
        for (BillStatusEnum osEnum : BillStatusEnum.values()) {
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
