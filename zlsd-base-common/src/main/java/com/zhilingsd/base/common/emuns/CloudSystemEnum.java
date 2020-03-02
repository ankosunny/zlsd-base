package com.zhilingsd.base.common.emuns;

/**
 * 登录系统
 * @author: yuboliang
 * @date: 2020/2/29
 **/
public enum CloudSystemEnum {
    /**
     * 管理端（公司内部人员使用）
     */
    MANAGE("manage", "管理端"),
    /**
     * 业务端（商户使用）
     */
    BUSINESS("business", "业务端"),
    ;

    private String code;
    private String description;

    CloudSystemEnum(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
