/**
 * Software License Declaration.
 * <p>
 * wandaph.com, Co,. Ltd.
 * Copyright © 2017 All Rights Reserved.
 * <p>
 * Copyright Notice
 * This documents is provided to wandaph contracting agent or authorized programmer only.
 * This source code is written and edited by wandaph Co,.Ltd Inc specially for financial
 * business contracting agent or authorized cooperative company, in order to help them to
 * install, programme or central control in certain project by themselves independently.
 * <p>
 * Disclaimer
 * If this source code is needed by the one neither contracting agent nor authorized programmer
 * during the use of the code, should contact to wandaph Co,. Ltd Inc, and get the confirmation
 * and agreement of three departments managers  - Research Department, Marketing Department and
 * Production Department.Otherwise wandaph will charge the fee according to the programme itself.
 * <p>
 * Any one,including contracting agent and authorized programmer,cannot share this code to
 * the third party without the agreement of wandaph. If Any problem cannot be solved in the
 * procedure of programming should be feedback to wandaph Co,. Ltd Inc in time, Thank you!
 */
package com.zhilingsd.collection.common.utils;


import com.zhilingsd.collection.common.emuns.SignTypeEnum;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author luziliang1
 * @version Id: CommonSignUtils.java, v 0.1 2018/1/26 9:29 luziliang1 Exp $$
 */
public class CommonSignUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(CommonSignUtils.class);

    public static String sign(Object req, String signKey) throws IllegalAccessException {
        return sign(req, signKey, SignTypeEnum.MD5);
    }

    public static String sign(Object req, String signKey,
                              SignTypeEnum signTypeEnum) throws IllegalAccessException {
        Map<String, String> map = objectToMap(req);
        return getSign(map, signKey, signTypeEnum);
    }

    private static String getSign(Map<String, String> map, String signKey,
                                  SignTypeEnum signTypeEnum) {
        StringBuilder stringA = new StringBuilder();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            if ("sign".equals(key) || "serialVersionUID".equals(key)
                || StringUtils.isBlank(value)) {
                continue;
            }
            stringA.append(key).append("=").append(value).append("&");
        }
        stringA.append("key=").append(signKey);

        String sign;
        switch (signTypeEnum) {
            case MD5:
            default:
                sign = MD5Utils.getMD5String(stringA.toString()).toUpperCase();
                break;
        }

        LOGGER.info("CommonSignUtils.getSign stringA={}, sign={}", stringA.toString(), sign);
        return sign;
    }

    private static Map<String, String> objectToMap(Object obj) throws IllegalAccessException {
        Map<String, String> map = new TreeMap<>();
        Class<?> clazz = obj.getClass();
        for (Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true);
            String fieldName = field.getName();
            Object value = field.get(obj);
            if (value == null || StringUtils.isBlank(String.valueOf(value))) {
                continue;
            }
            map.put(fieldName, String.valueOf(value));
        }
        return map;
    }

}