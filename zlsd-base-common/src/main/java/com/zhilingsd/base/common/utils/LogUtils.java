package com.zhilingsd.base.common.utils;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * @program: 智灵时代广州研发中心
 * @description:日志工具类
 * @author: 吞星(yangguojun)
 * @create: 2019-07-12 11:30
 **/
public class LogUtils {

    /**
     *
     * 功能描述:打印完整堆栈信息
     * @param: [e]
     * @return: java.lang.String
     * @auther: 吞星
     * @date: 2019/7/12-11:31
     */
    public static String getStackTrace(Exception e) {

        StringWriter sw = null;
        PrintWriter pw = null;
        try {
            sw = new StringWriter();
            pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            pw.flush();
            sw.flush();
        } finally {
            if (sw != null) {
                try {
                    sw.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            if (pw != null) {
                pw.close();
            }
        }
        return sw.toString();
    }
}
