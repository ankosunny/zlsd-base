package com.zhilingsd.base.common.utils;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 对象复制
 *
 * @author liuzw
 */
@Slf4j
public class CopyDataUtil {

    private CopyDataUtil() {
    }


    /**
     * 复制List 例如：List<A> -> List<B>
     *
     * @param list  集合
     * @param clazz 目标对象
     * @return List<目标对象>
     */
    public static <T, V> List<V> copyList(List<T> list, Class<V> clazz) {
        List<V> data = Lists.newArrayList();
        if (list != null) {
            try {
                for (T obj : list) {
                    V dto = clazz.newInstance();
                    BeanUtils.copyProperties(obj, dto);
                    data.add(dto);
                }
            } catch (IllegalAccessException | InstantiationException e) {
                log.error("", e);
            }
            return data;
        } else {
            return new ArrayList<>();
        }
    }

}
