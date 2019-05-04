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
 * @Description 手机是否停催
 * @createTime 2019年05月04日 21:33*
 * log.info()
 */
public enum PhoneStopStatusEnum {

    PHONE_NOT_STOP(0, "未停催"),
    PHONE_STOP(1, "已停催");

    private int code;
    private String value;

    PhoneStopStatusEnum(int code, String value) {
        this.code = code;
        this.value = value;
    }

    public static String getValueByCode(int code) {
        for (BillStopStatusEnum osEnum : BillStopStatusEnum.values()) {
            if (osEnum.getCode() == code) {
                return osEnum.getValue();
            }
        }
        return "";
    }

    public static List<KeyValueBean> initBillStopStatus() {
        List<KeyValueBean> allotStatusList = Lists.newArrayList();
        for (PhoneStopStatusEnum osEnum : PhoneStopStatusEnum.values()) {
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
