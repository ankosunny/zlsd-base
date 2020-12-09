package com.zhilingsd.base.common.constants;

/**
 * Description: 基本常量
 *
 * @Author zengkai
 * @Date 2019/4/12 16:13
 */
public class BasicsConstant {
    public static final int NEGATIVE_ONE_INT = -1;
    public static final int ZERO_INT = 0;
    public static final int ONE_INT = 1;


    /**
     * 正整数最小值.验证使用 ZERO_INT:测试.ONE_INT:生产
     */
    public static final int MIN_VALUE = ONE_INT;
    /**
     * session 验证开关
     */
    public static final boolean ON_OF_SESSION = true;
    /**
     * 默认的用户ID, ON_OF_SESSION == false 启用
     */
    public static final Integer DEFAULT_USER_ID = 1;


    public static final int TWO_INT = 2;
    public static final int THREE_INT = 3;
    public static final int FOUR_INT = 4;
    public static final int FIVE_INT = 5;
    public static final int SIX_INT = 6;
    public static final int SEVEN_INT = 7;
    public static final int EIGHT_INT = 8;
    public static final int NINE_INT = 9;
    public static final int TEN_INT = 10;
    public static final int ELEVEN_INT = 11;
    public static final int TWELVE_INT = 12;
    public static final int THIRTEEN_INT = 13;
    public static final int FOURTEEN_INT = 14;
    public static final int FIFTEEN_INT = 15;
    public static final int SIXTEEN_INT = 16;
    public static final int SEVENTEEN_INT = 17;
    public static final int EIGHTEEN_INT = 18;
    public static final int NINETEEN_INT = 19;
    public static final int TWENTY_INT = 20;
    public static final int TWENTY_ONE_INT = 21;
    public static final int TWENTY_TWO_INT = 22;
    public static final int TWENTY_THREE_INT = 23;
    public static final int TWENTY_FOUR_INT = 24;
    public static final int TWENTY_FIVE_INT = 25;
    public static final int TWENTY_SIX_INT = 26;
    public static final int TWENTY_SEVEN_INT = 27;
    public static final int TWENTY_EIGHT_INT = 28;
    public static final int TWENTY_NINE_INT = 29;
    public static final int THIRTY_INT = 30;
    public static final int THIRTY_TWO = 32;
    public static final int FORTY_INT = 40;
    public static final int FORTY_THREE = 43;
    public static final int FIFTY_INT = 50;
    public static final int SIXTY_INT = 60;
    public static final int SEVENTY_INT = 70;
    public static final int EIGHTY_INT = 80;
    public static final int NINETY_INT = 90;
    public static final int ONE_HUNDRED_INT = 100;
    public static final int ONE_HUNDRED_TWENTY_EIGHT = 128;
    public static final int FIVE_HUNDRED_INT = 500;
    public static final int ONE_THOUSAND_INT = 1000;
    public static final int TEN_THOUSAND_INT = 10000;

    /**
     * 5秒
     */
    public static final long FIVE_SECONDS = 5;

    /**
     * 30s
     */
    public static final long THIRTY_SECONDS = 30;

    /**
     * 1分钟
     */
    public static final long ONE_MINUTES = 60;

    /**
     * 90秒
     */
    public static final long NINETY_SECONDS = 90;

    /**
     * 永久 - 100年
     */
    public static final long ONE_YEAR = 1 * 365 * 24 * 60 * 60;

    /**
     * 2分钟
     */
    public static final long TWO_MINUTES = 2 * 60;

    /**
     * 5分钟
     */
    public static final long FIVE_MINUTES = 5 * 60;

    /**
     * 10分钟
     */
    public static final long TEN_MINUTES_TO_MILLISECOND = 10 * 60 * 1000;

    /**
     * 三个月
     */
    public static final long THREE_MONTH = 3 * 30 * 24 * 60 * 60 * 1000L;

    /**
     * 10分钟
     */
    public static final long TEN_MINUTES = 10 * 60;
    /**
     * 30分钟
     */
    public static final long THIRTY_MINUTES = 30 * 60;

    /**
     * 15分钟
     */
    public static final long FIFTEEN_MINUTES = 15 * 60;

    /**
     * 1个小时
     */
    public static final long ONE_HOURS = 1 * 60 * 60;

    /**
     * 12小时
     */
    public static final long TWELVE_HOURS = 12 * 60 * 60;

    /**
     * 2个小时
     */
    public static final long TWO_HOURS = 2 * 60 * 60;

    /**
     * 4个小时
     */
    public static final long FOUR_HOURS = 4 * 60 * 60;

    /**
     * 6个小时
     */
    public static final long SIX_HOURS = 6 * 60 * 60;
    /**
     * 一天
     */
    public static final long ONE_DAY = 24 * ONE_HOURS;
    /**
     * 二天
     */
    public static final long TWO_DAYS = 2 * 24 * ONE_HOURS;
    /**
     * 四天
     */
    public static final long FOUR_DAYS = 4 * 24 * ONE_HOURS;

    /**
     * 一周
     */
    public static final long ONE_WEEK = 7 * ONE_DAY;
    /**
     * 一个月
     */
    public static final long ONE_MONTH = 30 * ONE_DAY;


    public static final String APPLICATION_JSON_CHARSET_UTF8 = "application/json; charset=UTF-8";


    /**
     * 乐观锁迭代次数
     */
    public static final int OPTIMISTIC_LOCK_ITERATION_NUM = TEN_INT;


    /**
     * 是
     */
    public static final String YES_VALUE = "1";

    /**
     * 非
     */
    public static final String NO_VALUE = "0";

    /**
     * 用户session缓存key 长度
     */
    public static final int SESSION_PW_LENGTH = FORTY_THREE;

    public static String ZERO_STR = "0";
    public static String ONE_STR = "1";
    public static String TWO_STR = "2";
    public static String THREE_STR = "3";
    public static String FOUR_STR = "4";
    public static String FIVE_STR = "5";
    public static String SIX_STR = "6";
    public static String SEVEN_STR = "7";
    public static String EIGHT_STR = "8";
    public static String NINE_STR = "9";
    public static String TEN_STR = "10";


    public static final String DECIMAL_MIN_STR = "0.000000001";

    /**
     * 字符串-英文逗号分隔符“,”
     */
    public static final String STRING_SPLIT_COMMA = ",";

    /**
     * 字符串-英文冒号分隔符“:”
     */
    public static final String STRING_SPLIT_COLON = ":";

    /**
     * mybatis xml中使用in的sql长度超过1000的配置
     */
    public static final Integer MYBATIS_IN_MAX = 990;

    /**
     * DFSX-DataNode磁盘剩余空间
     */
    public static final String KEY_DFSX_DISK_FREE_SPACE = "diskFreeSpace";

    /**
     * DFSX-DataNode唯一标识
     */
    public static final String KEY_DFSX_DATA_NODE_ID = "dataNodeId";

    /**
     * DFSX-DataNode netty端口
     */
    public static final String KEY_DFSX_NETTY_PORT = "nettyPort";
}
