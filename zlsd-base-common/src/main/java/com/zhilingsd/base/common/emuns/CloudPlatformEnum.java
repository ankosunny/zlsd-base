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
    MERCHANT("merchant", "商户管理中心"),
    /**
     * AI语音机器人
     */
    AI("ai", "AI语音机器人"),
    /**
     * 质检
     */
    QUALITY("quality", "质检"),
    /**
     * 培训
     */
    TRAIN("train", "培训"),
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
}
