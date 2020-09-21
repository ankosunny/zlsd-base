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
public enum ESearchType {
    /*must term*/
    TERM,
    /*mustNot term*/
    TERM_NOT,
    /*must terms*/
    TERMS,
    NESTED_HIT_RULE_MEDIA,
    NESTED_HIT_RULE_TEXT,
    NESTED,
    FUZZY,
    /*must range*/
    RANGE,
    MATCH,
    WILD_CARD,
    MUST_NOT_EXISTS;
}

