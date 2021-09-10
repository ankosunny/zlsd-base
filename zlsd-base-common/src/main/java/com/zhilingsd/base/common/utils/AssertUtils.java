package com.zhilingsd.base.common.utils;

import com.zhilingsd.base.common.exception.BusinessException;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Collection;

/**
 * @author zhenxin
 * @program 广州智灵时代研发中心
 * @date 2021/6/17 14:56
 */
public class AssertUtils {

    public static void notNull(Object object, String message) {
        if (object == null) {
            throw new BusinessException(message);
        }
    }

    public static void notEmpty(String object, String message) {
        if (StringUtils.isEmpty(object)) {
            throw new BusinessException(message);
        }
    }

    public static <T> void notEmpty(Collection<T> object, String message) {
        if (CollectionUtils.isEmpty(object)) {
            throw new BusinessException(message);
        }
    }

    public static <T> void isTrue(Boolean object, String message) {
        if (Boolean.FALSE.equals(object)) {
            throw new BusinessException(message);
        }
    }

}
