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
 * @Description 币种
 * @createTime 2019年05月07日 19:04*
 * log.info()
 */
public enum CurrencyEnum {

    RMB("rmb", "人民币"),
    HKD("hkd", "港币"),
    USD("usd", "美元");

    private String code;
    private String value;

    CurrencyEnum(String code, String value) {
        this.code = code;
        this.value = value;
    }

    public static CurrencyEnum getByCode(String code) {
        for (CurrencyEnum osEnum : CurrencyEnum.values()) {
            if (osEnum.getCode().equals(code)) {
                return osEnum;
            }
        }
        return null;
    }

    public static List<KeyValueBean> initParam() {
        List<KeyValueBean> allotStatusList = Lists.newArrayList();
        for (CurrencyEnum osEnum : CurrencyEnum.values()) {
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
