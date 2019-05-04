package com.zhilingsd.base.common.utils.collection;


import com.google.common.collect.Lists;
import com.zhilingsd.base.common.emuns.CollectionTypeEnum;

import java.util.List;

/**
 * **className CollectionTypeUtils
 *
 * @author: zou.cp
 * ******date: 2018/12/27 11:52
 * description: This is a  催收流程工具类
 * ----------------------------------------
 * =======================================>
 */
public class CollectionTypeUtils {

    /**
     * @return int
     * @Description 二进制转十进制
     * @Param [binaryStr]
     **/
    public static int Binary2Ten(String binaryStr) {
        int res = Integer.parseInt(binaryStr, 2);
        return res;

    }

    /**
     * @return java.lang.String
     * @Description 十进制转二进制  二进制最高位值为1
     * @Param [value]
     **/
    public static String Ten2Binary(int value) {
        String res = Integer.toBinaryString(value);
        return res;
    }

    /**
     * @return boolean
     * @Description 判断是否包含某个催收流程
     * @Param [judgeValue, typeValue]
     **/
    public static boolean hasCollectionFlow(int judgeValue, int typeValue) {
        int res = typeValue & judgeValue;
        return res == typeValue;
    }

    /**
     * @return boolean
     * @Description 判断是否包含某个催收流程
     * @Param [judgeValue, typeValue]
     **/
    public static boolean hasCollectionFlow(String judgeStr, int typeValue) {
        int res = typeValue & Binary2Ten(judgeStr);
        return res == typeValue;
    }

    /**
     * @description 获取本次查询可以使用的催收流程列表
     **/
    public static List<String> availableColFlows(List<String> judgeStrs, List<Integer> typeValues) {
        Long start = System.currentTimeMillis();
        List<String> newStrs = Lists.newArrayList();
        judgeStrs.forEach(
                str -> {
                    if (hasCollectionFlowList(str, typeValues)) {
                        newStrs.add(str);
                    }
                }
        );
        System.out.println("花费时间：" + (System.currentTimeMillis() - start));
        return newStrs;
    }

    /**
     * @return boolean
     * @Description 判断是否包含多个催收流程
     * @Param [judgeValue, typeValues]
     **/
    public static boolean hasCollectionFlows(String judgeStr, String a, Integer... typeValues) {
        int res;
        Integer ten = Binary2Ten(judgeStr);
        for (Integer typeValue : typeValues) {
            res = typeValue & ten;
            if (res != typeValue) {
                return false;
            }
        }
        return true;
    }

    /**
     * @return boolean
     * @Description 判断是否包含多个催收流程
     * @Param [judgeValue, typeValues]
     **/
    public static boolean hasCollectionFlowList(String judgeStr, List<Integer> typeValues) {
        int res;
        Integer ten = Binary2Ten(judgeStr);
        for (Integer typeValue : typeValues) {
            res = typeValue & ten;
            if (res != typeValue) {
                return false;
            }
        }
        return true;
    }

    /**
     * @return java.lang.String
     * @Description 通过催收流程二进制 获取对应文字描述
     * @Param [collectionType]
     **/
    public static String getDescFromCode(String collectionType) {
        StringBuffer sb = new StringBuffer();
        if ("0".equals(collectionType)) {
            return "未分配";
        }
        for (CollectionTypeEnum c : CollectionTypeEnum.values()) {
            if (hasCollectionFlow(collectionType, c.getCode())) {
                sb.append(c.getValue());
                sb.append("+");
            }
        }
        String res = sb.substring(0, sb.length() - 1);
        return res;
    }

    /**
     * @description 添加一个催收流程
     **/
    public static String addCollectionFlow(String collectionTypes, String value) {
        String res = addCollectionFlow(collectionTypes, Binary2Ten(value));
        return res;
    }

    /**
     * @description 添加一个催收流程
     **/
    public static String addCollectionFlow(String collectionTypes, int value) {
        int res = Binary2Ten(collectionTypes) | value;
        return Ten2Binary(res);
    }

    /**
     * @description 移除一个催收流程
     **/
    public static String removeCollectionFlow(String collectionTypes, String value) {
        String res = removeCollectionFlow(collectionTypes, Binary2Ten(value));
        return res;
    }

    /**
     * @description 移除一个催收流程
     **/
    public static String removeCollectionFlow(String collectionTypes, int value) {
        int res = Binary2Ten(collectionTypes) & (~value);
        return Ten2Binary(res);
    }

    public static void main(String[] args) {
//        System.out.println(CollectionTypeUtils.Binary2Ten("11111111"));
//        System.out.println(CollectionTypeUtils.Ten2Binary(4));
//        System.out.println(hasCollectionFlow("0010", CollectionTypeEnum.COLLECTION_CALL.getType()));
//        System.out.println(getDescFromCode("1011"));
//        System.out.println(getDescFromCode("0"));
//        System.out.println(addCollectionFlow("1001", CollectionTypeEnum.COLLECTION_MANUAL.getType()));
//        System.out.println(removeCollectionFlow("1001", CollectionTypeEnum.COLLECTION_MANUAL.getType()));
//        String collectionTypes = "00001111";
//        while (collectionTypes.startsWith("0")) {
//            collectionTypes = collectionTypes.substring(1, collectionTypes.length());
//        }
//        System.out.println(collectionTypes);

//        System.out.println(hasCollectionFlows("1110",1,2,4));
//        System.out.println(hasCollectionFlows("1111",1,2,4));
//        System.out.println(hasCollectionFlows("1011",1,2,4));
        List<Integer> flows = Lists.newArrayList();
        flows.add(CollectionTypeEnum.COLLECTION_JUSTICE.getCode());
        System.out.println(hasCollectionFlowList("1011", flows));

        List<String> flowStrs = Lists.newArrayList();
        flowStrs.add("110");
        flowStrs.add("10110");
        flowStrs.add("11110");
        flowStrs.add("11111");
        flowStrs.add("1");
        flowStrs.add("11");
        flowStrs.add("111010");
        flowStrs.add("11010");
        flowStrs.add("11000");
        flowStrs.add("1100");
        flowStrs.add("1111");
        flowStrs.add("11101");
        flowStrs.add("10101");
        List<String> strs = availableColFlows(flowStrs, flows);
        strs.forEach(
                str -> {
                    System.out.println(str);
                }
        );
    }

}
