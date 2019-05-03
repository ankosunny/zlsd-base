package com.zhilingsd.base.common.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * 对象转换, 封装 BeanUtils.copyProperties
 *
 * @author zhangrong67
 * @version 1.0
 * @since 1.0 2017年12月13日 02:17
 */
public class BaseConvertor {
    public static <K> K convert(Object source, Class<K> clazz, String... ignoreProperties) {
        if (source == null) {
            return null;
        }
        K target = BeanUtils.instantiate(clazz);
        BeanUtils.copyProperties(source, target, ignoreProperties);
        return target;
    }

    public static <K> List<K> convertList(List<?> sources, Class<K> clazz, String... ignoreProperties) {
        if (sources == null) {
            return null;
        }
        List<K> results = new ArrayList<K>();
        for (Object source : sources) {
            K target = BeanUtils.instantiate(clazz);
            BeanUtils.copyProperties(source, target, ignoreProperties);
            results.add(target);
        }
        return results;
    }
}
