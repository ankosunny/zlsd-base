/**
 * Software License Declaration.
 * <p>
 * zhilingsd.com, Co,. Ltd.
 * Copyright © 2019 All Rights Reserved.
 * <p>
 * Copyright Notice
 * This documents is provided to zhilingsd contracting agent or authorized programmer only.
 * This source code is written and edited by zhilingsd Co,.Ltd Inc specially for financial
 * business contracting agent or authorized cooperative company, in order to help them to
 * install, programme or central control in certain project by themselves independently.
 * <p>
 * Disclaimer
 * If this source code is needed by the one neither contracting agent nor authorized programmer
 * during the use of the code, should contact to zhilingsd Co,. Ltd Inc, and get the confirmation
 * and agreement of three departments managers  - Research Department, Marketing Department and
 * Production Department.Otherwise zhilingsd will charge the fee according to the programme itself.
 * <p>
 * Any one,including contracting agent and authorized programmer,cannot share this code to
 * the third party without the agreement of wandaph. If Any problem cannot be solved in the
 * procedure of programming should be feedback to zhilingsd Co,. Ltd Inc in time, Thank you!
 */
package com.zhilingsd.base.common.emuns;

/**
 * @author ZhangRong
 * @version Id: com.zhilingsd.base.common.emuns.ApiResultMainCodeEnum, v 0.1 2019/5/16 2:18 ZhangRong Exp $$
 */
public enum ApiResultCodeEnum {

    /**
     * sub code中，zop代表智灵时代开放平台，isp代表业务系统，isv代表使用方即api接入方
     */
    ISP_UNKNOW_ERROR(ApiResultMainCodeEnum.SERVICE_UNAVAILABLE.getCode(), ApiResultMainCodeEnum.SERVICE_UNAVAILABLE.getMsg(), "isp.unknow_error", "服务暂不可用（业务系统不可用）", "稍后重试"),
    ZOP_UNKNOW_ERROR(ApiResultMainCodeEnum.SERVICE_UNAVAILABLE.getCode(), ApiResultMainCodeEnum.SERVICE_UNAVAILABLE.getMsg(), "zop.unknow_error", "服务暂不可用（网关自身的未知错误）", "稍后重试"),

    INVALID_AUTH_TOKEN(ApiResultMainCodeEnum.INSUFFICIENT_AUTHORIZATION_AUTHORITY.getCode(), ApiResultMainCodeEnum.INSUFFICIENT_AUTHORIZATION_AUTHORITY.getMsg(), "zop.invalid_auth_token", "无效的访问令牌", "请刷新授权令牌或重新授权获取新的令牌"),
    AUTH_TOKEN_TIME_OUT(ApiResultMainCodeEnum.INSUFFICIENT_AUTHORIZATION_AUTHORITY.getCode(), ApiResultMainCodeEnum.INSUFFICIENT_AUTHORIZATION_AUTHORITY.getMsg(), "zop.invalid_auth_token", "访问令牌已过期", "请刷新授权令牌或重新授权获取新的令牌"),
    INVALID_APP_AUTH_TOKEN(ApiResultMainCodeEnum.INSUFFICIENT_AUTHORIZATION_AUTHORITY.getCode(), ApiResultMainCodeEnum.INSUFFICIENT_AUTHORIZATION_AUTHORITY.getMsg(), "zop.invalid_app_auth_token", "无效的应用授权令牌", "请刷新应用授权令牌或重新授权获取新的令牌"),
    INVALID_APP_AUTH_TOKEN_NO_API(ApiResultMainCodeEnum.INSUFFICIENT_AUTHORIZATION_AUTHORITY.getCode(), ApiResultMainCodeEnum.INSUFFICIENT_AUTHORIZATION_AUTHORITY.getMsg(), "zop.invalid_app_auth_token_no_api", "未授权当前接口", "请重新授权获取新的应用授权令牌"),
    APP_AUTH_TOKEN_TIME_OUT(ApiResultMainCodeEnum.INSUFFICIENT_AUTHORIZATION_AUTHORITY.getCode(), ApiResultMainCodeEnum.INSUFFICIENT_AUTHORIZATION_AUTHORITY.getMsg(), "zop.app_auth_token_time_out", "应用授权令牌已过期", "请刷新应用授权令牌或重新授权获取新的令牌"),

    MISSING_METHOD(ApiResultMainCodeEnum.MISSING_REQUIRED_ARGUMENTS.getCode(), ApiResultMainCodeEnum.MISSING_REQUIRED_ARGUMENTS.getMsg(), "isv.missing_method", "缺少方法名参数", "请求参数里面必须要有method参数"),
    MISSING_SIGNATURE(ApiResultMainCodeEnum.MISSING_REQUIRED_ARGUMENTS.getCode(), ApiResultMainCodeEnum.MISSING_REQUIRED_ARGUMENTS.getMsg(), "isv.missing_signature", "缺少签名参数", "检查请求参数，缺少sign参数"),
    MISSING_TIMESTAMP(ApiResultMainCodeEnum.MISSING_REQUIRED_ARGUMENTS.getCode(), ApiResultMainCodeEnum.MISSING_REQUIRED_ARGUMENTS.getMsg(), "isv.missing_timestamp", "缺少时间戳参数", "检查请求参数，缺少timestamp参数"),
    MISSING_VERSION(ApiResultMainCodeEnum.MISSING_REQUIRED_ARGUMENTS.getCode(), ApiResultMainCodeEnum.MISSING_REQUIRED_ARGUMENTS.getMsg(), "isv.missing_version", "缺少版本参数", "检查请求参数，缺少version参数"),

    INVALID_PARAMETER(ApiResultMainCodeEnum.ILLEGAL_PARAMETERS.getCode(), ApiResultMainCodeEnum.ILLEGAL_PARAMETERS.getMsg(), "isv.invalid_parameter", "参数无效", "检查参数，格式不对、非法值、越界等"),
    INVALID_METHOD(ApiResultMainCodeEnum.ILLEGAL_PARAMETERS.getCode(), ApiResultMainCodeEnum.ILLEGAL_PARAMETERS.getMsg(), "isv.invalid_method", "不存在的方法名", "检查入参method是否正确"),
    INVALID_FORMAT(ApiResultMainCodeEnum.ILLEGAL_PARAMETERS.getCode(), ApiResultMainCodeEnum.ILLEGAL_PARAMETERS.getMsg(), "isv.invalid_format", "无效的数据格式", "检查入参format，目前只支持json格式"),
    INVALID_SIGNATURE(ApiResultMainCodeEnum.ILLEGAL_PARAMETERS.getCode(), ApiResultMainCodeEnum.ILLEGAL_PARAMETERS.getMsg(), "isv.invalid_signature", "无效签名", "1.公私钥是否是一对 2.检查公钥上传是否与私钥匹配 3.存在中文需要做urlencode 4.签名算法是否无误"),
    INVALID_TOKEN(ApiResultMainCodeEnum.ILLEGAL_PARAMETERS.getCode(), ApiResultMainCodeEnum.ILLEGAL_PARAMETERS.getMsg(), "isv.invalid_token", "无效令牌", "auth_token 为无效的令牌，请确认令牌有效"),
    INVALID_ENCRYPT(ApiResultMainCodeEnum.ILLEGAL_PARAMETERS.getCode(), ApiResultMainCodeEnum.ILLEGAL_PARAMETERS.getMsg(), "isv.invalid_encrypt", "解密异常", "重试"),
    INVALID_APP_KEY(ApiResultMainCodeEnum.ILLEGAL_PARAMETERS.getCode(), ApiResultMainCodeEnum.ILLEGAL_PARAMETERS.getMsg(), "isv.invalid_app_key", "无效的appKey参数", "检查入参app_key，app_key不存在，或者未上线"),
    INVALID_TIMESTAMP(ApiResultMainCodeEnum.ILLEGAL_PARAMETERS.getCode(), ApiResultMainCodeEnum.ILLEGAL_PARAMETERS.getMsg(), "isv.invalid_timestamp", "非法的时间戳参数", "时间戳参数timestamp非法，请检查格式需要为\"yyyy-MM-dd HH:mm:ss\""),
    INVALID_CHARSET(ApiResultMainCodeEnum.ILLEGAL_PARAMETERS.getCode(), ApiResultMainCodeEnum.ILLEGAL_PARAMETERS.getMsg(), "isv.invalid_charset", "字符集错误", "请求参数charset错误，目前支持格式：UTF-8"),
    DECRYPTION_ERROR_NOT_VALID_ENCRYPT_KEY(ApiResultMainCodeEnum.ILLEGAL_PARAMETERS.getCode(), ApiResultMainCodeEnum.ILLEGAL_PARAMETERS.getMsg(), "isv.decryption_error_not_valid_encrypt_key", "解密出错, 未配置加密密钥或加密密钥格式错误", "没有配置加密密钥"),
    DECRYPTION_ERROR_UNKNOWN(ApiResultMainCodeEnum.ILLEGAL_PARAMETERS.getCode(), ApiResultMainCodeEnum.ILLEGAL_PARAMETERS.getMsg(), "isv.decryption_error_unknown", "解密出错，未知异常", "重试"),

    INSUFFICIENT_ISV_PERMISSIONS(ApiResultMainCodeEnum.INSUFFICIENT_AUTHORITY.getCode(), ApiResultMainCodeEnum.INSUFFICIENT_AUTHORITY.getMsg(), "isv.insufficient_isv_permissions", "isv权限不足", "请检查账户是否有当前接口权限"),
    LOGIN_TIMEOUT(ApiResultMainCodeEnum.INSUFFICIENT_AUTHORITY.getCode(), ApiResultMainCodeEnum.INSUFFICIENT_AUTHORITY.getMsg(), "login_timout", "登录超时，请重新登录", "重新登录"),
    CHECK_TOKEN_FAILED(ApiResultMainCodeEnum.INSUFFICIENT_AUTHORITY.getCode(), ApiResultMainCodeEnum.INSUFFICIENT_AUTHORITY.getMsg(), "check_token_failed", "验证token失败，请重新登录", "重新登录"),

    BUSINESS_PROCESSING_FAILURE(ApiResultMainCodeEnum.BUSINESS_PROCESSING_FAILURE.getCode(), ApiResultMainCodeEnum.BUSINESS_PROCESSING_FAILURE.getMsg(), "", "", ""),

    RESPONSE_NON_STANDARD(ApiResultMainCodeEnum.API_PROCESSING_FAILURE.getCode(), ApiResultMainCodeEnum.API_PROCESSING_FAILURE.getMsg(), "isp.response_non_standard", "业务可能成功，但业务系统出参格式不规范，API无法正确解析返回数据", "需要业务系统改造"),
    ;

    private int code;
    private String msg;
    private String subCode;
    private String subMsg;
    private String solution;

    ApiResultCodeEnum(int code, String msg, String subCode, String subMsg, String solution) {
        this.code = code;
        this.msg = msg;
        this.subCode = subCode;
        this.subMsg = subMsg;
        this.solution = solution;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getSubCode() {
        return subCode;
    }

    public void setSubCode(String subCode) {
        this.subCode = subCode;
    }

    public String getSubMsg() {
        return subMsg;
    }

    public void setSubMsg(String subMsg) {
        this.subMsg = subMsg;
    }

    public String getSolution() {
        return solution;
    }

    public void setSolution(String solution) {
        this.solution = solution;
    }
}
