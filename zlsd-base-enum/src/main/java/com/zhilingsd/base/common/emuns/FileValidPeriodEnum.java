package com.zhilingsd.base.common.emuns;

/**
 * @author: yuboliang
 * @date: 2020/4/7
 **/
public enum FileValidPeriodEnum {
    /**
     * 30分钟
     */
    THIRTY_MINUTES("thirty_minutes", "30分钟", 30, "/thirty_minutes"),
    /**
     * 1小时
     */
    ONE_HOUR("one_hour", "1小时", 60, "/one_hour"),
    /**
     * 4小时
     */
    FOUR_HOURS("four_hours", "4小时", 4 * 60, "/four_hours"),
    /**
     * 8小时
     */
    EIGHT_HOURS("eight_hours", "8小时", 8 * 60, "/eight_hours"),
    /**
     * 1天
     */
    ONE_DAY("one_day", "1天", 24 * 60, "/one_day"),
    /**
     * 3天
     */
    THREE_DAYS("three_days", "3天", 3 * 24 * 60, "/three_days"),
    ;

    /**
     * 编码
     */
    private String code;

    /**
     * 描述
     */
    private String description;

    /**
     * 有效期（单位:分钟）
     */
    private int validPeriod;

    /**
     * 目录
     */
    private String directory;

    FileValidPeriodEnum(String code, String description, int validPeriod, String directory) {
        this.code = code;
        this.description = description;
        this.validPeriod = validPeriod;
        this.directory = directory;
    }

    /**
     * 根据code查找实例
     *
     * @param code code
     * @return EnumStorageStatus
     */
    public static FileValidPeriodEnum find(String code) {
        for (FileValidPeriodEnum errorCode : FileValidPeriodEnum.values()) {
            if (errorCode.getCode().equals(code)) {
                return errorCode;
            }
        }
        return FileValidPeriodEnum.THIRTY_MINUTES;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public int getValidPeriod() {
        return validPeriod;
    }

    public String getDirectory() {
        return directory;
    }
}

