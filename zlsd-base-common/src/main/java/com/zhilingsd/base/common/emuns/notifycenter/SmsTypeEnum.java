//package com.zhilingsd.base.common.emuns.notifycenter;
//
///**
// * 短信类型枚举
// *
// * @author liuzw
// * @date 2019-07-02
// **/
//public enum SmsTypeEnum {
//
//    /**
//     * 催收
//     */
//    COLLECTION("collection","催收"),
//
//    /**
//     * 提醒
//     */
//    NOTICE("notice","提醒"),
//
//    /**
//     * 未知
//     */
//    NOTIFY("notify","通知"),
//
//    /**
//     * 营销
//     */
//    PROMO("promo", "营销")
//    ;
//
//    public static String getValueByCode(String code) {
//        for (SmsTypeEnum osEnum : SmsTypeEnum.values()) {
//            if (osEnum.getCode().equals(code)) {
//                return osEnum.value;
//            }
//        }
//        return "";
//    }
//
//
//    private String code;
//
//    private String value;
//
//    SmsTypeEnum(String code, String value) {
//        this.code = code;
//        this.value = value;
//    }
//
//    public String getCode() {
//        return code;
//    }
//
//    public String getValue() {
//        return value;
//    }
//
//}
