package com.zhilingsd.base.common.emuns;

/**
 * 处理结果码
 *
 * @author zhangrong67
 * @version 1.0
 * @since 1.0 2017年12月12日 17:01
 */
public enum BaseResultCodeEnum {

    /**
     *   credit 10段开始
     *   user   20段开始
     *   account 40段开始
     *   payment 30段开始
     *
     */

    /**
     * system
     */
    SYSTEM_ERROR("SYSTEM_ERROR", "系统异常"),

    BUSINESS_ERROR("BUSINESS_ERROR", "业务异常"),

    EXECUTION_EXCEPTION("EXECUTION_EXCEPTION", "系统执行异常"),

    NULL_ARGUMENT("NULL_ARGUMENT", "参数为空"),

    ILLEGAL_ARGUMENT("ILLEGAL_ARGUMENT", "参数不正确"),

    DATA_NOT_EXIST("DATA_NOT_EXIST", "数据不存在"),

    DATA_EXIST("DATA_EXIST", "数据已存在"),

    RETURN_IS_NULL("RETURN_IS_NULL", "返回结果为空"),

    SYSTEM_BUSY("SYSTEM_BUSY", "系统繁忙"),

    NETWORK_ERROR("NETWORK_ERROR","网络异常"),

    DATA_FORMAT_ERROR("DATA_FORMAT_ERROR","数据格式错误"),

    MISSING_PARAMETER("MISSING_PARAMETER","缺少参数"),

    SIGN_ERROR("SIGN_ERROR","签名错误"),

    OTHER_ERROR("OTHER_ERROR","其他错误"),
    
    DAYS_NUMBER("DAYS_NUMBER","日期间隔大于31天"),

    NOT_SUPPORTED("NOT_SUPPORTED","不支持该请求方法"),

    DB_ERROR("DB_ERROR","数据库错误"),

    METHOD_ARGUMENT_NOT_VALID_ERROR("METHOD_ARGUMENT_NOT_VALID_ERROR","参数校验异常"),

    COMMON_ERR_9999("9999", "其他未知错误"),
    SUCCESS("200000", "操作成功"),
    QUERY_MAX_SIZE("200001", "查询数量超过限制"),
    UPDATE_MAX_SIZE("200002", "更新数量超过限制"),
    ADD_MAX_SIZE("200003", "新增数量超过限制"),
    ;

    /**
     * 错误码
     */
    private String code;

    /**
     * 错误信息
     */
    private String msg;

    BaseResultCodeEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    BaseResultCodeEnum(String msg) {
        this.code = this.name();
        this.msg = msg;
    }

    /**
     * 根据value获取对应的枚举
     */
    public static BaseResultCodeEnum getEnumByCode(String value) {
        if (value == null || "".equals(value)) {
            return null;
        }
        for (BaseResultCodeEnum tEnum : values()) {
            if (value.equalsIgnoreCase(tEnum.getCode())) {
                return tEnum;
            }
        }
        return null;
    }


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
