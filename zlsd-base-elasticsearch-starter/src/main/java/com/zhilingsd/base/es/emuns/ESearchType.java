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

    /*查询某个字段里含有某个关键词的文档,相当于mysql的=符号*/
    TERM,
    /*查询某个字段里含有某个关键词的文档,相当于mysql的!=符号*/
    TERM_NOT,
    /*查询某个字段里含有多个关键词的文档相当于mysql的in符号*/
    TERMS,

    NESTED_HIT_RULE_MEDIA,
    NESTED_HIT_RULE_TEXT,
    NESTED,
    FUZZY,
    /*must range*/
    RANGE,
    /*它和term区别可以理解为term是精确查询，这边match模糊查询；match会对传入值进行分词再匹配，然后term对认为这是一个单词*/
    MATCH,
    WILD_CARD,
    MUST_NOT_EXISTS;
}

