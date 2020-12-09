package com.zhilingsd.base.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 机器人服务接口统一处理
 *
 * @author linmenghuai
 * @date 2019-4-29 20:16:41
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface TechFacade {

    boolean needPrintArgs() default true;

    boolean needLogin() default true;
}
