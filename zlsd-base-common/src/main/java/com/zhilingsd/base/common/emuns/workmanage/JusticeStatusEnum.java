package com.zhilingsd.base.common.emuns.workmanage;

import com.google.common.collect.Lists;
import com.zhilingsd.base.common.bean.KeyValueBean;

import java.util.List;

/**
 * 函件状态
 *
 * @Author: zhangbo
 * @DateTime: 2019/5/9 19:17
 */
public enum JusticeStatusEnum {
    // daichuli：待处理，dayin：已打印，wancheng：已完成，quxiao：已取消
    JUSTICE_DAICHULI("daichuli", "待处理"),
    JUSTICE_DAYIN("dayin", "已打印"),
    JUSTICE_WANCHENG("wancheng", "已完成"),
    JUSTICE_QUXIAO("quxiao","已取消")
    ;

    private String code;
    private String value;

    JusticeStatusEnum(String code, String value) {
        this.code = code;
        this.value = value;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public static String getValueByCode(String code) {
        for (JusticeStatusEnum osEnum : JusticeStatusEnum.values()) {
            if (osEnum.code.equals(code) ) {
                return osEnum.getValue();
            }
        }
        return "";
    }

    public static List<KeyValueBean> initParam() {
        List<KeyValueBean> initParamList = Lists.newArrayList();
        for (JusticeStatusEnum osEnum : JusticeStatusEnum.values()) {
            KeyValueBean keyValueBean = KeyValueBean.builder().code(osEnum.getCode() + "").name(osEnum.getValue()).build();
            initParamList.add(keyValueBean);
        }
        return initParamList;
    }
}