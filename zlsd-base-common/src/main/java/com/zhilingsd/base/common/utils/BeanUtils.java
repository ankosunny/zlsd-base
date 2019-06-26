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
package com.zhilingsd.base.common.utils;

import com.zhilingsd.base.common.emuns.ReturnCode;
import com.zhilingsd.base.common.exception.ServiceException;
import org.springframework.beans.BeansException;
import org.springframework.beans.FatalBeanException;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.util.CollectionUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;


/**
 * 
 * @author linguangliang
 * @version Id: BeanUtils.java, v 0.1 2018年4月26日 上午9:20:16 linguangliang Exp $
 */
public class BeanUtils extends  org.springframework.beans.BeanUtils{


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
                            if(value != null){          //只拷贝不为null的属性 by zhao
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
    
}
