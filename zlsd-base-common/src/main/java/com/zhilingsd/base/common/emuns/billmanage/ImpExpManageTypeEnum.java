package com.zhilingsd.base.common.emuns.billmanage;


import com.google.common.collect.Lists;
import com.zhilingsd.base.common.bean.KeyValueBean;

import java.util.List;

/**
 * @author: longhui
 * @description:导入导出管理类型
 * @create: 2019/6/17 14:45
 */
public enum ImpExpManageTypeEnum {

    IMPORT("import","导入"),
    EXPORT("export","导出"),
    ;

    private String code;
    private String value;

    ImpExpManageTypeEnum(String code, String value) {
        this.code = code;
        this.value = value;
    }

    public static String getValueByCode(String code) {
        for (ImpExpManageTypeEnum osEnum : ImpExpManageTypeEnum.values()) {
            if (code.equals(osEnum.getCode())) {
                return osEnum.getValue();
            }
        }
        return "";
    }

    public static List<KeyValueBean> initParam(){
        List<KeyValueBean> initParamList = Lists.newArrayList();
        for (ImpExpManageTypeEnum osEnum: ImpExpManageTypeEnum.values()){
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
