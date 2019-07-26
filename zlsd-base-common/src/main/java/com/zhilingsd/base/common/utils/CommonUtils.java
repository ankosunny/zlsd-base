package com.zhilingsd.base.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

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
    public static <T> List<List<T>> splitList(List<T> list, int len) {
        if (list == null || list.size() == 0 || len < 1) {
            return null;
        }

        List<List<T>> result = new ArrayList<List<T>>();

        int size = list.size();
        int count = (size + len - 1) / len;

        for (int i = 0; i < count; i++) {
            List<T> subList = list.subList(i * len, ((i + 1) * len > size ? size : len * (i + 1)));
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
     * 手机号脱敏(只显示前三位)
     * @param phone
     * @return
     */
    public static String desensitizationPhone(String phone){
        String phoneNumber = phone.replaceAll("(\\d{3})\\d{8}","$1********");
        log.info("脱敏前手机号phone={}，脱敏后的手机号phoneNumber={}",phone,phoneNumber);
        return phoneNumber;
    }

    /**
     * 手机号脱敏(脱敏中间4位)
     * @param phone
     * @return
     */
    public static String desensitizationCenterPhone(String phone){
        String phoneNumber = phone.replaceAll("(\\d{3})\\d{4}(\\d{4})","$1****$2");
        log.info("脱敏前手机号phone={}，脱敏后的手机号phoneNumber={}",phone,phoneNumber);
        return phoneNumber;
    }
    
    /**
     * 判断Object是否为空
     * @param obj
     * @return
     */
    public static boolean ObjectisEmpty(Object obj)
    {
        if (obj == null)
        {
            return true;
        }
        if ((obj instanceof List))
        {
            return ((List) obj).size() == 0;
        }
        if ((obj instanceof String))
        {
            return ((String) obj).trim().equals("");
        }
        return false;
    }

    /**
     * @description  拼接用户名与账号
     **/
    public static String getShowName(String name,String account){
        if (StringUtils.isNotBlank(account)){
            return name.concat("(").concat(account).concat(")");
        }else {
            return name;
        }
    }
}
