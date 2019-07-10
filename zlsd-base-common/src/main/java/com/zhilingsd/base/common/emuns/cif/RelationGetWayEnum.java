package com.zhilingsd.base.common.emuns.cif;

public enum RelationGetWayEnum {
    BANK(0,"银行导入"),
    USERADD(1,"用户自增"),
    ;

    /** 状态码 **/
    private Integer code;
    /** 状态描述 **/
    private String description;


    RelationGetWayEnum(Integer code, String description) {
        this.code = code;
        this.description = description;
    }


    public static RelationGetWayEnum find(int code) {
        for (RelationGetWayEnum frs : RelationGetWayEnum.values()) {
            if (frs.getCode().equals(code)) {
                return frs;
            }
        }
        return null;
    }

    public Integer getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}
