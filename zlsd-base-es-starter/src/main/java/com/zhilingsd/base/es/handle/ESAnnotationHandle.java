package com.zhilingsd.base.es.handle;

import com.zhilingsd.base.es.annotation.ESDocument;
import com.zhilingsd.base.es.annotation.ESearchField;
import com.zhilingsd.base.es.bo.ESRangeQueryBO;
import com.zhilingsd.base.es.emuns.ESFieldType;
import com.zhilingsd.base.es.emuns.ESearchType;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;


/**
 * @program: 智灵时代广州研发中心
 * @description:
 * @author: 吞星(yangguojun)
 * @create: 2020-02-26 21:40
 **/
public class ESAnnotationHandle {

    /**
     * 功能描述:获得ESDocument注解配置的prefix值
     *
     * @param: clazz
     * @return: java.lang.String
     * @auther: 吞星（yangguojun）
     * @date: 2020/2/28-14:37
     */
    public static String getPrefix(Class clazz) {
        Assert.notNull(clazz, "Class must not be null");
        if (clazz.isAnnotationPresent(ESDocument.class)) {
            Annotation annotation = clazz.getAnnotation(ESDocument.class);
            if (annotation instanceof ESDocument) {
                ESDocument esDocument = (ESDocument) annotation;
                return esDocument.prefix();
            }
        }
        return "";
    }


    /**
     * 功能描述:
     *
     * @param: field
     * @return: java.lang.String
     * @auther: 吞星（yangguojun）
     * @date: 2020/2/28-14:38
     */
    public static String getManualName(Field field) {
        Assert.notNull(field, "Field must not be null");
        if (field.isAnnotationPresent(ESearchField.class)) {
            String name = field.getAnnotation(ESearchField.class).value();
            return StringUtils.isNotBlank(name) ? name : field.getName();
        }
        return field.getName();
    }


    /**
     * 功能描述:获得es字段类型
     *
     * @param: field
     * @return: com.zhilingsd.qi.business.common.constants.ESFieldType
     * @auther: 吞星（yangguojun）
     * @date: 2020/2/28-15:16
     */
    public static ESFieldType getFieldType(Field field) {
        Assert.notNull(field, "Field must not be null");
        if (field.isAnnotationPresent(ESearchField.class)) {
            return field.getAnnotation(ESearchField.class).type();
        }
        return ESFieldType.Text;
    }

    /**
     * 功能描述 获得注解ESearchField的searchFiled字段值
     *
     * @param field
     * @return java.lang.String
     * @auther 吞星（yangguojun）
     * @date 2020/3/4-9:01
     */
    public static String getSearchField(Field field) {
        Assert.notNull(field, "Field must not be null");
        if (field.isAnnotationPresent(ESearchField.class)) {
            ESearchField annotation = field.getAnnotation(ESearchField.class);
            return annotation.searchField();
        }
        return null;
    }

    /**
     * 功能描述 获得注解ESearchField的ESearchType字段值
     *
     * @param field
     * @return java.lang.String
     * @auther 吞星（yangguojun）
     * @date 2020/3/4-9:01
     */
    public static ESearchType getESearchType(Field field) {
        Assert.notNull(field, "Field must not be null");
        if (field.isAnnotationPresent(ESearchField.class)) {
            ESearchField annotation = field.getAnnotation(ESearchField.class);
            ESearchType eSearchType = annotation.ESearchTypesearch();
            return eSearchType;
        }
        return null;
    }
}

