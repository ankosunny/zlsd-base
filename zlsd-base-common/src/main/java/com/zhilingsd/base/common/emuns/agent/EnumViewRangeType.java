package com.zhilingsd.base.common.emuns.agent;

/**
 * @program: 智灵时代广州研发中心
 * @description: 案件查看范围
 * @author: 火箭浣熊(zhangzhihang)
 * @create: 2019-07-23 21:15
 **/
public enum EnumViewRangeType {
    GROUP("group","小组"),
    PERSON("person","个人"),
    BRANCH_COMPANY("branch_company","分公司"),
    COMPANY("company","公司"),
    ;

    /** 状态码 **/
    private String code;
    /** 状态描述 **/
    private String description;


    EnumViewRangeType(String code, String description) {
        this.code = code;
        this.description = description;
    }


    public static EnumViewRangeType find(String code) {
        for (EnumViewRangeType frs : EnumViewRangeType.values()) {
            if (frs.getCode().equals(code)) {
                return frs;
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
