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
 * @Description 案件操作枚举
 * @createTime 2019年05月07日 19:04*
 * log.info()
 */
public enum BillOperateTypeEnum {

    ALLOT_0(0, "分案"),
    ADJUST_1(1, "调案"),
    REVOKE_2(2, "撤销案件"),
    CANCEL_3(3,"取消分案"),
    BACK_4(4,"退案"),
    STOP_5(5,"停催案件"),
    ;
    private int code;
    private String value;

    BillOperateTypeEnum(int code, String value) {
        this.code = code;
        this.value = value;
    }

    public static String getValueByCode(int code) {
        for (BillOperateTypeEnum osEnum : BillOperateTypeEnum.values()) {
            if (osEnum.getCode() == code) {
                return osEnum.getValue();
            }
        }
        return "";
    }

    public static List<KeyValueBean> initParam() {
        List<KeyValueBean> allotStatusList = Lists.newArrayList();
        for (BillOperateTypeEnum osEnum : BillOperateTypeEnum.values()) {
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
