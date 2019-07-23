package com.zhilingsd.base.common.emuns.workmanage;

import com.google.common.collect.Lists;
import com.zhilingsd.base.common.bean.KeyValueBean;

import java.util.List;

/**
 * ^---^---^---^---^---^---^---^
 * --v---v---v---v---v---v---v--
 *
 * @author zou.cp
 * @version 1.0
 * @Description 函件种类
 * @createTime 2019年05月17日 11:50*
 * log.info()
 */
public enum JusticeTypeEnum {

//    1：邮寄 2：外访
    JUSTICE_TYPE_0("youji", "邮寄"),
    JUSTICE_TYPE_1("guahao", "挂号"),
    JUSTICE_TYPE_2("waifang", "外访"),
    JUSTICE_TYPE_3("chuanzhen", "传真");

    private String code;
    private String value;

    JusticeTypeEnum(String code, String value) {
        this.code = code;
        this.value = value;
    }

    public static String getValueByCode(String code) {
        for (JusticeTypeEnum osEnum : JusticeTypeEnum.values()) {
            if (osEnum.code.equals( code)) {
                return osEnum.getValue();
            }
        }
        return "";
    }

    public static List<KeyValueBean> initParam(){
        List<KeyValueBean> initParamList = Lists.newArrayList();
        for (JusticeTypeEnum osEnum: JusticeTypeEnum.values()){
            KeyValueBean keyValueBean = KeyValueBean.builder().code(osEnum.getCode()+"").name(osEnum.getValue()).build();
            initParamList.add(keyValueBean);
        }
        return initParamList;
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

}
