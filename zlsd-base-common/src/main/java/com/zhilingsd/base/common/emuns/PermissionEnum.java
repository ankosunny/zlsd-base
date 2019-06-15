package com.zhilingsd.base.common.emuns;

/**
 * 页面资源枚举
 *
 * @author liuzw
 * @date 2019-06-13
 **/
public enum PermissionEnum {


    /**
     * 页面资源
     */
    RESOURCE("resource"),

    /**
     * 动作
     */
    ACTION("action"),
    ;

    PermissionEnum(String type) {
        this.type = type;
    }


    private String type;

    public String getType() {
        return type;
    }

}
