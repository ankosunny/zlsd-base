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
 * @Description 更新状态类型
 * @createTime 2019年05月04日 15:04*
 * log.info()
 */
public enum UpStatusOperateTypeEnum {

    //1:案件分配状态 2:案件状态 3:案件归类状态 4:还款 5:停催
    ALLOT_STATUS_1(1, "案件分配状态"),
    BILL_STATUS_2(2, "案件状态"),
    BILL_CLASSIFY_3(3, "案件归类状态"),
    REPAY_STATUS_4(4, "还款"),
    STOP_STATUS_5(5, "停催"),
    ;

    private int code;
    private String value;

    UpStatusOperateTypeEnum(int code, String value) {
        this.code = code;
        this.value = value;
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

    public static String getValueByCode(int code) {
        for (UpStatusOperateTypeEnum osEnum : UpStatusOperateTypeEnum.values()) {
            if (osEnum.getCode() == code) {
                return osEnum.getValue();
            }
        }
        return "";
    }

}
