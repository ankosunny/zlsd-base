package com.zhilingsd.base.common.annotation;

import java.lang.annotation.*;

/**
 * 向外输出SingleResult注解
 * @author 吞星
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface GetSingleResult {
}
