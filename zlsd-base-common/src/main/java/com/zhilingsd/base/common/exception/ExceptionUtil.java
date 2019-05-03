package com.zhilingsd.base.common.exception;

/**
 * 非法参数异常
 *
 * @author zhangrong67
 * @version 1.0
 * @since 1.0 2017年12月18日 20:20
 */
public class ExceptionUtil {

    public static String getMessage(Exception e){
        StackTraceElement[] stackTraceElements = e.getStackTrace();
        StringBuffer stringBuffer = new StringBuffer(e.toString());
        for(StackTraceElement stackTraceElement : stackTraceElements){
            stringBuffer.append("\tat " + stackTraceElement);
        }
        return stringBuffer.toString();
    }

    public static String getMessage(BaseException e){
        StackTraceElement[] stackTraceElements = e.getStackTrace();
        StringBuffer stringBuffer = new StringBuffer(e.toString());
        for(StackTraceElement stackTraceElement : stackTraceElements){
            stringBuffer.append("\tat " + stackTraceElement);
        }
        return stringBuffer.toString();
    }
}
