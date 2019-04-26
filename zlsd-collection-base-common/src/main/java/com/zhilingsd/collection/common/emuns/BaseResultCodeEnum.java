package com.zhilingsd.collection.common.emuns;

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
    SUCCESS("SUCCESS", "操作成功"),

    SYSTEM_ERROR("SYSTEM_ERROR", "系统异常"),

    BUSINESS_ERROR("BUSINESS_ERROR", "业务异常"),

    EXECUTION_EXCEPTION("EXECUTION_EXCEPTION", "系统执行异常"),

    NULL_ARGUMENT("NULL_ARGUMENT", "参数为空"),

    ILLEGAL_ARGUMENT("ILLEGAL_ARGUMENT", "参数不正确"),

    DATA_NOT_EXIST("DATA_NOT_EXIST", "数据不存在"),

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

    /**
     * notifycenter
     */
    NC_CAPTCHA_ERROR_TIMES("NC_CAPTCHA_ERROR_TIMES", "验证码错误次数限制"),

    NC_SMSCODE_TIMEOUT("NC_SMSCODE_TIMEOUT", "验证码超时"),

    NC_SMSCODE_MISMATCHED("NC_SMSCODE_MISMATCHED", "验证码不匹配"),

    NC_SCENECODE_NOT_EXIST("NC_SCENECODE_NOT_EXIST", "场景码不存在"),

    NC_SMS_SEND_FAILURE("NC_SMS_SEND_FAILURE", "短信发送失败"),

    /**
     * credit
     */
    CREDIT_LINE_SUCCESS("1000", "授信计算成功"),

    CREDIT_LINE_1006("1006", "您已授信，请重新进入万达花小程序"),

    CREDIT_LINE_1001("1001", "身份证 姓名校验不一致"),

    CREDIT_LINE_1002("1002", "手机号 身份证 姓名三者不一致"),

    CREDIT_LINE_1004("1004", "手机号在网时长小于6个月"),

    CREDIT_LINE_1005("1005", "手机号码状态不正常"),

    CREDIT_LINE_1007("1007", "年龄不在范围"),

    CREDIT_LINE_1008("1008", "万达征信校验异常"),

    CREDIT_LINE_1010("1010", "风控通过"),

    CREDIT_LINE_2000("2000", "人脸识别未通过"),

    CREDIT_LINE_2001("2001", "授信异常"),

    CREDIT_LINE_2002("2002", "人脸识别次数超过指定次数,请改天再试"),

    CREDIT_LINE_1011("1011", "不在交易时间"),

    CREDIT_LINE_1998("1998", "参数错误"),

    CREDIT_LINE_1999("1999", "其他错误"),

    CREDIT_LINE_Q05("Q05", "TD10"),
    /**
     * 活体检测未通过
     */
    CREDIT_LINE_Q02_RL05("Q02", "RL05"),
    /**
     * 人脸识别分数偏低,未通过
     */
    CREDIT_LINE_Q02_RL04("Q02", "RL04"),
    /**
     * 网络异常等错误
     */
    CREDIT_LINE_M02("M02", "M02"),



    CREDIT_LINE_C08("C08", "SQ01"),
    CREDIT_LINE_C09("C09", "SQ02"),
    CREDIT_LINE_C10("C10", "SQ03"),

    CREDIT_LINE_M05_SQ04("M05", "SQ04"),
    CREDIT_LINE_M06_SQ05("M06", "SQ05"),
    CREDIT_LINE_M07_SQ06("M07", "SQ06"),

    CREDIT_LINE_B03("B03", "ZW01"),
    CREDIT_LINE_M04("M04", "TX02"),
    CREDIT_LINE_M04_TX03("M04", "TX03"),
    CREDIT_LINE_M03_TX01("M03", "TX01"),

    CREDIT_LINE_F01_ZM27("F01", "ZM27"),
    CREDIT_LINE_F30_YC18("F30", "YC18"),
    /**
     * 芝麻欺诈分
     */
    CREDIT_LINE_ZM27("F01", "ZM27"),
    /**
     * 非VIP客户
     */
    CREDIT_LINE_SQ03("C10", "SQ03"),
    /**
     * 当月活跃天数小于8天
     */
    CREDIT_LINE_SQ02("C09", "SQ02"),
    /**
     *注册时间小于180天
     */
    CREDIT_LINE_SQ01("C08", "SQ01"),
    /**
     * 小于22周岁
     */
    CREDIT_LINE_MK01("C01", "MK01"),
    /**
     * 大于40周岁
     */
    CREDIT_LINE_MK02("C01", "MK02"),

    /**
     * 小于200周岁
     */
    CREDIT_LINE_MK03("C01", "MK03"),
    /**
     * 大于201周岁
     */
    CREDIT_LINE_MK04("C01", "MK04"),
    /**
     * 手机号码为170、171开头
     */
    CREDIT_LINE_YC10("F17", "YC10"),
    /**
     * 上次拒绝距今小于90天
     */
    CREDIT_LINE_LS02("A03", "LS02"),
    /**
     *身份证姓名不一致
     */
    CREDIT_LINE_YY02("Q02", "YY02"),
    /**
     *客户在网时常小于6小时
     */
    CREDIT_LINE_YY03("C07", "YY03"),
    /**
     *区域准入1西藏、青海、新疆、内蒙古
     */
    CREDIT_LINE_MK08("C05", "MK08"),
    /**
     *区域准入2福建地区（除福州、厦门）
     */
    CREDIT_LINE_MK09("C05", "MK09"),



    /**
     * user
     */
    USER_LINE_2001("2001", "登录信息已存在，登录凭证必须唯一"),

    USER_LINE_2002("2002", "用户不存在"),

    USER_LINE_2003("2003", "无效的用户类型"),

    USER_LINE_2004("2004", "查不到客户"),

    USER_LINE_2005("2005", "客户无绑卡信息"),

    USER_LINE_2006("2006", "登录超时"),
    USER_LINE_2007("2007", "用户实名认证失败"),
    USER_LINE_2008("2008", "没有找到授信失败记录"),

    USER_LINE_DB_2997("2997", "数据库错误"),
    USER_LINE_SC_2998("2998", "业务层错误"),
    USER_LINE_SC_2999("2999", "未知错误"),

    /**
     * payment
     */
    PAYMENT_REPEATE_SUCCESS("3000", "交易成功"),
    PAYMENT_REPEATE_ERR_3010("3010", "重复交易"),
    PAYMENT_REPEATE_ERR_3011("3011", "交易失败"),
    PAYMENT_REPEATE_ERR_3012("3012", "交易处理中"),
    PAYMENT_REPEATE_ERR_3013("3013", "报文签名失败"),
    PAYMENT_REPEATE_ERR_3014("3014", "响应报文异常"),
    PAYMENT_REPEATE_ERR_3016("3016", "交易不存在"),
    PAYMENT_REPEATE_ERR_3017("3017", "底层查询错误"),
    PAYMENT_REPEATE_ERR_3018("3018", "未提交"),
    PAYMENT_REPEATE_ERR_3019("3019", "响应报文签名不一致"),
    PAYMENT_REPEATE_ERR_3021("3021", "参数错误"),
    PAYMENT_REPEATE_ERR_3024("3024", "异常通知"),
    PAYMENT_REPEATE_ERR_3025("3025", "通知金额异常"),
    PAYMENT_REPEATE_ERR_3999("3999", "其他错误"),

    PAYMENT_CANCEL_SUCCESS("3030", "预付单取消成功"),
    PAYMENT_CANCEL_ERR_3031("3031", "预付单不允许取消"),


    /**
     * account
     */
    ACCOUNT_PASSWORD_ERROR("4001", "密码校验错误"),
    /**
     * asset
     * J03		SQ06 商户当日累计提现成功笔数X3，是否在设定范围内
     J04		SQ07 商户当日累计提现成功金额X4，是否在设定范围内
     J07		SQ10 商户累计提现成功笔数Y3，是否在设定范围内
     J08		SQ11 商户累计提现成功金额Y4，是否在设定范围内
     */
    ASSET_MERCHANT_CREDIT_ERROR("5001", "商户笔数及额度累加失败，请检查商户笔数及限额"),
    ASSET_UPDATE_WITHHOLD_ERROR("5002", "更新代扣记录数据失败"),
    ASSET_SAVE_TASK_ERROR("5003", "保存任务调度日志失败"),
    ASSET_NO_SETTLE_BILL("5004", "请于下月出账单后再还款"),
    ASSET_EXIST_DEALING_BILL("5005", "存在处理中的账单"),
    ASSET_BILL_ID_ERROR("5006", "账单id有误"),
    ASSET_DAY_COUNTLIMIT_OVER("J03","SQ06"),
    ASSET_DAY_LIMIT_OVER("J04","SQ07"),
    ASSET_MONTH_COUNTLIMIT_OVER("J07","SQ10"),
    ASSET_MONTH_LIMIT_OVER("J08","SQ11"),
    /**
     * common
     */
    COMMON_ERR_9999("9999", "其他未知错误"),

    /**
     * console
     */
    CONSOLE_AGENT_PAY_SUCCESS("6000", "支付成功"),
    CONSOLE_AGENT_PAY_FAIL("6001", "支付失败"),
    CONSOLE_AGENT_PAY_ERROR_6002("6002", "传入金额不一致"),
    CONSOLE_AGENT_PAY_ERROR_6003("6003", "下单失败"),
    CONSOLE_AGENT_PAY_ERROR_6004("6004", "资产状态更新失败"),
    CONSOLE_AGENT_PAY_ERROR_6005("6005", "支付处理中"),
    CONSOLE_AGENT_PAY_ERROR_6006("6006", "支付金额为0"),
    CONSOLE_AGENT_PAY_ERROR_6999("6999", "其他错误"),
    CONSOLE_DOWN_BILL_ERROR_2000("2000","响应报文错误"),
    CONSOLE_REPEATE_ERR_2001("2001", "报文签名失败"),
    CONSOLE_DOWN_BILL_FAIL("2002", "下载对账文件失败"),


    /**
     * file
     */
    FILE_LINE_7001("7001", "文件不存在"),
    FILE_LINE_7002("7002", "文件上传异常"),

    FILE_LINE_DB_7997("7997", "数据库错误"),
    FILE_LINE_SC_7998("7998", "业务层错误"),
    FILE_LINE_SC_7999("7999", "未知错误"),


    /**
     * order
     */
    ORDER_PAY_FAIL("8001", "支付单支付失败"),
    ORDER_STATUS_ERROR("8002", "订单状态错误"),
    ORDER_PAYER_ERROR("8003", "订单交易方错误"),
    TRADE_ORDER_PAYING("8004", "业务单支付中"),
    TRADE_ORDER_EXPIRE("8005", "业务单已过时"),
    TRADE_ORDER_PAID("8006", "业务单已支付"),
    ORDER_TYPE_ERROR("8007", "订单类型错误"),

    STLMNT_BATCH_NOT_FOUND("8101", "对账批次不存在"),
    STLMNT_BATCH_CLEAN_UNCOMPLETED("8102", "对账批次未完成清洗"),
    STLMNT_BATCH_FILE_UNCOMPLETED("8103", "对账批次对账文件未完成导入"),
    STLMNT_FILE_NOT_DOWN("8104", "对账文件未下载"),
    STLMNT_REQ_MSG_ERROR("8105", "请求报文错误"),
    STLMNT_FILE_DOWN_ERROR("8106", "对账文件下载错误"),
    STLMNT_FILE_PARSE_ERROR("8107", "对账文件解析错误"),
    STLMNT_FILE_VERIFY_ERROR("8108", "对账文件校验错误"),
    STLMNT_FILE_DETAIL_ERROR("8109", "对账文件明细错误"),


    RPY_INSTALLMENT_8201("8201", "库分期交易记录不存在"),

    RPY_INSTALLMENT_8297("8297", "数据库错误"),
    RPY_INSTALLMENT_8298("8298", "业务层错误"),
    RPY_INSTALLMENT_8299("8299", "未知错误"),

    /**
     * reconciliation
     */
    STLMNT_INSTALLMENT_9001("9001", "寺库对账记录为空"),
    STLMNT_INSTALLMENT_9002("9002", "下载解析对账文件异常"),
    STLMNT_INSTALLMENT_9003("9003", "对账文件已存在"),
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
