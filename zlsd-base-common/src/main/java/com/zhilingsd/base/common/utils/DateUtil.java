package com.zhilingsd.base.common.utils;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.*;

/**
 * 日期工具
 *
 * @author jiaowl
 * @version $Id: SystemUtils.java, v 0.1 2015-10-16 上午11:56:58 jiaowl Exp $
 */
@Slf4j
public class DateUtil {
    public static final String DATE_HOUR = "yyyy-MM-dd HH";
    public static final String TIME = "HH:mm:ss";
    public static final String DATE_TIME = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE = "yyyy-MM-dd";
    public static final String DATE_MINUTE = "yyyy-MM-dd HH:mm";
    public static final String DATE_D = "yyyy/MM/dd";
    public static final String DATE_D_MINUTE = "yyyy/MM/dd HH:mm";
    public static final String DATE_D_TIME = "yyyy/MM/dd HH:mm:ss";
    public static final String DATE_ = "yyyyMMdd";
    public static final String DATE_YEAR = "yyyy";
    public static final String DATE_MOTH = "yyyy-MM";
    public static final String DATE_TIME_PATTERN = "yyyyMMddHHmmss";
    public static final String DATE_MINUTE_PATTERN = "yyMMddHHmm";
    public static final String DATE_MINUTE_CHINESE = "yyyy年MM月dd日 HH:mm";
    public static final String DATE_MINUTE_CHINESE_YMD = "yyyy年MM月dd日";
    public static final String[] MONTHS = {"一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"};
    public static final String[] QUARTERS = {"一季度", "二季度", "三季度", "四季度"};
    private static final String CRON_DATE_FORMAT = "ss mm HH dd MM ? yyyy";
    private static final String CRON_DATE_FORMAT_EVERY_DAY = "ss mm HH * * ?";
    // 时间格式
    private static String FORMAT_TIME = "yyyy-MM-dd HH:mm:ss";

    public DateUtil() {
    }

    /**
     * 获取当前日期和时间
     * 格式 yyyyMMdd HH:mm:ss
     *
     * @return String
     */
    public static String getCurrentDateStr() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        return formatter.format(new Date());
    }

    /**
     * 获取日期字符串。
     * <p>
     * <pre>
     *  日期字符串格式： yyyyMMdd
     *  其中：
     *      yyyy   表示4位年。
     *      MM     表示2位月。
     *      dd     表示2位日。
     * </pre>
     *
     * @return String "yyyyMMdd"格式的日期字符串。
     */
    public static String getDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        return formatter.format(new Date());
    }

    public static String getTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        return formatter.format(new Date());
    }

    public static String getTime(Date date) {
        if (date == null) {
            return "";
        }
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        return formatter.format(date);
    }

    /**
     * 获取当前年度字符串。
     * <p>
     * <pre>
     *  日期字符串格式： yyyy
     *  其中：
     *      yyyy   表示4位年。
     * </pre>
     *
     * @return String "yyyy"格式的当前年度字符串。
     */
    public static String getNowYear() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy");

        return formatter.format(new Date());
    }

    /**
     * 获取当前月度字符串。
     * <p>
     * <pre>
     *  日期字符串格式： MM
     *  其中：
     *      MM   表示4位年。
     * </pre>
     *
     * @return String "yyyy"格式的当前月度字符串。
     */
    public static String getNowMonth() {
        SimpleDateFormat formatter = new SimpleDateFormat("MM");

        return formatter.format(new Date());
    }

    /**
     * 获取当前月度字符串。
     * <p>
     * <pre>
     *  日期字符串格式： dd
     *  其中：
     *      dd   表示4位年。
     * </pre>
     *
     * @return String "yyyy"格式的当前月度字符串。
     */
    public static String getNowDay() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd");

        return formatter.format(new Date());
    }

    /**
     * 获取日期字符串。
     * <p>
     * <pre>
     *  日期字符串格式： yyyyMMdd
     *  其中：
     *      yyyy   表示4位年。
     *      MM     表示2位月。
     *      dd     表示2位日。
     * </pre>
     *
     * @param date 需要转化的日期。
     * @return String "yyyyMMdd"格式的日期字符串。
     */
    public static String getDate(Date date, String format) {
        SimpleDateFormat formatter = new SimpleDateFormat(format);

        return formatter.format(date);
    }

    public static String getDate(Date date) {
        if (date == null) {
            return "";
        }
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        return formatter.format(date);
    }

    /**
     * 获取日期字符串。 
     *
     * <pre>
     *  日期字符串格式： yyyyMMdd
     *  其中：
     *      yyyy   表示4位年。
     *      MM     表示2位月。
     *      dd     表示2位日。
     * </pre>
     *
     * @param date
     *                需要转化的日期。
     * @return String "yyyyMMdd"格式的日期字符串。
     */
    /**
     * 将指定的日期字符串转化为日期对象
     *
     * @param dateStr 日期字符串
     * @return java.util.Date
     * @roseuid 3F39FE450385
     */
    public static Date getDate(String dateStr) {
        if (null == dateStr || dateStr.equals("")) {
            throw new IllegalArgumentException("date must not be a null!");
        }

        if (DateUtil.isDate(dateStr)) { // 日期型
            SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
            try {
                java.util.Date date = df.parse(dateStr);
                return date;
            } catch (Exception ex) {
                return null;
            } // end try - catch
        } else if (DateUtil.isDateTime(dateStr)) { // 日期时间型
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
            try {
                java.util.Date date = df.parse(dateStr);
                return date;
            } catch (Exception ex) {
                return null;
            } // end try - catch
        } // end if
        return null;
    }

    public static Date getDate(String dateStr, String pattern) {
        if (DateUtil.isDateTime(dateStr, pattern)) { // 日期型
            SimpleDateFormat df = new SimpleDateFormat(pattern);
            try {
                java.util.Date date = df.parse(dateStr);
                return date;
            } catch (Exception ex) {
                return null;
            } // end try - catch
        }
        return null;
    }

    /**
     * 获取日期字符串。
     * <p>
     * <pre>
     *  日期字符串格式： yyyy-MM-dd
     *  其中：
     *      yyyy   表示4位年。
     *      MM     表示2位月。
     *      dd     表示2位日。
     * </pre>
     *
     * @return String "yyyy-MM-dd"格式的日期字符串。
     */
    public static String getHyphenDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        return formatter.format(new Date());
    }

    /**
     * 获取日期字符串。
     * <p>
     * <pre>
     *  日期字符串格式： yyyy-MM-dd
     *  其中：
     *      yyyy   表示4位年。
     *      MM     表示2位月。
     *      dd     表示2位日。
     * </pre>
     *
     * @param date 需要转化的日期。
     * @return String "yyyy-MM-dd"格式的日期字符串。
     */
    public static String getHyphenDate(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        return formatter.format(date);
    }

    /**
     * 将"yyyyMMdd"格式的日期字符串转换为日期对象。
     *
     * @param source 日期字符串。
     * @return Date 日期对象。
     */
    public static Date parsePlainDate(String source) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

        return sdf.parse(source, new ParsePosition(0));
    }

    /**
     * 将“yyyy-MM-dd”格式的日期字符串转换为日期对象。
     *
     * @param source 日期字符串。
     * @return Date 日期对象。
     */
    public static Date parseHyphenDate(String source) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        return sdf.parse(source, new ParsePosition(0));
    }

    /**
     * 将指定格式的日期字符串转换为日期对象。
     *
     * @param source  日期字符串。
     * @param pattern 模式。
     * @return Date 日期对象。
     */
    public static Date parseDate(String source, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);

        return sdf.parse(source, new ParsePosition(0));
    }

    /**
     * 将指定格式的时间字符串转换为日期对象。
     *
     * @param time  时间字符串（HH:mm:ss）。
     * @return Date 日期对象。
     */
    public static Date parseTime(String time) {
        String dateStr = getDateTime(DATE, new Date());
        dateStr = dateStr + " " + time;
        return parseDate(dateStr, DATE_TIME);
    }

    /**
     * 将“yyyy-MM-dd”格式的日期字符串转换为“yyyyMMdd”格式的日期字符串。
     *
     * @param source 日期字符串。
     * @return String "yyyymmdd"格式的日期字符串。
     */
    public static String toPlainDate(String source) {
        Date date = parseHyphenDate(source);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");

        return formatter.format(date);
    }

    /**
     * 将“yyyyMMdd”格式的日期字符串转换为“yyyy-MM-dd”格式的日期字符串。
     *
     * @param source 日期字符串。
     * @return String "yyyy-MM-dd"格式的日期字符串。
     */
    public static String toHyphenDate(String source) {
        Date date = parsePlainDate(source);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        return formatter.format(date);
    }

    /**
     * 获取周一的时间
     *
     * @return
     */
    public static Date theMonDayOfWeek() {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(System.currentTimeMillis());
        c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        Date time = c.getTime();
        return time;
    }

    /**
     * 获取周一的时间
     *
     * @return
     */
    public static Date theDayOfWeek(int i) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(System.currentTimeMillis());
        c.set(Calendar.DAY_OF_WEEK, i);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        Date time = c.getTime();
        return time;
    }

    public static Date theDayOfWeekMax(int i) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(System.currentTimeMillis());
        c.set(Calendar.DAY_OF_WEEK, i);
        c.set(Calendar.HOUR_OF_DAY, 23);
        c.set(Calendar.MINUTE, 59);
        c.set(Calendar.SECOND, 59);
        Date time = c.getTime();
        return time;
    }

    /**
     * 获取周六的时间
     *
     * @return
     */
    public static Date theSaturdayOfWeek() {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(System.currentTimeMillis());
        c.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        Date time = c.getTime();
        return time;
    }

    /**
     * 获取周天
     *
     * @return
     */
    public static Date theSunDayOfWeek() {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(System.currentTimeMillis());
        c.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        Date time = c.getTime();
        return time;
    }

    /**
     * 获取某个时间月份的第一天
     *
     * @return
     */
    public static Date theFirstDayOfMonth() {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, 0);
        c.set(Calendar.DAY_OF_MONTH, 1);//设置为1号,当前日期既为本月第一天
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        Date time = c.getTime();
        return time;
    }

    /**
     * 获取某个时间月份的最后一天
     *
     * @return
     */
    public static Date theLastDayOfMonth() {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
        c.set(Calendar.HOUR_OF_DAY, 23);
        c.set(Calendar.MINUTE, 59);
        c.set(Calendar.SECOND, 59);
        c.set(Calendar.MILLISECOND, 999);
        Date time = c.getTime();
        return time;
    }

    /**
     * 获取当月的第一天
     *
     * @return
     */
    public static Date theFirstDayOfMonth(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.MONTH, 0);
        c.set(Calendar.DAY_OF_MONTH, 1);//设置为1号,当前日期既为本月第一天
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        Date time = c.getTime();
        return time;
    }

    /**
     * 获取当月的最后一天
     *
     * @return
     */
    public static Date theLastDayOfMonth(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        Date time = c.getTime();
        return time;
    }

    /**
     * 获取时间戳，将日期对象转换为时间戳类型。
     *
     * @param date 日期对象
     * @return Timestamp 时间戳
     */
    public static Timestamp getTimestamp(Date date) {
        return new Timestamp(date.getTime());
    }

    /**
     * 获取时间戳，将当前日期转换为时间戳类型。
     *
     * @return Timestamp 时间戳
     */
    public static Timestamp getTimestamp() {
        return new Timestamp(System.currentTimeMillis());
    }

    /**
     * 将“yyyyMMdd”格式的日期字符串转换为Timestamp类型的对象。
     *
     * @param source 日期字符串
     * @return Timestamp 时间戳
     */
    public static Timestamp parseTimestamp(String source) {
        Date date = parsePlainDate(source);

        return getTimestamp(date);
    }

    /**
     * 获得年度周期 <br>
     * Example:<br>
     * XJPDateUtil.getPeriodYear("20040229" , -1);<br>
     * XJPDateUtil.getPeriodYear("20040228" , -1);<br>
     * XJPDateUtil.getPeriodYear("20020230" , 2);<br>
     *
     * @param source     时间串
     * @param yearPeriod 年度周期 -1代表本时间的上一年度,以次类推。
     * @return String 时间。
     */
    public static String getPeriodYear(String source, int yearPeriod) {
        int p = Integer.parseInt(source.substring(0, 4)) + yearPeriod;
        String newYear = p + source.substring(4);
        Date date = parsePlainDate(newYear);
        String s = getDate(date);
        int ny = Integer.parseInt(s);
        int sy = Integer.parseInt(newYear);

        while (ny > sy) {
            sy--;
            ny = Integer.parseInt(getDate(parsePlainDate(String.valueOf(sy))));
        }

        return String.valueOf(sy);
    }

    /**
     * 当前日期延后多少天
     *
     * @param day 天数
     * @return 返回相加后的日期
     */
    public static String addDate(int day) {
        java.util.Calendar c = java.util.Calendar.getInstance();

        c.setTimeInMillis(System.currentTimeMillis() + ((long) day) * 24 * 3600 * 1000);
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
        return df.format(c.getTime());
    }

    /**
     * 日期减上多少月前的日期
     *
     * @param now
     * @param month
     * @return
     */
    public static Date dateReduceMonth(Date now, int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        calendar.add(Calendar.MONTH, month * -1);
        return calendar.getTime();
    }

    /**
     * 日期减上多少天前的日期
     *
     * @param now
     * @param day
     * @return
     */
    public static Date dateReduceDay(Date now, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        calendar.add(Calendar.DATE, day * -1);
        return calendar.getTime();
    }

    /**
     * 日期减上多少年前的日期
     *
     * @param now
     * @param year
     * @return
     */
    public static Date dateReduceYear(Date now, int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        calendar.add(Calendar.YEAR, year * -1);
        return calendar.getTime();
    }

    /**
     * 日期加上多少月后的日期
     *
     * @param now
     * @param month
     * @return
     */
    public static Date dateAddMonth(Date now, int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        calendar.add(Calendar.MONTH, month);
        return calendar.getTime();
    }

    /**
     * 日期加上多少年后的日期
     *
     * @param now
     * @param year
     * @return
     */
    public static Date dateAddYear(Date now, int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        calendar.add(Calendar.YEAR, year);
        return calendar.getTime();
    }

    /**
     * 日期加上多少天后的日期
     *
     * @param now
     * @param day
     * @return
     */
    public static Date dateAddDay(Date now, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        calendar.add(Calendar.DAY_OF_MONTH, day);
        return calendar.getTime();
    }

    /**
     * 日期添加多少天后的日期
     *
     * @param datestr 日期字符串
     * @param day     相对天数，为正数表示之后，为负数表示之前
     * @return 指定日期字符串n天之前或者之后的日期
     */
    public static Date dateAddDate(String datestr, int day) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        java.sql.Date olddate = null;
        try {
            df.setLenient(false);
            olddate = new java.sql.Date(df.parse(datestr).getTime());
        } catch (ParseException e) {
            throw new RuntimeException("日期转换错误");
        }
        Calendar cal = new GregorianCalendar();
        cal.setTime(olddate);

        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int nowDay = cal.get(Calendar.DAY_OF_MONTH);

        int newDay = nowDay + day;

        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.DAY_OF_MONTH, newDay);

        return new Date(cal.getTimeInMillis());
    }

    /**
     * 时间戳转换成日期格式字符串
     *
     * @param seconds 精确到秒的字符串
     * @param format
     * @return
     */
    public static String timeStamp2Date(String seconds, String format) {
        if (seconds == null || seconds.isEmpty() || "null".equals(seconds)) {
            return "";
        }
        if (format == null || format.isEmpty()) {
            format = FORMAT_TIME;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date(Long.valueOf(seconds + "000")));
    }

    /**
     * 日期格式字符串转换成时间戳
     *
     * @param date   字符串日期
     * @param format 如：yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static String date2TimeStamp(String date, String format) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return String.valueOf(sdf.parse(date).getTime() / 1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 取得当前时间戳（精确到秒）
     *
     * @return
     */
    public static String timeStamp() {
        long time = System.currentTimeMillis();
        String t = String.valueOf(time / 1000);
        return t;
    }

    /**
     * 返回毫秒
     *
     * @param date 日期
     * @return 返回毫秒
     */
    public static long getMillis(java.util.Date date) {
        java.util.Calendar c = java.util.Calendar.getInstance();
        c.setTime(date);
        return c.getTimeInMillis();
    }

    /**
     * 获取当前日期和时间
     *
     * @param format 日期格式 例：yyyy-MM-dd hh:mm
     * @return String
     */
    public static String getNowDate(String format) {
        Date date = new Date();
        String str = null;
        SimpleDateFormat df = new SimpleDateFormat(format);
        str = df.format(date);
        return str;
    }

    /**
     * 根据日期时间获得字符串时间
     *
     * @param dateStr
     * @return
     * @throws Exception
     */
    public static String getTimeStr(String dateStr) throws Exception {
        Date date = getDate(dateStr);
        String str = null;
        SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
        str = df.format(date);
        return str;
    }

    /**
     * 根据日期时间获得字符串时间
     *
     * @param date
     * @return
     * @throws Exception
     */
    public static String getTimeStr6(Date date) throws Exception {
        SimpleDateFormat df = new SimpleDateFormat("HHmmss");
        String str = df.format(date);
        return str;
    }

    /**
     * 取得当前日期
     *
     * @return
     */
    public static String getTimeStr6() {
        String str = "";
        SimpleDateFormat df = new SimpleDateFormat("HHmmss");
        str = df.format(new Date());
        return str;
    }

    /**
     * 日期添加多少分钟后的时间
     *
     * @param day
     * @param x
     * @return
     */
    public static Date addDateMinut(Date day, int x) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(day);
        cal.add(Calendar.MINUTE, x);// 24小时制
        day = cal.getTime();
        cal = null;
        return day;

    }

    /**
     * 日期减少多少分钟后的时间
     *
     * @param day
     * @param x
     * @return
     */
    public static String reduceDateMinut(Date day, int x) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 24小时制
        Calendar cal = Calendar.getInstance();
        cal.setTime(day);
        cal.add(Calendar.MINUTE, -x);// 24小时制
        day = cal.getTime();
        cal = null;
        return format.format(day);

    }

    /**
     * 获得字符串时间
     *
     * @return
     */
    public static String getTimeStr() {
        String str = "";
        SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
        str = df.format(new Date());
        return str;
    }

    /**
     * 判断字符串是否日期时间格式
     *
     * @param str
     * @return
     */
    public static boolean isDateTime(String str) {
        return isDateTime(str, "yyyy-MM-dd HH:mm:ss.SSS");
    }

    /**
     * 判断字符串是否日期时间格式
     *
     * @param str
     * @return
     */
    public static boolean isDateTime(String str, String pattern) {
        boolean convertSuccess = true;
        // 指定日期格式为四位年/两位月份/两位日期，注意yyyy/MM/dd区分大小写；
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        try {
            // 设置lenient为false. 否则SimpleDateFormat会比较宽松地验证日期，比如2007/02/29会被接受，并转换成2007/03/01
            format.setLenient(false);
            format.parse(str);
        } catch (Exception e) {
            // e.printStackTrace();
            // 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
            convertSuccess = false;
        }
        return convertSuccess;
    }

    /**
     * 判断字符串是否日期格式
     *
     * @param str
     * @return
     */
    public static boolean isDate(String str) {
        boolean convertSuccess = true;
        // 指定日期格式为四位年/两位月份/两位日期，注意yyyy/MM/dd区分大小写；
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        try {
            // 设置lenient为false. 否则SimpleDateFormat会比较宽松地验证日期，比如2007/02/29会被接受，并转换成2007/03/01
            format.setLenient(false);
            format.parse(str);
        } catch (Exception e) {
            // e.printStackTrace();
            // 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
            convertSuccess = false;
        }
        return convertSuccess;
    }

    /**
     * 获得 这周的第一天
     *
     * @return
     */
    public static Date getThisWeekFirstDay() {
        try {
            SimpleDateFormat datef = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            Calendar calendar = Calendar.getInstance();
            // 得到当天是这周的第几天
            int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
            // 减去dayOfWeek,得到第一天的日期，因为Calendar用０－６代表一周七天，所以要减一
            calendar.add(Calendar.DAY_OF_WEEK, -(dayOfWeek - 1));
            String beginTime1 = datef.format(calendar.getTime()) + " 00:00:00";
            Date firstDateOfWeek = dateFormat.parse(beginTime1);

            return firstDateOfWeek;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获得这周的最后一天
     *
     * @return
     */
    public static Date getThisWeekLastDay() {
        try {
            SimpleDateFormat datef = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            Calendar calendar = Calendar.getInstance();
            // 得到当天是这周的第几天
            int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
            // 每周7天，加６，得到最后一天的日子
            calendar.add(Calendar.DAY_OF_WEEK, 7 - dayOfWeek);
            String endTime1 = datef.format(calendar.getTime()) + " 23:59:59";
            Date lastDateOfWeek = dateFormat.parse(endTime1);

            return lastDateOfWeek;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获得这个月的第一天
     *
     * @return
     */
    public static Date getThisMonthFirstDay() {
        try {
            SimpleDateFormat datef = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            Calendar c = Calendar.getInstance();
            c.add(Calendar.MONTH, 0);
            c.set(Calendar.DAY_OF_MONTH, 1);//设置为1号,当前日期既为本月第一天
            String beginTime1 = datef.format(c.getTime()) + " 00:00:00";
            Date firstDateOfWeek = dateFormat.parse(beginTime1);
            return firstDateOfWeek;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获得这个月的最后一天
     *
     * @return
     */
    public static Date getThisMonthLastDay() {
        try {
            SimpleDateFormat datef = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            Calendar ca = Calendar.getInstance();
            ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));
            String endTime1 = datef.format(ca.getTime()) + " 23:59:59";
            Date lastDateOfWeek = dateFormat.parse(endTime1);

            return lastDateOfWeek;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 设置时间后获得日期类型
     *
     * @param time
     * @return
     */
    public static Date setTimeGetDate(Date myDate, String time) {
        try {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            java.util.Date date = df.parse(DateUtil.getDate(myDate) + " " + time);
            return date;
        } catch (Exception e) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                java.util.Date date = sdf.parse(DateUtil.getDate(myDate) + " " + time);
                return date;
            } catch (Exception ex) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 获取某一天的0时0分0秒时间
     *
     * @param dt
     * @return
     */
    public static Date getStartTimeOfDay(Date dt) {
        if (dt == null) {
            return null;
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    /**
     * 获取某一天的23时59分59秒时间
     *
     * @param dt
     * @return
     */
    public static Date getEndTimeOfDay(Date dt) {
        if (dt == null) {
            return null;
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 999);
        return cal.getTime();
    }

    /**
     * 获取某一天的的下一天的0时0分0秒时间
     *
     * @param dt
     * @return
     */
    public static Date getStartTimeOfNextDay(Date dt) {
        if (dt == null) {
            return null;
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        cal.add(Calendar.DAY_OF_MONTH, 1);
        return cal.getTime();
    }

    /**
     * 日期减指定天数
     *
     * @param day 天数
     * @return 返回相减后的日期
     */
    public static Date deleteDate(Date d, long day) {

        long time = d.getTime();
        day = day * 24 * 60 * 60 * 1000;
        time -= day;
        return new Date(time);

    }

    /**
     * 日期之间间隔天数
     *
     * @param before
     * @param after
     * @return
     */
    public static int getBetweenDays(Date before, Date after) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            before = sdf.parse(sdf.format(before));
            after = sdf.parse(sdf.format(after));
            Calendar cal = Calendar.getInstance();
            cal.setTime(before);
            long time1 = cal.getTimeInMillis();
            cal.setTime(after);
            long time2 = cal.getTimeInMillis();
            long betweenDays = (time2 - time1) / (1000 * 3600 * 24);

            int result = Integer.parseInt(String.valueOf(betweenDays));
            return result;
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * 获得昨天开始时间
     *
     * @return
     */
    public static Date getYesterdayBegin() {
        try {
            SimpleDateFormat datef = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            Calendar calendar = Calendar.getInstance();
            // 得到当天是这周的第几天
            calendar.add(Calendar.DAY_OF_WEEK, -1);
            String beginTime1 = datef.format(calendar.getTime()) + " 00:00:00";
            Date firstDateOfWeek = dateFormat.parse(beginTime1);

            return firstDateOfWeek;
        } catch (Exception e) {
        }
        return null;
    }

    /**
     * 获得昨天结束时间
     *
     * @return
     */
    public static Date getYesterdayEnd() {
        try {
            SimpleDateFormat datef = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DAY_OF_WEEK, -1);
            String endTime1 = datef.format(calendar.getTime()) + " 23:59:59";
            Date lastDateOfWeek = dateFormat.parse(endTime1);

            return lastDateOfWeek;
        } catch (Exception e) {
        }
        return null;
    }

    /**
     * 获得本季第一天
     *
     * @return
     */
    public static Date getThisQuarterFirstDay() {
        try {
            SimpleDateFormat datef = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            Calendar c = Calendar.getInstance();
            c.set(Calendar.DAY_OF_MONTH, 1);//设置为1号,当前日期既为本月第一天
            int month = c.get(Calendar.MONTH) + 1;
            System.out.println(month);
            if (1 == month || 2 == month || 3 == month) {
                c.set(Calendar.MONTH, 12);
            } else if (4 == month || 5 == month || 6 == month) {
                c.set(Calendar.MONTH, 3);
            } else if (7 == month || 8 == month || 9 == month) {
                c.set(Calendar.MONTH, 6);
            } else {
                c.set(Calendar.MONTH, 9);
            }
            String beginTime1 = datef.format(c.getTime()) + " 00:00:00";
            Date firstDateOfWeek = dateFormat.parse(beginTime1);
            return firstDateOfWeek;
        } catch (Exception e) {
        }
        return null;
    }

    /**
     * 获得本季最后一天
     *
     * @return
     */
    public static Date getThisQuarterLastDay() {
        try {
            SimpleDateFormat datef = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            Calendar c = Calendar.getInstance();
            int month = c.get(Calendar.MONTH) + 1;
            if (1 == month || 2 == month || 3 == month) {
                c.set(Calendar.MONTH, 2);
            } else if (4 == month || 5 == month || 6 == month) {
                c.set(Calendar.MONTH, 5);
            } else if (7 == month || 8 == month || 9 == month) {
                c.set(Calendar.MONTH, 8);
            } else {
                c.set(Calendar.MONTH, 11);
            }
            c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
            String endTime1 = datef.format(c.getTime()) + " 23:59:59";
            Date lastDateOfWeek = dateFormat.parse(endTime1);

            return lastDateOfWeek;
        } catch (Exception e) {
        }
        return null;
    }

    /**
     * 获得本年第一天
     *
     * @return
     */
    public static Date getThisYearFirstDay() {
        try {
            SimpleDateFormat datef = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            Calendar c = Calendar.getInstance();
            c.add(Calendar.MONTH, 0);
            c.set(Calendar.DAY_OF_YEAR, 1);//设置为1号,当前日期既为本月第一天
            String beginTime1 = datef.format(c.getTime()) + " 00:00:00";
            Date firstDateOfWeek = dateFormat.parse(beginTime1);
            return firstDateOfWeek;
        } catch (Exception e) {
        }
        return null;
    }

    /**
     * 获得本年最后一天
     *
     * @return
     */
    public static Date getThisYearLastDay() {
        try {
            SimpleDateFormat datef = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            Calendar ca = Calendar.getInstance();
            ca.set(Calendar.DAY_OF_YEAR, ca.getActualMaximum(Calendar.DAY_OF_YEAR));
            String endTime1 = datef.format(ca.getTime()) + " 23:59:59";
            Date lastDateOfWeek = dateFormat.parse(endTime1);

            return lastDateOfWeek;
        } catch (Exception e) {
        }
        return null;
    }

    public static Date getDayEnd(Date date) {
        try {
            SimpleDateFormat datef = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            Calendar ca = Calendar.getInstance();
            ca.setTime(date);
            String endTime1 = datef.format(ca.getTime()) + " 23:59:59";
            Date dateEnd = dateFormat.parse(endTime1);

            return dateEnd;
        } catch (Exception e) {
        }
        return null;
    }

    public static Date getDayBegin(Date date) {
        try {
            SimpleDateFormat datef = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            Calendar ca = Calendar.getInstance();
            ca.setTime(date);
            String endTime1 = datef.format(ca.getTime()) + " 00:00:00";
            Date dateEnd = dateFormat.parse(endTime1);

            return dateEnd;
        } catch (Exception e) {
        }
        return null;
    }

    public static String getDayEndStr(Date date) {
        try {
            SimpleDateFormat datef = new SimpleDateFormat("yyyy-MM-dd");
            Calendar ca = Calendar.getInstance();
            ca.setTime(date);
            String endTime1 = datef.format(ca.getTime()) + " 23:59:59";
            return endTime1;
        } catch (Exception e) {
        }
        return null;
    }

    public static String getDayBeginStr(Date date) {
        try {
            SimpleDateFormat datef = new SimpleDateFormat("yyyy-MM-dd");
            Calendar ca = Calendar.getInstance();
            ca.setTime(date);
            String endTime1 = datef.format(ca.getTime()) + " 00:00:00";
            return endTime1;
        } catch (Exception e) {
        }
        return null;
    }

    /**
     * 判断日期是否相同
     *
     * @param d1
     * @param d2
     * @return
     */
    public static boolean isSame(Date d1, Date d2) {
        try {
            SimpleDateFormat datef = new SimpleDateFormat("yyyyMMdd");
            return datef.format(d1).equals(datef.format(d2));
        } catch (Exception e) {
        }
        return false;
    }

    /**
     * 不区分时间比较日期
     *
     * @param before 前日期
     * @param end    后日期
     * @return 等于0，日期相等；大于0，前日期大于后日期；小于0，前日期小于后日期
     */
    public static int compare(Date before, Date end) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String beforeStr = sdf.format(before);
        String endStr = sdf.format(end);

        return beforeStr.compareTo(endStr);
    }

    /**
     * 快速获取指定日期
     *
     * @param year  年
     * @param month 月
     * @param day   日
     * @return
     */
    public static Date getDate(Integer year, Integer month, Integer day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, day);
        return calendar.getTime();
    }

    /**
     * 获取月份的最开早时间
     * 0点0分0秒
     *
     * @param i 加前月份的偏移量,0:本月 1:下个月
     * @return
     */
    public static Date getMonthTime(int i) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, i);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * 获得时分秒毫秒
     *
     * @return
     */
    public static String getTimeStr9() {
        SimpleDateFormat formatter = new SimpleDateFormat("HHmmssSSS");
        return formatter.format(new java.util.Date());
    }

    /**
     * 给某个指定日期加上天数
     *
     * @param date
     * @param day
     * @return
     * @throws ParseException
     */
    public static Date addDate(Date date, long day) {
        if (date == null) {
            return date;
        }
        long time = date.getTime(); // 得到指定日期的毫秒数
        day = day * 24 * 60 * 60 * 1000; // 要加上的天数转换成毫秒数
        time += day; // 相加得到新的毫秒数
        return new Date(time); // 将毫秒数转换成日期
    }

    /**
     * 获取1970的日期
     *
     * @return
     */
    public static Date getGregorianDate() {
        Calendar calendar = new GregorianCalendar(1900, 0, -1);
        Date d = calendar.getTime();
        return d;
    }

    /***
     * @param date 时间
     * @return cron类型的日期
     */
    public static String getCron(final Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(CRON_DATE_FORMAT);
        String formatTimeStr = "";
        if (date != null) {
            formatTimeStr = sdf.format(date);
        }
        return formatTimeStr;
    }

    /**
     * 获取cron表达式
     */
    public static String getCronByType(final Date date, Integer type) {
        if (date == null || type == null) {
            return null;
        }
        SimpleDateFormat sdf = null;
        if (0 == type) {
            sdf = new SimpleDateFormat(CRON_DATE_FORMAT);
        } else if (1 == type) {
            sdf = new SimpleDateFormat(CRON_DATE_FORMAT_EVERY_DAY);
        } else {
            return null;
        }
        return sdf.format(date);
    }


    /**
     * 获取当前日期
     *
     * @return
     */
    public static Date getCurrentDate() {
        return new Date();
    }

    /**
     * 获取当前时间
     *
     * @return
     */
    public static Timestamp getCurrentTime() {
        return new Timestamp(System.currentTimeMillis());
    }

    public static Long getNowTime() {
        return System.currentTimeMillis();
    }

    /**
     * 时间转字符串--默认格式
     *
     * @param date
     * @return
     */
    public static String convertDateToString(Date date) {
        return getDateTime(DATE_TIME, date);
    }

    /**
     * 时间转字符串--按指定格式
     *
     * @param pattern
     * @param date
     * @return
     */
    public static String convertDateToString(String pattern, Date date) {
        return getDateTime(pattern, date);
    }

    public static String getDateTime(String aMask, Date aDate) {
        SimpleDateFormat df = null;
        String returnValue = "";

        if (aDate == null) {
        } else {
            df = new SimpleDateFormat(aMask);
            returnValue = df.format(aDate);
        }

        return (returnValue);
    }

    /**
     * 字符串转时间--按默认格式
     * "yyyy-MM-dd HH:mm:ss"
     *
     * @param strDate
     * @return
     * @throws ParseException
     */
    public static Date convertStringToDate(final String strDate) throws ParseException {
        return convertStringToDate(DATE_TIME, strDate);
    }

    /**
     * 字符串转时间--按指定格式
     *
     * @param pattern
     * @param strDate
     * @return
     * @throws ParseException
     */
    public static Date convertStringToDate(String pattern, String strDate) throws ParseException {
        SimpleDateFormat df;
        Date date;
        df = new SimpleDateFormat(pattern);

        try {
            date = df.parse(strDate);
        } catch (ParseException pe) {
            // log.error("ParseException: " + pe);
            throw new ParseException(pe.getMessage(), pe.getErrorOffset());
        }

        return (date);
    }

    /**
     * 获得日期与本周一相差的天数
     *
     * @param date
     * @return
     */
    public static int getMondayPlus(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        // 获得今天是一周的第几天，星期日是第一天，星期二是第二天......
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
        if (dayOfWeek == 1) {
            return -6;
        } else {
            return 2 - dayOfWeek;
        }
    }

    /**
     * 获取当前日期的上一天
     *
     * @return
     */
    public static Date getYesterday() {
        Calendar cd = Calendar.getInstance();
        cd.add(Calendar.DATE, -1);
        return cd.getTime();
    }

    /**
     * 如果date比当前时间早，返回true；反之返回false
     *
     * @param date
     * @return
     */
    public static boolean isBypastTime(Date date) {
        Calendar cd1 = Calendar.getInstance();
        Calendar cd2 = Calendar.getInstance();
        cd2.setTime(date);
        return cd1.after(cd2);
    }

    /**
     * 获取两个日期之间所有的日期列表（格式yyyy-MM-dd)
     *
     * @param date1
     * @param date2
     * @return
     * @throws ParseException
     */
    public static List<String> getBetweenDates(String date1, String date2) throws ParseException {
        if (StringUtils.isBlank(date1) || StringUtils.isBlank(date2)) {
            return null;
        }
        List<String> list = new ArrayList<String>();
        if (!date1.equals(date2)) {
            String tmp;
            if (date1.compareTo(date2) > 0) { // 确保 date1的日期不晚于date2
                tmp = date1;
                date1 = date2;
                date2 = tmp;
            }
            tmp = convertDateToString(DATE, convertStringToDate(DATE, date1));// 格式化
            while (tmp.compareTo(date2) <= 0) {
                list.add(tmp);
                tmp = addDate(DATE, tmp, "D", 1);
            }
        } else {
            list.add(date1);
        }
        return list;
    }

    /**
     * 获取当前日期的n个月前日期
     *
     * @return
     */
    public static Date getBeforeNMonth(int n) {
        Calendar cd = Calendar.getInstance();
        cd.add(Calendar.MONTH, -n);
        return cd.getTime();
    }

    /**
     * 获取两个月份之间所有的月份列表（格式yyyy-MM)
     *
     * @param date1
     * @param date2
     * @return
     * @throws ParseException
     */
    public static List<String> getBetweenMonths(String date1, String date2) throws ParseException {
        if (StringUtils.isBlank(date1) || StringUtils.isBlank(date2)) {
            return null;
        }
        List<String> list = new ArrayList<String>();
        if (!date1.equals(date2)) {
            String tmp;
            if (date1.compareTo(date2) > 0) { // 确保 date1的日期不晚于date2
                tmp = date1;
                date1 = date2;
                date2 = tmp;
            }
            tmp = convertDateToString(DATE_MOTH, convertStringToDate(DATE_MOTH, date1));// 格式化
            while (tmp.compareTo(date2) <= 0) {
                list.add(tmp);
                tmp = addDate(DATE_MOTH, tmp, "M", 1);
            }
        } else {
            list.add(date1);
        }
        return list;
    }

    /**
     * 比较两个日期的大小
     *
     * @param date1
     * @param date2
     * @return
     */
    public static int compareDate(Date date1, Date date2) {
        if (date1.getTime() > date2.getTime()) {
            return 1;
        } else if (date1.getTime() < date2.getTime()) {
            return -1;
        } else {
            return 0;
        }
    }


    /**
     * 两个日期相差的天数
     *
     * @param date1
     * @param date2
     * @return
     */
    public static int getGapOfTwoDate(Date date1, Date date2) {
        long day = (date1.getTime() - date2.getTime()) / (24 * 60 * 60 * 1000) > 0 ? (date1.getTime() - date2.getTime()) / (24 * 60 * 60 * 1000) : (date2.getTime() - date1.getTime())
                / (24 * 60 * 60 * 1000);
        return Integer.parseInt(String.valueOf(day));

    }

    public static String getStrBetweenDate(Date date1, Date date2) {

        if (date1 == null || date2 == null) {
            return null;
        }

        //前的时间
        Date fd = date1.getTime() <= date2.getTime() ? date1 : date2;
        //后的时间
        Date td = date1.getTime() > date2.getTime() ? date1 : date2;
        //两时间差,精确到毫秒
        long diff = td.getTime() - fd.getTime();
        long day = diff / 86400000;                         //以天数为单位取整
        long hour = diff % 86400000 / 3600000;               //以小时为单位取整
        long min = diff % 86400000 % 3600000 / 60000;       //以分钟为单位取整
        long seconds = diff % 86400000 % 3600000 % 60000 / 1000;   //以秒为单位取整
        //天时分秒
        return day + "天" + hour + "小时" + min + "分" + seconds + "秒";
    }

    /**
     * 日期增加或者减少秒，分钟，天，月，年
     *
     * @param srcDate
     * @param type    类型 Y M D HH MM SS 年月日时分秒
     * @param offset  （整数）
     * @return 增加或者减少之后的日期
     */
    public static Date addDate(Date srcDate, String type, int offset) {
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(srcDate);
        if (type.equals("SS")) {
            gc.add(GregorianCalendar.SECOND, offset);
        } else if (type.equals("MM")) {
            gc.add(GregorianCalendar.MINUTE, offset);
        } else if (type.equals("HH")) {
            gc.add(GregorianCalendar.HOUR, offset);
        } else if (type.equals("D")) {
            gc.add(GregorianCalendar.DATE, offset);
        } else if (type.equals("M")) {
            gc.add(GregorianCalendar.MONTH, offset);
        } else if (type.equals("Y")) {
            gc.add(GregorianCalendar.YEAR, offset);
        }
        return gc.getTime();
    }

    public static String addDate(String srcDate, String type, int offset) throws ParseException {
        return convertDateToString(addDate(convertStringToDate(srcDate), type, offset));
    }

    public static String addDate(String pattern, String srcDate, String type, int offset) throws ParseException {
        return convertDateToString(pattern, addDate(convertStringToDate(pattern, srcDate), type, offset));
    }

    /**
     * 根据日期计算年龄
     *
     * @param birthday
     * @return
     */
    public static int getAgeByBirthday(Date birthday) {
        Calendar cal = Calendar.getInstance();

        if (cal.before(birthday)) {
            throw new IllegalArgumentException("The birthDay is before Now.It's unbelievable!");
        }

        int yearNow = cal.get(Calendar.YEAR);
        int monthNow = cal.get(Calendar.MONTH) + 1;
        int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);

        cal.setTime(birthday);
        int yearBirth = cal.get(Calendar.YEAR);
        int monthBirth = cal.get(Calendar.MONTH) + 1;
        int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);

        int age = yearNow - yearBirth;

        if (monthNow <= monthBirth) {
            if (monthNow == monthBirth) {
                // monthNow==monthBirth
                if (dayOfMonthNow < dayOfMonthBirth) {
                    age--;
                }
            } else {
                // monthNow>monthBirth
                age--;
            }
        }
        return age;
    }

    /**
     * 判断时间戳是否符合格式
     *
     * @param
     * @param pattern 格式
     * @return
     */
    public static boolean isDatePattern(String date, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        boolean result = false;
        try {
            if (StringUtils.isNotBlank(date) && date.trim().length() == pattern.trim().length()) {
                sdf.setLenient(false);
                System.out.println(sdf.parse(date));
                result = true;
            }
        } catch (ParseException e) {
            result = false;
        }
        return result;
    }

    public static boolean between(Date date, Date start, Date end) throws ParseException {
        return 0 <= getDay(start, date) && 0 >= getDay(end, date);
    }


    public static int getDay(Date date1, Date date2) throws ParseException {
        if (null == date1 || null == date2) {
            return 0;
        }

        return Long.valueOf((date2.getTime() - date1.getTime()) / 86400000).intValue();
    }

    public static String datetimeToString(Date date) {

        if (null == date) {
            throw new IllegalArgumentException("date must not be a null!");
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
        return sdf.format(date);
    }

    /**
     * 将当前时间转换为当日零点零分零秒
     *
     * @param date
     * @return
     */
    public static Date getMonningDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * 获取上周一
     *
     * @param date
     * @return
     */
    public static Date getLastWeekMonday(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_MONTH, -1);
        cal.add(Calendar.WEEK_OF_YEAR, -1);// 一周
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        return cal.getTime();
    }

    /**
     * 获取省周日
     *
     * @param date
     * @return
     */
    public static Date getLastWeekSunday(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_MONTH, -1);
        cal.set(Calendar.DAY_OF_WEEK, 1);
        return cal.getTime();
    }

    /**
     * 将日期改为当天的23点59分59秒
     *
     * @param date
     * @return
     */
    public static Date getEveningDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        return calendar.getTime();
    }

    /**
     * 获取当前月第一天
     *
     * @param date
     * @return
     */
    public static Date getMonthStart(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        return cal.getTime();
    }

    /**
     * 获取当前月最后一天
     *
     * @param date
     * @return
     */
    public static Date getMonthEnd(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, 1);
        cal.set(Calendar.DAY_OF_MONTH, 0);
        return cal.getTime();
    }

    /**
     * 获取当前年当前周周一
     *
     * @param year
     * @param week
     * @return
     */
    public static Date getYearWeekFirstDay(Integer year, Integer week) {
        Calendar cal = Calendar.getInstance();
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        cal.setMinimalDaysInFirstWeek(7);
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.WEEK_OF_YEAR, week);
        return cal.getTime();
    }

    /**
     * 获取当前年当前周周日
     *
     * @param year
     * @param week
     * @return
     */
    public static Date getYearWeekEndDay(Integer year, Integer week) {
        Calendar cal = Calendar.getInstance();
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        cal.setMinimalDaysInFirstWeek(7);
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.WEEK_OF_YEAR, week);
        return cal.getTime();
    }

    /**
     * 获取该年有多少周
     *
     * @param year
     * @return
     */
    public static Integer getWeeksOfYear(Integer year) {
        int result = 52;
        Date d = getYearWeekFirstDay(year, 53);
        String date = convertDateToString(DATE, d);
        if (date.substring(0, 4).equals(year + "")) { //判断年度是否相符，如果相符说明有53个周。
            result = 53;
        }
        return result;
    }

    /**
     * @description 时分秒清零
     **/
    public static Date getResetHHMMSS(Date date) {
        if (Objects.isNull(date)){
            return null;
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    /**
     * @description 当天23:59:59
     **/
    public static Date getMaxHHMMSS(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 999);
        return cal.getTime();
    }

    /**
     * 获取下一个月第一天 时分秒清零
     *
     * @param date
     * @return
     */
    public static Date getNextMonthStartReset(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, 1);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    /**
     * 获取当前月第一天 时分秒清零
     *
     * @param date
     * @return
     */
    public static Date getMonthStartReset(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    public static Date getYearFirstDay() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MONTH, 0);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    public static Date getNextYearFirstDay() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.YEAR, 1);
        cal.set(Calendar.MONTH, 0);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    public static Date getNextQuarterStart(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int month = cal.get(Calendar.MONTH);
        if (month < Calendar.APRIL) {
            cal.set(Calendar.MONTH, Calendar.APRIL);
            return getMonthStartReset(cal.getTime());
        } else if (month < Calendar.JULY) {
            cal.set(Calendar.MONTH, Calendar.JULY);
            return getMonthStartReset(cal.getTime());
        } else if (month < Calendar.OCTOBER) {
            cal.set(Calendar.MONTH, Calendar.OCTOBER);
            return getMonthStartReset(cal.getTime());
        } else {
            cal.set(Calendar.MONTH, Calendar.JANUARY);
            cal.add(Calendar.YEAR, 1);
            return getMonthStartReset(cal.getTime());
        }
    }

    /**
     * 返回给前端的时间格式转换
     *
     * @param strDate
     * @param fromFormat
     * @param toFormat
     * @return
     */
    public static String formatDateString(String strDate, String fromFormat, String toFormat) throws ParseException {
        if (null == strDate) {
            return null;        //业务层面部分时间可能是传进来null，原路给返回。
        }
        Date date = convertStringToDate(fromFormat, strDate);
        String resultDateString = convertDateToString(toFormat, date);
        return resultDateString;
    }

    public static Date firstDayOfMonth(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.MONTH, 0);
        c.set(Calendar.DAY_OF_MONTH, 1);// 设置为1号,当前日期既为本月第一天
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        return c.getTime();
    }

    public static Date toDateTime(String source) {
        StringBuilder dateStr = new StringBuilder("00000000000000");
        if (StringUtils.isEmpty(source)) {
            return null;
        }

        String[] dateEles = source.split("[^0-9]+");
        for (int i = 0; i < dateEles.length; i++) {
            if (i == 0) {
                dateStr.replace(0, 4, String.format("%04d", Integer.valueOf(dateEles[0])));
            }
            if (i == 1) {
                dateStr.replace(4, 6, String.format("%02d", Integer.valueOf(dateEles[1])));
            }
            if (i == 2) {
                dateStr.replace(6, 8, String.format("%02d", Integer.valueOf(dateEles[2])));
            }
            if (i == 3) {
                dateStr.replace(8, 10, String.format("%02d", Integer.valueOf(dateEles[3])));
            }
            if (i == 4) {
                dateStr.replace(10, 12, String.format("%02d", Integer.valueOf(dateEles[4])));
            }
            if (i == 5) {
                dateStr.replace(12, 14, String.format("%02d", Integer.valueOf(dateEles[5])));
            }
        }

        try {
            return new SimpleDateFormat("yyyyMMddHHmmss").parse(dateStr.toString());
        } catch (ParseException e) {
            throw new RuntimeException(String.format("parser %s to Date fail", source));
        }

    }

    public static Date toDate(String source) {
        StringBuilder dateStr = new StringBuilder("00000000");
        if (StringUtils.isEmpty(source)) {
            return null;
        }

        String[] dateEles = source.split("[^0-9]+");
        for (int i = 0; i < dateEles.length; i++) {
            if (i == 0) {
                dateStr.replace(0, 4, String.format("%04d", Integer.valueOf(dateEles[0])));
            }
            if (i == 1) {
                dateStr.replace(4, 6, String.format("%02d", Integer.valueOf(dateEles[1])));
            }
            if (i == 2) {
                dateStr.replace(6, 8, String.format("%02d", Integer.valueOf(dateEles[2])));
            }
        }
        try {
            String string = dateStr.toString();
            if (string.length() == 8) {
                return new SimpleDateFormat("yyyyMMdd").parse(string);
            } else if (string.length() > 8) {
                return new SimpleDateFormat("yyyyMMdd").parse(string.substring(0, 8));
            }

        } catch (Exception e) {
            log.error("解析字符串到Date对象失败,原字符串为-->{}", source);
        }
        return null;
    }

    public static Date oneDayAfter(Date date) {
        return new Date(date.getTime() + 24 * 3600 * 1000);
    }

    public static Date firstDayOfRecentMonth() {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, 0);
        c.set(Calendar.DAY_OF_MONTH, 1);// 设置为1号,当前日期既为本月第一天
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        return c.getTime();
    }

    public static Date lastDayOfMonth(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.MONTH, 0);
        c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
        c.set(Calendar.HOUR_OF_DAY, 24);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        return c.getTime();
    }

    public static Date firstDayOfYear(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.MONTH, Calendar.JANUARY);
        c.set(Calendar.DAY_OF_MONTH, 1);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        return c.getTime();
    }

    public static Date firstDayOfRecentSeason(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int month = c.get(Calendar.MONTH);
        c.set(Calendar.DAY_OF_MONTH, 1);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);

        switch (month) {
            case Calendar.JANUARY:
                c.set(Calendar.MONTH, Calendar.JANUARY);
                break;
            case Calendar.FEBRUARY:
                c.set(Calendar.MONTH, Calendar.JANUARY);
                break;
            case Calendar.MARCH:
                c.set(Calendar.MONTH, Calendar.JANUARY);
                break;
            case Calendar.APRIL:
                c.set(Calendar.MONTH, Calendar.APRIL);
                break;
            case Calendar.MAY:
                c.set(Calendar.MONTH, Calendar.APRIL);
                break;
            case Calendar.JUNE:
                c.set(Calendar.MONTH, Calendar.APRIL);
                break;
            case Calendar.JULY:
                c.set(Calendar.MONTH, Calendar.JULY);
                break;
            case Calendar.AUGUST:
                c.set(Calendar.MONTH, Calendar.JULY);
                break;
            case Calendar.SEPTEMBER:
                c.set(Calendar.MONTH, Calendar.JULY);
                break;
            case Calendar.OCTOBER:
                c.set(Calendar.MONTH, Calendar.OCTOBER);
                break;
            case Calendar.NOVEMBER:
                c.set(Calendar.MONTH, Calendar.OCTOBER);
                break;
            case Calendar.DECEMBER:
                c.set(Calendar.MONTH, Calendar.OCTOBER);
                break;
            default:
                break;
        }
        return c.getTime();
    }

    public static Date firstDayOfLastSeason(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int month = c.get(Calendar.MONTH);
        c.set(Calendar.DAY_OF_MONTH, 1);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);

        switch (month) {
            case Calendar.JANUARY:
                c.add(Calendar.YEAR, -1);
                c.set(Calendar.MONTH, Calendar.OCTOBER);
                break;
            case Calendar.FEBRUARY:
                c.add(Calendar.YEAR, -1);
                c.set(Calendar.MONTH, Calendar.OCTOBER);
                break;
            case Calendar.MARCH:
                c.add(Calendar.YEAR, -1);
                c.set(Calendar.MONTH, Calendar.OCTOBER);
                break;
            case Calendar.APRIL:
                c.set(Calendar.MONTH, Calendar.JANUARY);
                break;
            case Calendar.MAY:
                c.set(Calendar.MONTH, Calendar.JANUARY);
                break;
            case Calendar.JUNE:
                c.set(Calendar.MONTH, Calendar.JANUARY);
                break;
            case Calendar.JULY:
                c.set(Calendar.MONTH, Calendar.APRIL);
                break;
            case Calendar.AUGUST:
                c.set(Calendar.MONTH, Calendar.APRIL);
                break;
            case Calendar.SEPTEMBER:
                c.set(Calendar.MONTH, Calendar.APRIL);
                break;
            case Calendar.OCTOBER:
                c.set(Calendar.MONTH, Calendar.JULY);
                break;
            case Calendar.NOVEMBER:
                c.set(Calendar.MONTH, Calendar.JULY);
                break;
            case Calendar.DECEMBER:
                c.set(Calendar.MONTH, Calendar.JULY);
                break;
            default:
                break;
        }

        return c.getTime();
    }

    public static Date firstDayOfLastMonth(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.MONTH, -1);
        c.set(Calendar.DAY_OF_MONTH, 1);// 设置为1号,当前日期既为本月第一天
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        return c.getTime();
    }

    public static Date firstDayOfLastMonth() {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, -1);
        c.set(Calendar.DAY_OF_MONTH, 1);// 设置为1号,当前日期既为本月第一天
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        return c.getTime();
    }

    public static Date lastDayOfLastMonth() {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, 0);
        c.set(Calendar.DAY_OF_MONTH, 1);// 设置为1号,当前日期既为本月第一天
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        return c.getTime();
    }

    /**
     * 获取当天剩余时间（秒）
     *
     * @return
     */
    public static Long getRemainTimes() {

        LocalDateTime midnight = LocalDateTime.now().plusDays(1).withHour(0).withMinute(0).withSecond(0).withNano(0);
        long seconds = ChronoUnit.SECONDS.between(LocalDateTime.now(), midnight);
        return seconds;
    }

    public static boolean checkCurrentMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        int currentMonth = -1;
        int i = calendar.get(Calendar.MONTH);
        calendar.setTime(date);
        return i == calendar.get(Calendar.MONTH);
    }

    public static boolean checkMonth(Date date) {
        Date currentDate = new Date();
        String s = DateUtil.convertDateToString(DateUtil.DATE_MOTH, currentDate);
        Date currentMonth = null;
        try {
            currentMonth = DateUtil.convertStringToDate(DateUtil.DATE_MOTH, s);
        } catch (ParseException e) {
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentMonth);
        long t1 = calendar.getTimeInMillis();
        calendar.setTime(date);
        long t2 = calendar.getTimeInMillis();
        return t1 > t2;
    }

    /**
     * 获取当天开始时间（0点）
     *
     * @param date
     * @return
     */
    public static Date getStartDate(Date date) {
        LocalDateTime localDateTime = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
        localDateTime = localDateTime.withHour(0).withMinute(0).withSecond(0).withNano(0);

        return new Date(localDateTime.toInstant(ZoneOffset.of("+8")).toEpochMilli());
    }

    /**
     * 获取当天结束日期
     *
     * @param date
     * @return
     */
    public static Date getEndDate(Date date) {

        LocalDateTime localDateTime = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
        localDateTime = localDateTime.withHour(23).withMinute(59).withSecond(59).withNano(999999999);
        return new Date(localDateTime.toInstant(ZoneOffset.of("+8")).toEpochMilli());
    }

    /**
     * 获取给定时间 返回每个月的第一天和最后一天 不包含当月 2018-05-01 ----->>>>2018-05-01  2018-06-01  2018-07-01  2018-08-01 2018-09-01
     *
     * @param date
     * @return
     * @author wuzs
     */
    public static List<Date> getMonthOfDayList(Date date) {
        List<Date> listDate = Lists.newArrayList();
        Calendar cal = Calendar.getInstance();// 获取当前日期
        cal.setTime(date);
        cal.set(Calendar.DAY_OF_MONTH, 1);// 设置为1号,当前日期既为本月第一天
        Calendar nowcal = Calendar.getInstance();
        nowcal.set(Calendar.DAY_OF_MONTH, 1);// 设置为1号,当前日期既为本月第一天
        Date dates = DateUtil.stringToDate(DateUtil.dateToString(nowcal.getTime(), "yyyy-MM-dd"), "yyyy-MM-dd");// 转为"yyyy-MM-dd"
        nowcal.setTime(dates);
        while (DateUtil.compareDate(nowcal.getTime(), cal.getTime()) == 1) {
            listDate.add(cal.getTime());
            cal.add(Calendar.MONTH, 1);
        }
        return listDate;
    }

    /**
     * 获取当前时间是星期几
     *
     * @param currentDate
     * @return
     */
    public static int dayForWeek(Date currentDate) {
        Calendar c = Calendar.getInstance();
        c.setTime(currentDate);
        int dayForWeek = 0;
        dayForWeek = c.get(Calendar.DAY_OF_WEEK);
        return dayForWeek;
    }


    /**
     * 日期转化成字符串
     *
     * @param date          日期字符串
     * @param formatPattern 转化格式
     * @return
     */
    public static String dateToString(Date date, String formatPattern) {
        if (date == null) {
            return null;
        }
        return getFormat(formatPattern).format(date);
    }

    /**
     * 字符串转化成日期
     *
     * @param sDate         日期字符串
     * @param formatPattern 转化格式
     * @return
     */
    public static Date stringToDate(String sDate, String formatPattern) {
        if (sDate == null || "".equals(sDate.trim())) {
            return null;
        }
        Date date = null;
        try {
            date = getFormat(formatPattern).parse(sDate);
        } catch (Exception e) {
        }
        return date;
    }

    public static SimpleDateFormat getFormat(String pattern) {
        return new SimpleDateFormat(pattern);
    }

    /**
     * @Description:
     * @Param:
     * @return:
     * @Author: wuzs
     * @Date: 2018/10/26 12:16
     */
    // 获取一段时间内每一天的日期 (当天的不要)
    public static List<String> findDates(Date dBegin, Date dEnd) {
        if (dBegin == null || dEnd == null) {
            return null;
        }
        List<String> list = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String es = sdf.format(dEnd);
        try {
            dEnd = sdf.parse(es);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar calBegin = Calendar.getInstance();
        // 使用给定的 Date 设置此 Calendar 的时间
        calBegin.setTime(dBegin);
        Calendar calEnd = Calendar.getInstance();
        // 使用给定的 Date 设置此 Calendar 的时间
        calEnd.setTime(dEnd);
        while (dEnd.after(calBegin.getTime())) {
            // 根据日历的规则，为给定的日历字段添加或减去指定的时间量
            String date = sdf.format(calBegin.getTime());
            list.add(date);
            calBegin.add(Calendar.DAY_OF_MONTH, 1);
        }
        return list;
    }

    /**
     * @Description:
     * @Param:
     * @return:
     * @Author: wuzs
     * @Date: 2018/10/26 12:16
     */
    // 获取一段时间内每一天的日期 (当天的不要)
    public static List<Date> findInterverDates(Date dBegin, Date dEnd) {
        if (dBegin == null || dEnd == null) {
            return null;
        }
        List<Date> list = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String es = sdf.format(dEnd);
        try {
            dEnd = sdf.parse(es);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar calBegin = Calendar.getInstance();
        // 使用给定的 Date 设置此 Calendar 的时间
        calBegin.setTime(dBegin);
        Calendar calEnd = Calendar.getInstance();
        // 使用给定的 Date 设置此 Calendar 的时间
        calEnd.setTime(dEnd);
        while (dEnd.after(calBegin.getTime())) {
            // 根据日历的规则，为给定的日历字段添加或减去指定的时间量
            list.add(calBegin.getTime());
            calBegin.add(Calendar.DAY_OF_MONTH, 1);
        }
        return list;
    }

    /**
     * 取得两个日期的差值
     *
     * @param startTime           开始日期
     * @param endTime             结束日期
     * @param '适用于yyyy-MM-dd，精确到日
     * @return 差值日
     */
    public static int dateDiff(String startTime, String endTime) {
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
        long nd = 1000 * 24 * 60 * 60;
        long diff;
        long day = 0L;
        try {
            diff = sd.parse(endTime).getTime() - sd.parse(startTime).getTime();
            day = diff / nd;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return (int) day;
    }

    /**
     * @Description: 获取24小时
     * @Param:
     * @return:
     * @Author: wuzs
     * @Date: 2018/12/24 15:59
     */
    public static List<String> get24HourString() {
        List<String> resultList = Lists.newArrayList();
        int size = 24;
        for (int i = 1; i <= size; i++) {
            String hour = i + "";
            if (i < 10) {
                hour = "0" + hour;
            }
            resultList.add(hour);
        }
        return resultList;
    }

    /**
     * 解析excel时间格式
     *
     * @param source
     * @return
     * @throws ParseException
     */
    public static Date toDateWithThrow(String source) throws ParseException {
        StringBuilder dateStr = new StringBuilder("00000000000000");
        if (StringUtils.isEmpty(source)) {
            return null;
        }

        String[] dateEles = source.split("[^0-9]+");
        for (int i = 0; i < dateEles.length; i++) {
            if (i == 0) {
                dateStr.replace(0, 4, String.format("%04d", Integer.valueOf(dateEles[0])));
            }
            if (i == 1) {
                dateStr.replace(4, 6, String.format("%02d", Integer.valueOf(dateEles[1])));
            }
            if (i == 2) {
                dateStr.replace(6, 8, String.format("%02d", Integer.valueOf(dateEles[2])));
            }
            if (i == 3) {
                dateStr.replace(8, 10, String.format("%02d", Integer.valueOf(dateEles[3])));
            }
            if (i == 4) {
                dateStr.replace(10, 12, String.format("%02d", Integer.valueOf(dateEles[4])));
            }
            if (i == 5) {
                dateStr.replace(12, 14, String.format("%02d", Integer.valueOf(dateEles[5])));
            }
        }

        return new SimpleDateFormat("yyyyMMddHHmmss").parse(dateStr.toString());
    }

    /**
     * 根据日期获取年
     * @param date
     * @return
     */
    public static int getYearByDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.YEAR);
    }

    /**
     * 根据日期获取月
     * @param date
     * @return
     */
    public static int getMonthByDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.MONTH) + 1;
    }

    /**
     * 根据日期获取日
     * @param date
     * @return
     */
    public static int getDayByDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.DATE);
    }

    public static void main(String[] args) {
        String dateStr1 = "20121126";
        String dateStr2 = "2012/11/26";
        String dateStr3 = "2012-11-26";
        Date toDate1 = toDate(dateStr1);
        Date toDate2 = toDate(dateStr2);
        Date toDate3 = toDate(dateStr3);
        String dateToString1 = dateToString(toDate1, DateUtil.DATE);
        String dateToString2 = dateToString(toDate2, DateUtil.DATE);
        String dateToString3 = dateToString(toDate3, DateUtil.DATE);
        System.out.println(dateToString1);
        System.out.println(dateToString2);
        System.out.println(dateToString3);
    }
}
