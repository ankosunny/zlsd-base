/**
 * Software License Declaration.
 * <p>
 * zhilingsd.com, Co,. Ltd.
 * Copyright © 2016 All Rights Reserved.
 * <p>
 * Copyright Notice
 * This documents is provided to zhilingsd contracting agent or authorized programmer only.
 * This source code is written and edited by zhilingsd Co,.Ltd Inc specially for financial
 * business contracting agent or authorized cooperative company, in order to help them to
 * install, programme or central control in certain project by themselves independently.
 * <p>
 * Disclaimer
 * If this source code is needed by the one neither contracting agent nor authorized programmer
 * during the use of the code, should contact to zhilingsd Co,. Ltd Inc, and get the confirmation
 * and agreement of three departments managers  - Research Department, Marketing Department and
 * Production Department.Otherwise zhilingsd will charge the fee according to the programme itself.
 * <p>
 * Any one,including contracting agent and authorized programmer,cannot share this code to
 * the third party without the agreement of zhilingsd. If Any problem cannot be solved in the
 * procedure of programming should be feedback to zhilingsd Co,. Ltd Inc in time, Thank you!
 */
package com.zhilingsd.base.es.core.annotation;

import java.lang.reflect.Field;

import org.springframework.util.Assert;

/**
 * @className ESAnnotationHandle.java
 * @description //TODO
 * @author linmenghuai
 * @version 1.0
 * @date 2020/9/27 9:38
 */
public class ESAnnotationHandle {

    public static String getFieldName(Field ESField) {
        Assert.notNull(ESField, "ESField must not be null");
        return ESField.getName();
    }

    public static String getSearchFieldName(Field eSearchField) {
        Assert.notNull(eSearchField, "eSearchField must not be null");
        if (eSearchField.isAnnotationPresent(ESearchField.class)) {
            return eSearchField.getAnnotation(ESearchField.class).fullName();
        }
        return "";
    }


    public static FieldType getFieldType(Field ESField) {
        Assert.notNull(ESField, "ESField must not be null");
        if (ESField.isAnnotationPresent(ESField.class)) {
            return ESField.getAnnotation(ESField.class).type();
        }
        return FieldType.Keyword;
    }

    public static ESearchType getSearchFieldType(Field ESField) {
        Assert.notNull(ESField, "ESField must not be null");
        if (ESField.isAnnotationPresent(ESearchField.class)) {
            return ESField.getAnnotation(ESearchField.class).searchType();
        }
        return ESearchType.TERM;
    }

    public static Boolean isIndex(Field ESField) {
        Assert.notNull(ESField, "ESField must not be null");
        if (ESField.isAnnotationPresent(ESField.class)) {
            return ESField.getAnnotation(ESField.class).index();
        }
        return true;
    }

    public static Boolean isStore(Field ESField) {
        Assert.notNull(ESField, "ESField must not be null");
        if (ESField.isAnnotationPresent(ESField.class)) {
            return ESField.getAnnotation(ESField.class).store();
        }
        return false;
    }

//    public static Boolean isFielddata(Field ESField) {
//        Assert.notNull(ESField, "ESField must not be null");
//        if (ESField.isAnnotationPresent(ESField.class)) {
//            return ESField.getAnnotation(ESField.class).fielddata();
//        }
//        return false;
//    }
}