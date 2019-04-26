/**
 * Software License Declaration.
 * <p>
 * wandaph.com, Co,. Ltd.
 * Copyright ? 2017 All Rights Reserved.
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
package com.zhilingsd.collection.common.util;

import com.alibaba.fastjson.JSONObject;
import com.zhilingsd.collection.common.exception.BaseException;


/**
 *  寺库加密解密工具
 * @author zhoucong
 * @version Id: EncryptAndDecryptUtils.java, v 0.1 2018/3/20 16:29 zhoucong Exp $$
 */
public class EncryptAndDecryptUtils {

    public static String responseDecrypt(String resultJson, String md5Key, String aesKey) {
        JSONObject jsonObject = JSONObject.parseObject(resultJson);
        try {
            String data = (String) jsonObject.get("data");
            System.out.println("--返回值解密前->>" + resultJson);
            String aseStr = UtilTools.aesDecrypt(aesKey, data);
            System.out.println("--返回值解密后->>" + aseStr);
            return aseStr;
        } catch (Exception e) {
            e.printStackTrace();
            throw new BaseException("解密失败",e.getMessage());
        }
    }

    public static String requestEncrypt(String midPlatform, String json, String md5Key, String aesKey, String version) {
        try {
            System.out.println("--加密前->>" + json);
            String md5Str = UtilTools.md5(json + md5Key);
            String aesStr = UtilTools.aesEncrypt(aesKey, json);
            String reqJson = "{\"sign\":\"" + md5Str + "\",\"data\":\"" + aesStr + "\",\"midPlatform\":\"" + midPlatform + "\",\"version\":\"" + version + "\"}";
            System.out.println("--加密后->>" + reqJson);
            return reqJson;
        } catch (Exception e) {
            e.printStackTrace();
            throw new BaseException("加密失败",e.getMessage());
        }
    }
}