package com.zhilingsd.base.common.emuns.notifycenter;

/**
 * 消息类型
 *
 * @author liuzw
 * @date 2019-07-02
 **/
public enum NotifyTypeEnum {

    /**
     * 短信
     */
    SMS("sms","短信"),

    /**
     * 邮件
     */
    MAIL("mail","邮件"),

    ;

    public static String getValueByCode(String code) {
        for (NotifyTypeEnum osEnum : NotifyTypeEnum.values()) {
            if (osEnum.getCode().equals(code)) {
                return osEnum.value;
            }
        }
        return "";
    }


    private String code;

    private String value;

    NotifyTypeEnum(String code, String value) {
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
