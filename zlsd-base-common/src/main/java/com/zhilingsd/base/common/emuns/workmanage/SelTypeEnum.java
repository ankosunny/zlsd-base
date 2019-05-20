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
 * @Description
 * @createTime 2019年05月19日 09:59*
 * log.info()
 */
public enum SelTypeEnum {

    //选择类型(1:勾选,2:本页,3:筛选)
    SEL_TYPE_1(1, "勾选"),
    SEL_TYPE_2(2, "本页"),
    SEL_TYPE_3(3,"筛选"),
    ;

    private int code;
    private String value;

    SelTypeEnum(int code, String value) {
        this.code = code;
        this.value = value;
    }

    public static String getValueByCode(int code) {
        for (SelTypeEnum osEnum : SelTypeEnum.values()) {
            if (osEnum.getCode() == code) {
                return osEnum.getValue();
            }
        }
        return "";
    }

    public static List<KeyValueBean> initParam(){
        List<KeyValueBean> initParamList = Lists.newArrayList();
        for (SelTypeEnum osEnum: SelTypeEnum.values()){
            KeyValueBean keyValueBean = KeyValueBean.builder().code(osEnum.getCode()+"").name(osEnum.getValue()).build();
            initParamList.add(keyValueBean);
        }
        return initParamList;
    }

    public static Boolean isSelect(int code){
        if (code == SEL_TYPE_1.getCode() || code == SEL_TYPE_2.getCode()){
            return true;
        }
        return false;
    }

    public static Boolean isSearch(int code){
        if (code == SEL_TYPE_3.getCode()){
            return true;
        }
        return false;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
