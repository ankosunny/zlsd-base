package com.zhilingsd.collection.common.annotation;

import java.lang.annotation.*;

/**
 * 向外输出SingleResult注解
 * @author jacky
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface GetSingleResult {
}
