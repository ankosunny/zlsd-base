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
package com.zhilingsd.base.snowflake.client;

import com.zhilingsd.base.snowflake.common.SnowFlakeEntityEnum;
import com.zhilingsd.base.snowflake.common.SnowFlakeException;

import java.util.List;

/**
 * @author ZhangRong
 * @version Id: com.zhilingsd.base.snowflake.client.Serial, v 0.1 2019/5/1 15:35 ZhangRong Exp $$
 */
public interface Serial {
    /**
     * 功能描述：获得通用Long类型雪花ID
     * @param
     * @return java.lang.Long
     * @auther 吞星（yangguojun）
     * @date 2021/7/22-10:14
     */
    Long getLongSerialNum() throws SnowFlakeException;

    /**
     * 功能描述：根据指定枚举获得Long类型雪花ID
     * @param
     * @return java.lang.Long
     * @auther 吞星（yangguojun）
     * @date 2021/7/22-10:14
     */
    Long getLongSerialNum(SnowFlakeEntityEnum entityEnum) throws SnowFlakeException;


    /**
     * 功能描述：获得通用String类型雪花ID
     * @param
     * @return java.lang.String
     * @auther 吞星（yangguojun）
     * @date 2021/7/22-10:14
     */
    String getStringSerialNum() throws SnowFlakeException;

    /**
     * 功能描述：根据指定枚举获得String类型雪花ID
     * @param
     * @return java.lang.String
     * @auther 吞星（yangguojun）
     * @date 2021/7/22-10:14
     */
    String getStringSerialNum(SnowFlakeEntityEnum entityEnum) throws SnowFlakeException;


    /**
     * 功能描述：获得一批通用Long类型雪花ID
     * @param number 一批的ID个数
     * @return java.lang.Long
     * @auther 吞星（yangguojun）
     * @date 2021/7/22-10:14
     */
    List<Long> getBatchLongSerialNum(Integer number) throws SnowFlakeException;

    /**
     * 功能描述：根据一批指定枚举获得Long类型雪花ID
     * @param entityEnum 枚举
     * @param number 一批的ID个数
     * @return java.lang.Long
     * @auther 吞星（yangguojun）
     * @date 2021/7/22-10:14
     */
    List<Long> getBatchLongSerialNum(SnowFlakeEntityEnum entityEnum,Integer number) throws SnowFlakeException;


    /**
     * 功能描述：获得一批通用String类型雪花ID
     * @param number 一批的ID个数
     * @return java.lang.String
     * @auther 吞星（yangguojun）
     * @date 2021/7/22-10:14
     */
    List<String> getBatchStringSerialNum(Integer number) throws SnowFlakeException;

    /**
     * 功能描述：根据一批指定枚举获得String类型雪花ID
     * @param entityEnum 枚举
     * @param number 一批的ID个数
     * @return java.lang.String
     * @auther 吞星（yangguojun）
     * @date 2021/7/22-10:14
     */
    List<String> getBatchStringSerialNum(SnowFlakeEntityEnum entityEnum,Integer number) throws SnowFlakeException;

}
