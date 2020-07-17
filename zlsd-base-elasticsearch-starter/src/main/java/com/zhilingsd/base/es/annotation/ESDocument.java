/*
 * Decompiled with CFR 0.148.
 */
package com.zhilingsd.base.es.annotation;

import java.lang.annotation.*;

/**
 * @program: 智灵时代广州研发中心
 * @description:
 * @author: 吞星(yangguojun)
 * @create: 2020-02-26 21:41
 **/
@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = {ElementType.TYPE})
@Documented
@Inherited
public @interface ESDocument {

    String prefix() default "";

    String index() default "index";

    String description() default "";

}

