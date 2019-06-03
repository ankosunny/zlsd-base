package com.zhilingsd.base.common.emuns.bill;

import com.google.common.collect.Lists;
import com.zhilingsd.base.common.bean.KeyValueBean;

import java.util.List;

/**
 * ^---^---^---^---^---^---^---^
 * --v---v---v---v---v---v---v--
 *
 * @author zou.cp
 * @version 1.0
 * @Description  案件与客户关系对应
 * @createTime 2019年06月03日 17:14*
 * log.info()
 */
public enum  BillCifTypeEnum {

    //tel：电话 email：邮箱 fax：传真 addr：地址 bank 银行 company 公司

    //常量表字段
    TEL("tel", "电话"),
    EMAIL("email", "邮箱"),
    FAX("fax","传真"),
    ADDR("addr","地址"),
    COMPANY("company","公司"),
    BANK("bank","银行"),
    ;

    private String code;
    private String value;

    BillCifTypeEnum(String code, String value) {
        this.code = code;
        this.value = value;
    }

    public static String getValueByCode(String code) {
        for (BillCifTypeEnum osEnum : BillCifTypeEnum.values()) {
            if (code.equals(osEnum.getCode())) {
                return osEnum.getValue();
            }
        }
        return "";
    }

    public static List<KeyValueBean> initParam(){
        List<KeyValueBean> initParamList = Lists.newArrayList();
        for (BillCifTypeEnum osEnum: BillCifTypeEnum.values()){
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
