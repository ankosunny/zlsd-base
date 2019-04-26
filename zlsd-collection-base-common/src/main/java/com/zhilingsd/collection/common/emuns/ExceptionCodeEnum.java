package com.zhilingsd.collection.common.emuns;

/**
 * Created by chenzongbo on 2017/12/13.
 */
public enum  ExceptionCodeEnum {

    /**
     * 数据库异常
     */
    DAO_CODE_001("ORA001", "数据插入异常"),
    DAO_CODE_002("ORA002", "获取数据库序列失败"),
    DAO_CODE_003("ORA003", "数据更新异常"),
    DAO_CODE_404("ORA404", "操作目标不存在"),
    DAO_CODE_005("ORA005", "数据删除异常"),
    DAO_CODE_006("ORA006", "数据查询异常"),


    /**
     * 用户模块异常
     */
    CUST_CODE_404("001404", "客户信息未找到"),
    CUST_CODE_412("001412", "必要查询参数缺失"),
    CUST_CODE_415("001415", "客户类型参数不符合规范"),
    CUST_CODE_500("001500", "客户服务内部错误"),
    CUST_CODE_501("001501", "客户未实名认证"),
    CUST_CODE_502("001502", "请扫描收银台收款码"),
    USER_CODE_404("002404", "用户信息未找到"),
    USER_CODE_303("002303", "用户信息已存在"),
    USER_CODE_500("002500", "用户服务内部错误"),
    USER_CODE_501("002501", "用户验证码校验失败"),
    USER_CODE_502("002502", "用户绑定银行卡失败"),
    USER_CODE_503("002503", "用户银行卡四要素认证失败"),
    ACCT_CODE_404("003404", "账户信息未找到"),
    ACCT_CODE_405("003405", "用户客户账户关联信息未找到"),
    ACCT_CODE_406("003406", "账户状态异常"),
    ACCT_CODE_407("003407", "账户余额不足"),
    ACCT_CODE_500("003500", "账户服务内部错误"),

    /**
     * 资产模块异常
     */
    ASSET_CODE_100("010100", "重复授信错误"),
    ASSET_CODE_101("010101", "授信信息未找到"),
    ASSET_CODE_103("010103", "代扣成功更新借据状态失败"),
    
    AGREE_CODE_303("004303", "协议账单已存在"),
    AGREE_CODE_304("004304", "上一期协议账单不存在"),
    AGREE_CODE_415("004415", "请求参数不符合规范"),
    AGREE_CODE_404("004404", "协议信息未找到"),
    AGREE_CODE_407("004407", "协议状态不符合请求要求"),
    AGREE_CODE_500("004500", "协议服务内部错误"),
    PROD_CODE_404("005404", "产品信息未找到"),
    PROD_CODE_415("005415", "产品类型参数不符合规范"),
    PROD_CODE_416("005416", "产品销售期限已过"),
    PROD_CODE_500("005500", "产品服务内部错误"),
    APPLY_CODE_404("006404", "申请信息未找到"),
    APPLY_CODE_500("006500", "协议申请服务内部错误"),
    TSAC_CODE_400("007400", "交易参数错误"),
    TSAC_CODE_404("007404", "交易信息未找到"),
    TSAC_CODE_500("007500", "交易服务内部错误"),
    TSAC_CODE_501("007501", "支付银行卡认证ID重复"),
    TSAC_CODE_502("007502", "用户账户存在逾期"),
    TSAC_CODE_503("007503", "支付场景未定义"),
    PAY_CODE_404("008404", "支付信息未找到"),
    PAY_CODE_500("008500", "支付服务内部错误"),
    LIABILITY_CODE_404("009404", "资管信息未找到"),
    LIABILITY_CODE_500("009500", "资管服务内部错误"),

    AES_ENCRYPT_ERROR("900001", "AES加密异常"),
    AES_DECRYPT_ERROR("900002", "AES解密异常"),

    UNKNOWN_ERROR_999("999999", "未知错误"),

    /**
     * 对账单模块异常
     */
    TRADE_CODE_101("00101", "对账单必要查询参数缺失"),
    ;

    /**
     * 错误码
     */
    private String code;

    /**
     * 错误信息
     */
    private String msg;

    ExceptionCodeEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    ExceptionCodeEnum(String msg) {
        this.code = this.name();
        this.msg = msg;
    }

    /**
     * 根据value获取对应的枚举
     */
    public static ExceptionCodeEnum getEnumByCode(String value) {
        if (value == null || "".equals(value)) {
            return null;
        }
        for (ExceptionCodeEnum tEnum : values()) {
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
