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
 * @Description
 * @createTime 2019年06月03日 17:14*
 * log.info()
 */
public enum ContactStationEnum {

    //person_phone：个人客户手机号
    // person_home_tel：个人客户家庭电话
    // person_office_tel：个人客户办公电话
    // organ_finance_tel：机构客户财务电话
    // organ_service_tel：机构客户客服电话
    // organ_customer_tel：机构客户电话'

    //常量表字段
    PERSON_PHONE("person_phone", "个人客户手机号"),
    PERSON_HOME_TEL("person_home_tel","个人客户家庭电话"),
    PERSON_OFFICE_TEL("person_office_tel", "个人客户办公电话"),
    ORGAN_FINANCE_TEL("organ_finance_tel","机构客户财务电话"),
    ORGAN_SERVICE_TEL("organ_service_tel","机构客户客服电话"),
    ORGAN_CUSTOMER_TEL("organ_customer_tel","机构客户电话"),
    ;

    private String code;
    private String value;

    ContactStationEnum(String code, String value) {
        this.code = code;
        this.value = value;
    }

    public static String getValueByCode(String code) {
        for (ContactStationEnum osEnum : ContactStationEnum.values()) {
            if (code.equals(osEnum.getCode())) {
                return osEnum.getValue();
            }
        }
        return "";
    }

    public static List<KeyValueBean> initParam(){
        List<KeyValueBean> initParamList = Lists.newArrayList();
        for (ContactStationEnum osEnum: ContactStationEnum.values()){
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
