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
    BILL(30, "案件"),
    AGENT(40, "催收机构"),
    CIF(50, "客户"),
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
