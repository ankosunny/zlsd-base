package com.zhilingsd.base.common.utils;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * @description: 常用方法分装
 * @author: tangj
 * @create: 2019-07-03 19:29
 **/
@Slf4j
public class CommonUtils {

    /**
     * 按指定大小，分隔集合，将集合按规定个数分为n个部分
     * @param list
     * @param len
     * @return
     */
    private static List<List<Long>> splitList(List<Long> list, int len) {
        if (list == null || list.size() == 0 || len < 1) {
            return null;
        }
        List<List<Long>> result = new ArrayList<List<Long>>();
        int size = list.size();
        int count = (size + len - 1) / len;
        for (int i = 0; i < count; i++) {
            List<Long> subList = list.subList(i * len, ((i + 1) * len > size ? size : len * (i + 1)));
            result.add(subList);
        }
        return result;
    }

    /**
     * 手机号脱敏
     * @param phone
     * @return
     */
    public static String desensitizationPhone(String phone){
        String phoneNumber = phone.replaceAll("(\\d{3})\\d{4}(\\d{4})","$1****$2");
        log.info("脱敏前手机号phone={}，脱敏后的手机号phoneNumber={}",phone,phoneNumber);
        return phoneNumber;
    }

}
