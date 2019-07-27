package com.zhilingsd.base.common.utils.decimal;

import org.apache.commons.lang3.StringUtils;

import java.text.DecimalFormat;

/**
 * ^---^---^---^---^---^---^---^
 * --v---v---v---v---v---v---v--
 *
 * @author zou.cp
 * @version 1.0
 * @Description
 * @createTime 2019年07月27日 11:39*
 * log.info()
 */
public class DecimalUtil {

    public static final String TWO_BIT = "0.00";

    public static String spentTimeSS(long val1, long val2) {
        return showTwoBit((double) (val1 - val2) / 1000);
    }

    public static String showTwoBit(double value) {
        DecimalFormat decimalFormat = new DecimalFormat(DecimalUtil.TWO_BIT);
        return decimalFormat.format(value);
    }

    public static void main(String[] args) {
        System.out.println(spentTimeSS(1000,234));
        System.out.println(spentTimeSS(100000, 34567));
    }

}
