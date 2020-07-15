/*
 * Decompiled with CFR 0.148.
 */
package com.zhilingsd.base.es.emuns;

/**
 * @program: 智灵时代广州研发中心
 * @description:
 * @author: 吞星(yangguojun)
 * @create: 2020-02-26 21:41
 **/
public enum ESFieldType {
    Text("text"),
    Integer("integer"),
    Long("long"),
    Date("date"),
    Float("float"),
    Double("double"),
    Boolean("boolean"),
    Object("object"),
    Nested("nested"),
    Ip("ip"),
    Attachment("attachment"),
    Keyword("keyword");

    private String code;

    private ESFieldType(String code) {
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}

