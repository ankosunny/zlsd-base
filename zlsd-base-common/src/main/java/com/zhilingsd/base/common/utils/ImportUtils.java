package com.zhilingsd.base.common.utils;

import java.io.UnsupportedEncodingException;

/**
 * @program zhilingsd-base
 * @description: excel的工具类
 * @author: LiuHang
 * @create: 2019/04/16 11:54
 * @Copyright: 2018 www.mujinkeji.com Inc. All rights reserved.
 */


public class ImportUtils {
    private final static int[] li_SecPosValue = {1601, 1637, 1833, 2078, 2274,
            2302, 2433, 2594, 2787, 3106, 3212, 3472, 3635, 3722, 3730, 3858,
            4027, 4086, 4390, 4558, 4684, 4925, 5249, 5590};
    private final static String[] lc_FirstLetter = {"a", "b", "c", "d", "e",
            "f", "g", "h", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s",
            "t", "w", "x", "y", "z"};

    /**
     * 说明: 取得给定汉字串的首字母串,即声母串
     *
     * @param str 汉字串
     * @return java.lang.String 声母串
     * @Author zengkai
     * @Date 2019/1/1 12:13
     */
    public static String getAllFirstLetter(String str) {
        if (str == null || str.trim().length() == 0) {
            return "";
        }
        String _str = "";
        for (int i = 0; i < str.length(); i++) {
            _str = _str + getFirstLetter(str.substring(i, i + 1));
        }

        return _str;
    }

    /**
     * 说明: 取得给定汉字的首字母,即声母
     *
     * @param chinese 汉字
     * @return java.lang.String 声母
     * @Author zengkai
     * @Date 2019/1/1 12:14
     */
    public static String getFirstLetter(String chinese) {
        if (chinese == null || chinese.trim().length() == 0) {
            return "";
        }
        chinese = conversionStr(chinese, "GB2312", "ISO8859-1");

        if (chinese.length() > 1) // 判断是不是汉字
        {
            int li_SectorCode = (int) chinese.charAt(0); // 汉字区码
            int li_PositionCode = (int) chinese.charAt(1); // 汉字位码
            li_SectorCode = li_SectorCode - 160;
            li_PositionCode = li_PositionCode - 160;
            int li_SecPosCode = li_SectorCode * 100 + li_PositionCode; // 汉字区位码
            if (li_SecPosCode > 1600 && li_SecPosCode < 5590) {
                for (int i = 0; i < 23; i++) {
                    if (li_SecPosCode >= li_SecPosValue[i]
                            && li_SecPosCode < li_SecPosValue[i + 1]) {
                        chinese = lc_FirstLetter[i];
                        break;
                    }
                }
            } else // 非汉字字符,如图形符号或ASCII码
            {
                chinese = conversionStr(chinese, "ISO8859-1", "GB2312");
                chinese = chinese.substring(0, 1);
            }
        }

        return chinese;
    }

    /**
     * 说明: 字符串编码转换
     *
     * @param str           要转换编码的字符串
     * @param charsetName   原来的编码
     * @param toCharsetName 转换后的编码
     * @return java.lang.String 经过编码转换后的字符串
     * @Author zengkai
     * @Date 2019/1/1 12:15
     */
    private static String conversionStr(String str, String charsetName, String toCharsetName) {
        try {
            str = new String(str.getBytes(charsetName), toCharsetName);
        } catch (UnsupportedEncodingException ex) {
            System.out.println("字符串编码转换异常：" + ex.getMessage());
        }
        return str;
    }

    public static String getFortBitNumStr(Integer i) {
        String str = i.toString();
        String head = "";
        if (str.length() < 6) {
            char[] chars = str.toCharArray();
            for (int j = 0; j < 6 - chars.length; j++) {
                head += "0";
            }
        }
        return head + str;
    }
    public static String encrypIdNum(String idNum,int start,int end){
        if(idNum==null||idNum.length()<end||0>start){ return null;}
        String substring = idNum.substring(start, end);
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i <substring.length() ; i++) {
            stringBuilder.append("*");
        }
        idNum.replace(substring,stringBuilder.toString());
        return idNum;
    }
}
