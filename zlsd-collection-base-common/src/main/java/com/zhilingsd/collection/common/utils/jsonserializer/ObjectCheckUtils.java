package com.zhilingsd.collection.common.utils.jsonserializer;

import org.apache.commons.lang3.ArrayUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.Map;

/**
 * @program zhilingsd-collection
 * @description: 对象属性判断
 * @author: LiuHang
 * @create: 2019/04/24 20:30
 * @Copyright: 2018 www.mujinkeji.com Inc. All rights reserved.
 */

public class ObjectCheckUtils {
    /**
     * 判断obj对象所有属性是否为空（除去static final类型）
     * @param obj
     * @return
     */
    public static boolean objFieldsIsEmpty(Object obj) throws IllegalAccessException {
        for(Field f : getFields(obj)){
            f.setAccessible(true);
            if(Modifier.isFinal(f.getModifiers())&&Modifier.isStatic(f.getModifiers())){
                continue;
            }
            if(!isEmpty(f.get(obj))){
                f.setAccessible(false);
                return false;
            }
            f.setAccessible(false);
        }
        return true;
    }
    /**
     * 判断obj对象某个fileName属性是否为空
     * @param obj
     * @return
     */
    public static boolean objFieldIsEmpty(Object obj,String fileName) throws IllegalAccessException {
        Field f = getFieldByFieldName(obj,fileName);
        f.setAccessible(true);
        Object value = f.get(obj);
        f.setAccessible(false);
        if(isEmpty(value)) {
            return true;
        }
        return false;
    }
    /**
     * 判断obj对象多个fileName属性是否都为空
     * @param obj
     * @return
     */
    public static boolean objFieldIsEmpty(Object obj,String ... fileName) throws IllegalAccessException {
        boolean res = true;
        for(String file:fileName){
            if(!objFieldIsEmpty(obj,file)){
                res = false;
            }
        }
        return res;
    }
    /**
     * 获取obj对象fieldName的Field
     * @param obj
     * @param fieldName
     * @return
     */
    public static Field getFieldByFieldName(Object obj, String fieldName) {
        for (Class<?> superClass = obj.getClass(); superClass != Object.class; superClass = superClass
                .getSuperclass()) {
            try {
                return superClass.getDeclaredField(fieldName);
            } catch (NoSuchFieldException e) {
            }
        }
        return null;
    }
    /**
     * 获取obj对象所有的Field(包括父类)
     * @param obj
     * @return
     */
    public static Field[] getFields(Object obj) {
        Field[] result = new Field[]{};
        Field[] second;
        for (Class<?> superClass = obj.getClass(); superClass != Object.class; superClass = superClass
                .getSuperclass()) {
            second = superClass.getDeclaredFields();
            result = ArrayUtils.addAll(result, second);
        }
        return result;
    }
    /**
     * 判断obj对象是否为空
     * @param obj
     * @return
     */
    @SuppressWarnings("rawtypes")
    public static boolean isEmpty(Object obj) {
        if (obj == null) {
            return true;

        } else if (obj instanceof String && (obj.toString().trim().equals(""))) {
            return true;

        } else if (obj instanceof Number && ((Number) obj).doubleValue() < 0) {
            return true;

        } else if (obj instanceof Collection && ((Collection) obj).isEmpty()) {
            return true;

        } else if (obj instanceof Map && ((Map) obj).isEmpty()) {
            return true;

        } else if (obj instanceof Object[] && ((Object[]) obj).length == 0) {
            return true;

        }
        return false;
    }
}
