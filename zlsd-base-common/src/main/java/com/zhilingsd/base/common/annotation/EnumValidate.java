package com.zhilingsd.base.common.annotation;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.util.Objects;

/**
 * @author zhenxin
 * @program 广州智灵时代研发中心
 * @date 2021/8/20 11:08
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = {EnumValidate.EnumValidatorInner.class})
public @interface EnumValidate {
    /**
     * 必须的属性
     * 显示 校验信息
     * 利用 {} 获取 属性值，参考了官方的message编写方式
     *@see org.hibernate.validator 静态资源包里面 message 编写方式
     */
    String message() default "枚举值类型不存在";

    /**
     * 必须的属性
     * 用于分组校验
     */
    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    /**
     * 非必须
     */
    Class<? extends Enum<?>> enumClass();


    class EnumValidatorInner implements ConstraintValidator<EnumValidate, String> {
        private Class<? extends Enum<?>> enumClass;
        private Field codeField;

        @Override
        public void initialize(EnumValidate constraintAnnotation) {
            this.enumClass = constraintAnnotation.enumClass();

            try {
                codeField = enumClass.getDeclaredField("code");
                codeField.setAccessible(true);
            } catch (NoSuchFieldException e) {
                // pass
            }
        }

        /**
         * 校验逻辑的实现
         * @param value 需要校验的 值
         * @return 布尔值结果
         */
        @Override
        public boolean isValid(String value, ConstraintValidatorContext context) {
            if (value == null) {
                return true;
            }
            if("".equals(value)){
                return true;
            }
            for (Enum<?> anEnum : enumClass.getEnumConstants()) {
                if (codeField != null && compareCode(anEnum, value)) {
                    return true;
                }
                if (anEnum.name().equals(value)) {
                    return true;
                }
            }
            return false;
        }

        private boolean compareCode(Enum<?> anEnum, String value) {
            Object codeVal;
            try {
                codeVal = codeField.get(anEnum);
            } catch (IllegalAccessException e) {
                return false;
            }
            if (codeVal != null) {
                return Objects.equals(codeVal, value);
            }
            return false;
        }
    }
}
