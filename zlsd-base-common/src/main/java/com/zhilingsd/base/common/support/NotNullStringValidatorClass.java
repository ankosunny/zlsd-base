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


    /**
     * Initializes the validator in preparation for
     * {@link #isValid(Object, ConstraintValidatorContext)} calls.
     * The constraint annotation for a given constraint declaration
     * is passed.
     * <p/>
     * This method is guaranteed to be called before any use of this instance for
     * validation.
     *
     * @param constraintAnnotation annotation instance for a given constraint declaration
     */
    @Override
    public void initialize(NotNullString constraintAnnotation) {
    }

    /**
     * Implements the validation logic.
     * The state of {@code value} must not be altered.
     * <p/>
     * This method can be accessed concurrently, thread-safety must be ensured
     * by the implementation.
     *
     * @param charSequence   object to validate
     * @param context context in which the constraint is evaluated
     * @return {@code false} if {@code value} does not pass the constraint
     */
    @Override
    public boolean isValid(CharSequence charSequence, ConstraintValidatorContext context) {
        if ( charSequence == null ) {
            return true;
        }

        return charSequence.toString().trim().length() > 0;

    }
}
