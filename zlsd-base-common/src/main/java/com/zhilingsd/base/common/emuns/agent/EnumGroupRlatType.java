package com.zhilingsd.base.common.emuns.agent;

/**
 * 组织主从
 * @author linmenghuai
 * @date 2019-5-14 19:26:44
 * */
public enum EnumGroupRlatType {
    MASTER("master","主组织"),
    SLAVE("slave","兼职组织"),
    ;

    /** 状态码 **/
    private String code;
    /** 状态描述 **/
    private String description;


    EnumGroupRlatType(String code, String description) {
        this.code = code;
        this.description = description;
    }


    public static EnumGroupRlatType find(int code) {
        for (EnumGroupRlatType frs : EnumGroupRlatType.values()) {
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
