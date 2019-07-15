package com.zhilingsd.base.common.emuns.cif;

/**
 * Created by chenzongbo on 2019/7/11.
 */
public enum PhoneTypeEnum {
    PERSON_PHONE("person_phone", "个人客户手机号"),//个人客户手机号
    PERSON_HOME_TEL("person_home_tel", "个人客户家庭电话"), //个人客户家庭电话
    PERSON_OFFICE_TEL("person_office_tel", "个人客户办公电话"), //个人客户办公电话
    ORGAN_FINANCE_TEL("organ_finance_tel", "机构客户财务电话"), //机构客户财务电话
    ORGAN_SERVICE_TEL("organ_service_tel", "机构客户客服电话"), //机构客户客服电话
    ORGAN_CUSTOMER_TEL("organ_customer_tel", "机构客户电话")  //机构客户电话
    ;


    private String code;


    private String msg;

    PhoneTypeEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    /**
     * 根据value获取对应的枚举
     */
    public static PhoneTypeEnum getEnumByCode(String value) {
        if (value == null) {
            return null;
        }
        for (PhoneTypeEnum tEnum : values()) {
            if (value.equals(tEnum.getCode())) {
                return tEnum;
            }
        }
        return null;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

}
