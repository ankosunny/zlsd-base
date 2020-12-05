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
package com.zhilingsd.base.snowflake.common;

/**
 * @author ZhangRong
 * @version Id: com.zhilingsd.base.snowflake.client.common.SnowFlakeEntityEnum, v 0.1 2019/5/1 15:05 ZhangRong Exp $$
 */
public enum SnowFlakeEntityEnum {
    COMMON(0, "通用"),
//    BILL(30, "案件"),
//    AGENT(40, "催收机构"),
//    CIF(50, "客户"),
//    CONSOLE(60, "管理台"),
//    TEMPLATE(70, "模板"),
//    WORK_MANAGE(80, "作业管理"),
//    BUSINESS_MANAGE(90, "业务管理"),
//    CALL_CENTER(100, "呼叫中心"),
//    CALL_MANAGE(110, "呼叫管理"),
//    MERCHANT_CENTER(120, "商户中心"),
//    QI_BUSINESS(130, "质检业务"),
//    BASE_DATA(140, "基础管理"),
//    NLS(150, "语音交互能力层"),
//    TRAINING_BUSINESS(160, "培训业务"),
//    ICBC_BUSINESS(170, "工行业务"),
//    ROBOT_BILL_MANAGE(180, "机器人案件业务"),
//    ROBOT_BUSINESS_MANAGE(190, "机器人业务平台"),
//    ROBOT_BILL_CENTER(200, "机器人案件中心"),

    ASSET_BILL(10, "资产案件中心"),
    ASSET_AGENT(20, "资产用户"),
    ASSET_WORK_MANAGE(30, "资产作业管理"),
    ASSET_CALL_CENTER(40, "资产呼叫中心"),
    ASSET_BILL_MANAGE(50, "资产案件管理"),
    ASSET_DC(60, "资产数据中心"),
    ASSET_ENGINE(70, "资产引擎"),
    ;

    private int entityId;

    private String msg;

    SnowFlakeEntityEnum(int entityId, String msg) {
        this.entityId = entityId;
        this.msg = msg;
    }

    public int getEntityId() {
        return entityId;
    }

    public String getMsg() {
        return msg;
    }
}
