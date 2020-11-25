package com.zhilingsd.base.es.core.annotation;

import java.lang.annotation.*;

/**
 * @author linmenghuai
 * @version 1.0
 * @desc
 * @date 2020/9/25 14:59
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE })
public @interface Document {

    String indexName();

    short shards() default 5;

    short replicas() default 1;

    String refreshInterval() default "1s";

    boolean createIndex() default true;

    boolean mappingDynamic() default false;

    int maxResultWindow() default 100_000;
}
