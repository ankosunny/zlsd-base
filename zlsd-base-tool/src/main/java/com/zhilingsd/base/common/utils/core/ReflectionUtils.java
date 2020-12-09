/*
 * Decompiled with CFR 0.148.
 *
 * Could not load the following classes:
 *  org.apache.commons.beanutils.PropertyUtils
 *  org.apache.commons.lang3.StringUtils
 *  org.springframework.util.Assert
 *  org.springframework.util.CollectionUtils
 */
package com.zhilingsd.base.common.utils.core;

import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ReflectionUtils {


    public static Object getFieldValue(Object object, String fieldName) {
        Field field = ReflectionUtils.getDeclaredField(object, fieldName);
        if (field == null) {
            throw new IllegalArgumentException("Could not find field [" + fieldName + "] on target [" + object + "]");
        }
        ReflectionUtils.makeAccessible(field);
        Object result = null;
        try {
            result = field.get(object);
        } catch (IllegalAccessException e) {

        }
        return result;
    }

    public static void setFieldValue(Object target, String fname, Class ftype, Object fvalue) {
        block8:
        {
            if (target == null || fname == null || "".equals(fname) || fvalue != null && !ftype.isAssignableFrom(fvalue.getClass())) {
                return;
            }
            Class<?> clazz = target.getClass();
            try {
                Method method = clazz.getDeclaredMethod("set" + Character.toUpperCase(fname.charAt(0)) + fname.substring(1), ftype);
                if (!Modifier.isPublic(method.getModifiers())) {
                    method.setAccessible(true);
                }
                method.invoke(target, fvalue);
            } catch (Exception me) {
                try {
                    Field field = clazz.getDeclaredField(fname);
                    if (!Modifier.isPublic(field.getModifiers())) {
                        field.setAccessible(true);
                    }
                    field.set(target, fvalue);
                } catch (Exception fe) {

                }
            }
        }
    }

    public static void setFieldValue(Object object, String fieldName, Object value) {
        Field field = ReflectionUtils.getDeclaredField(object, fieldName);
        if (field == null) {
            throw new IllegalArgumentException("Could not find field [" + fieldName + "] on target [" + object + "]");
        }
        ReflectionUtils.makeAccessible(field);
        try {
            field.set(object, value);
        } catch (IllegalAccessException e) {

        }
    }

    public static Object invokeMethod(Object object, String methodName, Class<?>[] parameterTypes, Object[] parameters) throws InvocationTargetException {
        Method method = ReflectionUtils.getDeclaredMethod(object, methodName, parameterTypes);
        if (method == null) {
            throw new IllegalArgumentException("Could not find method [" + methodName + "] on target [" + object + "]");
        }
        method.setAccessible(true);
        try {
            return method.invoke(object, parameters);
        } catch (IllegalAccessException e) {

            return null;
        }
    }

    protected static Field getDeclaredField(Object object, String fieldName) {
        Assert.notNull((Object) object, (String) "object can not null");
        Assert.hasText((String) fieldName, (String) "fieldName");
        for (Class<?> superClass = object.getClass(); superClass != Object.class; superClass = superClass.getSuperclass()) {
            try {
                return superClass.getDeclaredField(fieldName);
            } catch (NoSuchFieldException noSuchFieldException) {
                continue;
            }
        }
        return null;
    }

    protected static void makeAccessible(Field field) {
        if (!Modifier.isPublic(field.getModifiers()) || !Modifier.isPublic(field.getDeclaringClass().getModifiers())) {
            field.setAccessible(true);
        }
    }

    protected static Method getDeclaredMethod(Object object, String methodName, Class<?>[] parameterTypes) {
        Assert.notNull((Object) object, (String) "object\u4e0d\u80fd\u4e3a\u7a7a");
        for (Class<?> superClass = object.getClass(); superClass != Object.class; superClass = superClass.getSuperclass()) {
            try {
                return superClass.getDeclaredMethod(methodName, parameterTypes);
            } catch (NoSuchMethodException noSuchMethodException) {
                continue;
            }
        }
        return null;
    }

    public static <T> Class<T> getSuperClassGenricType(Class clazz) {
        return ReflectionUtils.getSuperClassGenricType(clazz, 0);
    }

    public static Class getSuperClassGenricType(Class clazz, int index) {
        Type genType = clazz.getGenericSuperclass();
        if (!(genType instanceof ParameterizedType)) {
            return Object.class;
        }
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        if (index >= params.length || index < 0) {

            return Object.class;
        }
        if (!(params[index] instanceof Class)) {
            return Object.class;
        }
        return (Class) params[index];
    }

    public static List fetchElementPropertyToList(Collection collection, String propertyName) {
        ArrayList<Object> list = new ArrayList<Object>();
        try {
            for (Object obj : collection) {
                list.add(PropertyUtils.getProperty(obj, (String) propertyName));
            }
        } catch (Exception e) {
            ReflectionUtils.convertToUncheckedException(e);
        }
        return list;
    }

    public static void convertToUncheckedException(Exception e) throws IllegalArgumentException {
        if (e instanceof IllegalAccessException || e instanceof IllegalArgumentException || e instanceof NoSuchMethodException) {
            throw new IllegalArgumentException("Refelction Exception.", e);
        }
        throw new IllegalArgumentException(e);
    }

    public static <T> T newObject(Class<T> cls) throws InstantiationException, IllegalAccessException {
        return cls.newInstance();
    }

    public static <T> Class getParameterizedType(List<T> list) {
        if (CollectionUtils.isEmpty(list)) {
            return Object.class;
        }
        T t = list.get(0);
        return t.getClass();
    }

    public static Class getParameterizedType(Field field) {
        Type genericType = field.getGenericType();
        return ReflectionUtils.getParameterizedType(genericType);
    }

    public static Class getArrayParameterType(Field field) {
        return field.getType().getComponentType();
    }

    public static Class getFieldClazz(Field field) {
        return field.getType();
    }

    private static Class getParameterizedType(Type genericType) {
        if (genericType instanceof ParameterizedType) {
            ParameterizedType pt = (ParameterizedType) genericType;
            Class genericClazz = (Class) pt.getActualTypeArguments()[0];
            return genericClazz;
        }
        return Object.class;
    }
}

