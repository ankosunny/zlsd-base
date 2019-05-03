package com.zhilingsd.base.common.emuns;

/**
 * 是否
 * 
 * @author yangtao20815
 * @version $Id: EnumBool.java, v 1.0 2017年3月8日 上午9:16:38 yangtao20815 Exp $
 */
public enum EnumBool {
    /**0-否*/
    NO("0", "否"),
    /**1-是*/
    YES("1", "是");

    /** 状态码 **/
    private String code;
    /** 状态描述 **/
    private String description;

    /**
     * 私有构造方法
     * 
     * @param code
     *            编码
     * @param description
     *            描述
     **/
    private EnumBool(String code, String description) {
        this.code = code;
        this.description = description;
    }

    /**
     * 根据编码查找枚举
     * 
     * @param code
     *            编码
     * @return {@link EnumBool} 实例
     **/
    public static EnumBool find(String code) {
        for (EnumBool frs : EnumBool.values()) {
            if (frs.getCode().equals(code)) {
                return frs;
            }
        }
        return null;
    }

    /**
     * Getter method for property <tt>code</tt>.
     * 
     * @return property value of code
     **/
    public String getCode() {
        return code;
    }

    /**
     * Getter method for property <tt>description</tt>.
     * 
     * @return property value of description
     **/
    public String getDescription() {
        return description;
    }
}
