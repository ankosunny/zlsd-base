package com.zhilingsd.base.common.emuns;

/**
 * 删除状态
 * @author linmenghuai
 * @date 2019年4月28日14:05:47
 * */
public enum EnumDeleteState {
    NORMAL("normal","正常状态"),
    DELETE("delete","删除状态")
    ;

    /** 状态码 **/
    private String code;
    /** 状态描述 **/
    private String description;


    private EnumDeleteState(String code, String description) {
        this.code = code;
        this.description = description;
    }


    public static EnumDeleteState find(int code) {
        for (EnumDeleteState frs : EnumDeleteState.values()) {
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
