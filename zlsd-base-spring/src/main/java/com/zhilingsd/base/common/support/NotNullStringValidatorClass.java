package com.zhilingsd.base.common.support;

import com.zhilingsd.base.common.annotation.NotNullString;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @program: 智灵时代广州研发中心
 * @description:
 * @author: 吞星(yangguojun)
 * @create: 2020-03-06 11:07
 **/
public class NotNullStringValidatorClass implements ConstraintValidator<NotNullString, CharSequence> {



    @Override
    public void initialize(NotNullString constraintAnnotation) {
    }


    @Override
    public boolean isValid(CharSequence charSequence, ConstraintValidatorContext context) {
        if (charSequence == null) {
            return true;
        }

        return charSequence.toString().trim().length() > 0;

    }
}
