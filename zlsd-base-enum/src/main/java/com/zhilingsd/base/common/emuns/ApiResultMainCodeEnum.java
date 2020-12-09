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
public enum ApiResultMainCodeEnum {
    SERVICE_UNAVAILABLE(20000, "服务不可用"),
    INSUFFICIENT_AUTHORIZATION_AUTHORITY(20001, "授权权限不足"),
    MISSING_REQUIRED_ARGUMENTS(40001, "缺少必填参数"),
    ILLEGAL_PARAMETERS(40002, "非法参数"),
    INSUFFICIENT_AUTHORITY(40003, "权限不足"),
    BUSINESS_PROCESSING_FAILURE(40004, "业务处理失败"),
    BUSINESS_EXCEPTION(40005, "业务异常"),
    API_PROCESSING_FAILURE(50000, "网关处理失败"),
    ;

    private int code;
    private String msg;

    ApiResultMainCodeEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
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
}
