/**
 * Software License Declaration.
 *
 * wandaph.com, Co,. Ltd.
 * Copyright © 2017 All Rights Reserved.
 *
 * Copyright Notice
 * This documents is provided to wandaph contracting agent or authorized programmer only.
 * This source code is written and edited by wandaph Co,.Ltd Inc specially for financial
 * business contracting agent or authorized cooperative company, in order to help them to
 * install, programme or central control in certain project by themselves independently.
 *
 * Disclaimer
 * If this source code is needed by the one neither contracting agent nor authorized programmer
 * during the use of the code, should contact to wandaph Co,. Ltd Inc, and get the confirmation
 * and agreement of three departments managers  - Research Department, Marketing Department and
 * Production Department.Otherwise wandaph will charge the fee according to the programme itself.
 *
 * Any one,including contracting agent and authorized programmer,cannot share this code to
 * the third party without the agreement of wandaph. If Any problem cannot be solved in the
 * procedure of programming should be feedback to wandaph Co,. Ltd Inc in time, Thank you!
 */
package com.zhilingsd.base.common.utils.core;

import com.zhilingsd.base.common.emuns.ReturnCode;
import com.zhilingsd.base.common.exception.ServiceException;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.BeansException;
import org.springframework.beans.FatalBeanException;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.util.CollectionUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;


/**
 * 
 * @author linguangliang
 * @version Id: BeanUtils.java, v 0.1 2018年4月26日 上午9:20:16 linguangliang Exp $
 */
public class BeanUtils extends  org.springframework.beans.BeanUtils{


    /**
     * 功能描述 复制数组
     *
     * @param sourceList 原数组
     * @param clz  目标数组类型
     * @return java.util.List<T>
     * @auther 吞星（yangguojun）
     * @date 2020/3/17-18:39
     */
    public static <T> List<T> copyArray(Collection sourceList, Class<T> clz){
        if (CollectionUtils.isEmpty(sourceList)) {
            return Collections.EMPTY_LIST;
        }
        List<T> list = new ArrayList<>(sourceList.size());
        for (Object source : sourceList) {
            T target = null;
            try {
                target = clz.newInstance();
            } catch (Exception e) {
                throw new ServiceException(ReturnCode.SYSTEM_ERROR.getCode(),ReturnCode.SYSTEM_ERROR.getMsg()+e);
            }
            org.springframework.beans.BeanUtils.copyProperties(source, target);
            list.add(target);
        }

        return list;
    }

    /**
     * 功能描述 复制bean
     *
     * @param source 源对象
     * @param clz    目标对象类型
     * @return T
     * @auther 吞星（yangguojun）
     * @date 2020/3/17-18:38
     */
    public static <T> T copyBean(Object source, Class<T> clz){
        T target = null;
        try {
           target  = clz.newInstance();
            copyProperties(source, target, null, (String[]) null);
        } catch (Exception e) {
            throw new ServiceException(ReturnCode.SYSTEM_ERROR.getCode(),ReturnCode.SYSTEM_ERROR.getMsg()+e);
        }
        return target;
    }

    public static void copyProperties(Object source, Object target) throws BeansException {
        copyProperties(source, target, null, (String[]) null);
    }

    /**
     *
     * 功能描述:
     * @param: [source:源对象, target目标对象, editable, ignoreProperties：忽略不copy的字段]
     * @return: void
     * @auther: 吞星
     * @date: 2019/7/9-10:19
     */
    private static void copyProperties(Object source, Object target, Class<?> editable, String... ignoreProperties)
            throws BeansException {

        Assert.notNull(source, "Source must not be null");
        Assert.notNull(target, "Target must not be null");

        Class<?> actualEditable = target.getClass();
        if (editable != null) {
            if (!editable.isInstance(target)) {
                throw new IllegalArgumentException("Target class [" + target.getClass().getName() +
                        "] not assignable to Editable class [" + editable.getName() + "]");
            }
            actualEditable = editable;
        }
        PropertyDescriptor[] targetPds = getPropertyDescriptors(actualEditable);
        List<String> ignoreList = (ignoreProperties != null) ? Arrays.asList(ignoreProperties) : null;

        for (PropertyDescriptor targetPd : targetPds) {
            Method writeMethod = targetPd.getWriteMethod();
            if (writeMethod != null && (ignoreProperties == null || (!ignoreList.contains(targetPd.getName())))) {
                PropertyDescriptor sourcePd = getPropertyDescriptor(source.getClass(), targetPd.getName());
                if (sourcePd != null) {
                    Method readMethod = sourcePd.getReadMethod();
                    if (readMethod != null &&
                            ClassUtils.isAssignable(writeMethod.getParameterTypes()[0], readMethod.getReturnType())) {
                        try {
                            if (!Modifier.isPublic(readMethod.getDeclaringClass().getModifiers())) {
                                readMethod.setAccessible(true);
                            }
                            Object value = readMethod.invoke(source);
                            if(value != null){ //只拷贝不为null的属性
                                if (!Modifier.isPublic(writeMethod.getDeclaringClass().getModifiers())) {
                                    writeMethod.setAccessible(true);
                                }
                                writeMethod.invoke(target, value);
                            }
                        }
                        catch (Throwable ex) {
                            throw new FatalBeanException(
                                    "Could not copy property '" + targetPd.getName() + "' from source to target", ex);
                        }
                    }
                }
            }
        }
    }

    /**
     *
     * 功能描述:判断对象是否所有字段都为null,所以字段都为null就返回true,否则返回false
     * @param: [obj]
     * @return: boolean
     * @auther: 吞星
     * @date: 2019/7/9-10:31
     */
    public static boolean judgeBeanAllFieldIsNull(Object obj){
        boolean flag = true;
        for (Field field : obj.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            try {
                if (field.get(obj) != null) { //判断字段是否为空，并且对象属性中的基本都会转为对象类型来判断
                    flag=false;
                    return flag;
                }
            } catch (IllegalAccessException e) {
                throw new FatalBeanException(
                        "获得字段-->" + field.getName() + "' 值失败", e);
            }
        }
        return flag;
    }


    /**
     *
     * 功能描述:判断对象是否所有字段都为null(除了指定字段),就返回true,否则返回false
     * @param: [obj]
     * @return: boolean
     * @auther: 吞星
     * @date: 2019/7/9-10:31
     */
    public static boolean judgeBeanAllFieldIsNull(Object obj,String... fieldNames){
        boolean flag = true;
        for (Field field : obj.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            List<String> strings = Arrays.asList(fieldNames);
            if (!strings.contains(field.getName())){
                try {
                    if (field.get(obj) != null) { //判断字段是否为空，并且对象属性中的基本都会转为对象类型来判断
                        flag=false;
                        return flag;
                    }
                } catch (IllegalAccessException e) {
                    throw new FatalBeanException(
                            "获得字段-->" + field.getName() + "' 值失败", e);
                }
            }
        }
        return flag;
    }

    /**
     * 功能描述:该方法是用于相同对象不同属性值的合并，如果两个相同对象中同一属性都有值，
     *                    那么sourceBean中的值会覆盖tagetBean重点的值
     *
     * @param sourceBean
     * @param targetBean
     * @return java.lang.Object
     * @auther 吞星（yangguojun）
     * @date 2020/9/22-14:18
     */
    public static Object mergeTwoBean(Object sourceBean, Object targetBean) {
        Class sourceBeanClass = sourceBean.getClass();
        Field[] sourceFields = sourceBeanClass.getDeclaredFields();
        Field[] targetFields = sourceBeanClass.getDeclaredFields();
        for (int i = 0; i < sourceFields.length; i++) {
            Field sourceField = sourceFields[i];
            Field targetField = targetFields[i];
            sourceField.setAccessible(true);
            targetField.setAccessible(true);
            try {
                if (!(sourceField.get(sourceBean) == null)) {
                    targetField.set(targetBean, sourceField.get(sourceBean));
                }
            } catch (IllegalArgumentException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return targetBean;
    }

}
