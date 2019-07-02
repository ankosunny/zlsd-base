package com.zhilingsd.base.common.emuns.notifycenter;

/**
 * 短信状态枚举
 *
 * @author liuzw
 * @date 2019-07-02
 **/
public enum SmsStatusEnum {


    /**
     * 成功
     */
    SUCCESS("success","成功"),

    /**
     * 失败
     */
    FAIL("fail","失败"),

    /**
     * 未知
     */
    UNKNOWN("unkonw","未知"),

    /**
     * 发送中
     */
    SENDING("SENDING", "发送中"),

    /**
     * 等待中
     */
    wait("wait", "等待中"),

    /**
     * 已提交
     */
    submitted("submitted", "已提交"),

    /**
     * 获取状态报告中
     */
    reporting("reporting", "获取状态报告中"),

    /**
     * 完成
     */
    completed("completed", "完成"),

    ;

    public static String getValueByCode(String code) {
        for (SmsStatusEnum osEnum : SmsStatusEnum.values()) {
            if (osEnum.getCode().equals(code)) {
                return osEnum.value;
            }
        }
        return "";
    }


    private String code;
    
    private String value;

    SmsStatusEnum(String code, String value) {
        this.code = code;
        this.value = value;
    }

    public String getCode() {
        return code;
    }

    public String getValue() {
        return value;
    }

}
