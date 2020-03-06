package com.zhilingsd.base.common.emuns;

/**
 * 云平台
 * @author: yuboliang
 * @date: 2020/2/29
 **/
public enum CloudPlatformEnum {
    /**
     * 商户管理中心
     */
    MERCHANT("merchant", "管理中心"),
    /**
     * AI语音机器人
     */
    AI("ai", "AI语音系统"),
    /**
     * 质检
     */
    QUALITY("quality", "智能质检系统"),
    /**
     * 培训
     */
    TRAIN("train", "智能培训系统"),
    ;

    private String code;
    private String description;

    CloudPlatformEnum(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public static CloudPlatformEnum find(String code) {
        for (CloudPlatformEnum item : CloudPlatformEnum.values()) {
            if (item.getCode().equals(code)) {
                return item;
            }
        }
        return null;
    }
}
