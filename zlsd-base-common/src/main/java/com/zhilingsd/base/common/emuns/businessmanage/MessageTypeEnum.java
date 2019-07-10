package com.zhilingsd.base.common.emuns.businessmanage;

import com.google.common.collect.Lists;
import com.zhilingsd.base.common.bean.KeyValueBean;
import com.zhilingsd.base.common.emuns.workmanage.StopStatusEnum;

import java.util.List;
/**
 * @description: 消息通知类型
 * @author: tangj
 * @create: 2019-07-09 17:47
 **/
public enum MessageTypeEnum {

    COMPLAINT("0", "投诉"),
    COMPLAINT_WARN("1", "投诉预警"),
    DIVISION("2", "案件分发"),
    BILL_CANCEL("3", "案件撤销"),
    POLICY_CONFIG("4", "策略配置"),
    BANK_WORK_ORDER("5", "银行工单"),
    NOTICE("6","公告"),
    ORTHER("7","其他（停催）"),

    ;
    private String code;
    private String value;

    MessageTypeEnum(String code, String value) {
        this.code = code;
        this.value = value;
    }

    public static String getValueByCode(String code) {
        for (MessageTypeEnum osEnum : MessageTypeEnum.values()) {
            if (osEnum.getCode().equals(code)) {
                return osEnum.value;
            }
        }
        return "";
    }

    public static MessageTypeEnum getByCode(String code) {
        for (MessageTypeEnum osEnum : MessageTypeEnum.values()) {
            if (osEnum.getCode().equals(code)) {
                return osEnum;
            }
        }
        return null;
    }

    public static List<KeyValueBean> initParam() {
        List<KeyValueBean> allotStatusList = Lists.newArrayList();
        for (MessageTypeEnum osEnum : MessageTypeEnum.values()) {
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
