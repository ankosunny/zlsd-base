package com.zhilingsd.base.common.emuns.billmanage;

import com.google.common.collect.Lists;
import com.zhilingsd.base.common.bean.KeyValueBean;

import java.util.List;

/**
 * @author: longhui
 * @description:导入导出管理子类型
 * @create: 2019/6/17 14:45
 */
public enum ImpExpManageSubtypeEnum {

    IMP_BILL("imp_bill","导入新案"),
    EXP_BATCH_MARK("exp_batch_mark","导出批量催记"),
    EXP_SINGLE_MARK("exp_single_mark","导出单户催记"),
    EXP_RECORDINGS("exp_recordings","导出录音"),
    ;

    private String code;
    private String value;

    ImpExpManageSubtypeEnum(String code, String value) {
        this.code = code;
        this.value = value;
    }

    public static String getValueByCode(String code) {
        for (ImpExpManageSubtypeEnum osEnum : ImpExpManageSubtypeEnum.values()) {
            if (code.equals(osEnum.getCode())) {
                return osEnum.getValue();
            }
        }
        return "";
    }

    public static List<KeyValueBean> initParam(){
        List<KeyValueBean> initParamList = Lists.newArrayList();
        for (ImpExpManageSubtypeEnum osEnum: ImpExpManageSubtypeEnum.values()){
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
