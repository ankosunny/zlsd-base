package com.zhilingsd.base.common.emuns;

/**
 * collection
 *
 * @author liuzw
 * @date 2019-05-13
 **/
public enum LockStatusEnum {

    /**
     * 锁定
     */
    LOCKED("locked", "锁定"),

    /**
     * 正常
     */
    UNLOCK("normal", "正常"),
    ;

    /**
     * 状态码
     */
    private String code;

    /**
     * 描述
     */
    private String msg;

    LockStatusEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }


    public static LockStatusEnum getLockStatusEnum(String code) {
        for (LockStatusEnum statusEnum : LockStatusEnum.values()) {
            if (statusEnum.code.equals(code)) {
                return statusEnum;
            }
        }
        return UNLOCK;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }}
