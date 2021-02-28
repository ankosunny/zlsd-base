package com.zhilingsd.base.common.emuns;

/**
 * 引擎厂商枚举
 * @author yuboliang
 * @date 2020-3-5
 */
public enum EngineSupplierEnum {
    /**
     * 捷通华声
     */
    SINO_VOICE("sinoVoice", "捷通华声"),
    /**
     * 阿里云
     */
    ALI_YUN("aliYun", "阿里云"),
    /**
     * 科大讯飞
     */
    XF_YUN("xfYun", "科大讯飞"),

    KXJL_VOICE("kxjlVoice", "科讯嘉联"),

    ;

    private String code;
    private String description;

    EngineSupplierEnum(String code, String description) {
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

    public static EngineSupplierEnum find(String code) {
        for (EngineSupplierEnum item : EngineSupplierEnum.values()) {
            if (item.getCode().equals(code)) {
                return item;
            }
        }
        return null;
    }
}
