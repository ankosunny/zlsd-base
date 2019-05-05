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
 * @Description 跟进时间类型
 * @createTime 2019年05月04日 21:16*
 * log.info()
 */
public enum BillFollowTypeEnum {

    BILL_FOLLOW_1(1, "3天未跟进"),
    BILL_FOLLOW_2(2, "5天未跟进"),
    BILL_FOLLOW_3(3, "7天未跟进"),
    BILL_FOLLOW_4(4, "15天未跟进"),
    BILL_FOLLOW_5(5, "20天未跟进"),
    BILL_FOLLOW_6(6, "30天未跟进"),
    ;


    private int code;
    private String value;

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

    BillFollowTypeEnum(int code, String value) {
        this.code = code;
        this.value = value;
    }


    public static String getDescByType(Integer code) {
        for (BillFollowTypeEnum b : BillFollowTypeEnum.values()) {
            if (b.code == code) {
                return b.value.trim();
            }
        }
        return null;
    }

    public static BillFollowTypeEnum getByType(Integer code) {
        for (BillFollowTypeEnum b : BillFollowTypeEnum.values()) {
            if (b.code == code) {
                return b;
            }
        }
        return null;
    }

    public static List<KeyValueBean> initParam() {
        List<KeyValueBean> initParamList = Lists.newArrayList();
        for (BillFollowTypeEnum osEnum : BillFollowTypeEnum.values()) {
            KeyValueBean keyValueBean = KeyValueBean.builder().code(osEnum.getCode() + "").name(osEnum.getValue()).build();
            initParamList.add(keyValueBean);
        }
        return initParamList;
    }
}
