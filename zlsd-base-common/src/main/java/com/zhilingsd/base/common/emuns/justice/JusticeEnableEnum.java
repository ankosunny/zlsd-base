package com.zhilingsd.base.common.emuns.justice;

import com.google.common.collect.Lists;
import com.zhilingsd.base.common.bean.KeyValueBean;

import java.util.List;

/**
 * ^---^---^---^---^---^---^---^
 * --v---v---v---v---v---v---v--
 *
 * @author zou.cp
 * @version 1.0
 * @className com.zhilingsd.enums.java
 * @Description 手别
 * @createTime 2019年04月22日 12:13*
 * log.info()
 */
public enum JusticeEnableEnum {

    YOU_XIAO("youxiao", "有效"),
    WU_XIAO("wuxiao", "无效");
    private String code;
    private String value;

    JusticeEnableEnum(String code, String value) {
        this.code = code;
        this.value = value;
    }

    public String getCode() {
        return code;
    }

    public String getValue() {
        return value;
    }

    public static String getValueByCode(String code) {
        for (JusticeEnableEnum osEnum : JusticeEnableEnum.values()) {
            if (osEnum.getCode().equals(code)) {
                return osEnum.value;
            }
        }
        return "";
    }

    public static JusticeEnableEnum getByCode(int code) {
        for (JusticeEnableEnum osEnum : JusticeEnableEnum.values()) {
            if (osEnum.getCode().equals(code)) {
                return osEnum;
            }
        }
        return null;
    }

    public static List<KeyValueBean> initParam(){
        List<KeyValueBean> initParamList = Lists.newArrayList();
        for (JusticeEnableEnum osEnum: JusticeEnableEnum.values()){
            KeyValueBean keyValueBean = KeyValueBean.builder().code(osEnum.getCode()+"").name(osEnum.getValue()).build();
            initParamList.add(keyValueBean);
        }
        return initParamList;
    }

}
