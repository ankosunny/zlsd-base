package com.zhilingsd.base.common.annotation;

/**
 * @program: 智灵时代广州研发中心
 * @description:
 * @author: 吞星(yangguojun)
 * @create: 2020-03-06 11:05
 **/

import com.zhilingsd.base.common.support.NotNullStringValidatorClass;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER, ElementType.FIELD})
@Constraint(validatedBy = NotNullStringValidatorClass.class)
public @interface NotNullString {

    String message() default "{入参不能包含空字符串，请检查}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
