//package com.zhilingsd.base.common.emuns.justice;
//
//import com.google.common.collect.Lists;
//import com.zhilingsd.base.common.bean.KeyValueBean;
//
//import java.util.List;
//
///**
// * ^---^---^---^---^---^---^---^
// * --v---v---v---v---v---v---v--
// *函件类型枚举
// * @author liuhang
// * @version 1.0
// * @className com.zhilingsd.enums.java
// * @Description 外访类型
// * @createTime 2019年04月22日 12:13*
// * log.info()
// */
//public enum JusticeTypeEnum {
//
//    WAI_FANG("waifang", "外访"),
//    YOU_JI("youji", "邮寄");
//    private String code;
//    private String value;
//
//    JusticeTypeEnum(String code, String value) {
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
//    public static String getValueByCode(String code) {
//        for (JusticeTypeEnum osEnum : JusticeTypeEnum.values()) {
//            if (osEnum.getCode().equals(code)) {
//                return osEnum.value;
//            }
//        }
//        return "";
//    }
//
//    public static JusticeTypeEnum getByCode(int code) {
//        for (JusticeTypeEnum osEnum : JusticeTypeEnum.values()) {
//            if (osEnum.getCode().equals(code)) {
//                return osEnum;
//            }
//        }
//        return null;
//    }
//
//    public static List<KeyValueBean> initParam(){
//        List<KeyValueBean> initParamList = Lists.newArrayList();
//        for (JusticeTypeEnum osEnum: JusticeTypeEnum.values()){
//            KeyValueBean keyValueBean = KeyValueBean.builder().code(osEnum.getCode()+"").name(osEnum.getValue()).build();
//            initParamList.add(keyValueBean);
//        }
//        return initParamList;
//    }
//
//}
