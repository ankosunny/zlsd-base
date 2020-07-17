/*
 * Decompiled with CFR 0.148.
 */
package com.zhilingsd.base.es.annotation;

import com.zhilingsd.base.es.emuns.ESFieldType;
import com.zhilingsd.base.es.emuns.ESearchType;

import java.lang.annotation.*;

/**
 * @program: 智灵时代广州研发中心
 * @description:
 * @author: 吞星(yangguojun)
 * @create: 2020-02-26 21:41
 **/
@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = {ElementType.FIELD})
@Documented
@Inherited
public @interface ESearchField {

    String value() default "";

    String description() default "";

    ESFieldType type() default ESFieldType.Keyword;

    //表示es字段查询全路径
    String searchField() default "";

    //表示es的查询类型
    ESearchType ESearchTypesearch() default ESearchType.TERM;


}

