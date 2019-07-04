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
    public static List<List<Long>> splitList(List<Long> list, int len) {
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
     * @description  数据分割
     **/
    public static <T> List<List<T>> batchDataHandle(List<T> source, int n) {
        //数据是空的或者分批元素个数小于0
        if (null == source || source.size() == 0) {
            return null;
        }
        if (n <= 0) {
            n = 1000;
        }
        List<List<T>> result = new ArrayList<List<T>>();
        int remainder = source.size() % n;
        int size = (source.size() / n);
        for (int i = 0; i < size; i++) {
            List<T> subset = null;
            subset = source.subList(i * n, (i + 1) * n);
            result.add(subset);
        }
        if (remainder > 0) {
            List<T> subset = null;
            subset = source.subList(size * n, size * n + remainder);
            result.add(subset);
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
