package com.zhilingsd.base.es.core.annotation;

import java.lang.annotation.*;

/**
 * @author linmenghuai
 * @version 1.0
 * @desc
 * @date 2020/9/25 15:19
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Documented
@Inherited
public @interface ESField {

    FieldType type() default FieldType.Keyword;

    boolean index() default true;

    boolean store() default false;

//    boolean fielddata() default false;

//    String searchAnalyzer() default "";
//
//    String analyzer() default "";
//
//    String normalizer() default "";
//
//    String[] ignoreFields() default {};

}
