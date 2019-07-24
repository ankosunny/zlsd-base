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
package com.zhilingsd.base.common.constants;

/**
 * @author ZhangRong
 * @version Id: com.zhilingsd.base.common.constants.CacheCommonConstants, v 0.1 2019/5/18 21:28 ZhangRong Exp $$
 */
public class CacheCommonConstants {
    /**
     * 催收机构用户信息
     * 通过token获取用户信息
     * exp:"collection_agent_info:{token}"
     */
    public static String AGENT_INFO_CACHE = "collection_agent_info:";

    /**
     * 催收机构用户token信息
     * 通过agentId获取token值
     * exp:"collection_agent_token:{agentId}"
     */
    public static String AGENT_TOKEN_CACHE = "collection_agent_token:";

    /**
     * 应急限制信息
     * 通过groupId获取应急限制信息
     * exp:"emergency_group_info:{group}"
     */
    public static String EMERGENCY_GROUP_CACHE = "emergency_group_info:";



}
