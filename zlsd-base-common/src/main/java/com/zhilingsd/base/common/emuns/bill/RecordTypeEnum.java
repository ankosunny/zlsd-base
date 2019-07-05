package com.zhilingsd.base.common.emuns.bill;

/**
 *  催记行为 类型 普通工单  原始催记
 * @program 智灵时代广州研发中心
 * @author ant man(tuzhen)
 * @create 2019/6/27 14:51
 **/
public enum RecordTypeEnum {

    ORDER_WORK("workOrder","普通工单"),
    ORIGIN_RECORD("originRecord","原始催记");

    /** 状态码 **/
    private String code;
    /** 状态描述 **/
    private String description;

    RecordTypeEnum(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public static RecordTypeEnum find(String code) {
        for (RecordTypeEnum recordType : RecordTypeEnum.values()) {
            if (recordType.getCode().equals(code)) {
                return recordType;
            }
        }
        return null;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}
