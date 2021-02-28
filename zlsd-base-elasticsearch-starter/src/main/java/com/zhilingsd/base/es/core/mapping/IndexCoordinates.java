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
package com.zhilingsd.base.es.core.mapping;

import java.util.Arrays;

import org.elasticsearch.action.admin.indices.alias.Alias;
import org.springframework.util.Assert;

/**
 * @className IndexCoordinates.java
 * @description //TODO
 * @author linmenghuai
 * @version 1.0
 * @date 2020/9/27 17:20
 */

public class IndexCoordinates {
    public static final String TYPE = "_doc";

    private final String[] indexNames;

    private Alias alias;

    /**
     * 返回值类型
     * */
    private Class clazz;

    public static IndexCoordinates of(String... indexNames) {
        Assert.notNull(indexNames, "indexNames must not be null");
        return new IndexCoordinates(indexNames);
    }

    private IndexCoordinates(String[] indexNames) {
        Assert.notEmpty(indexNames, "indexNames may not be null or empty");
        this.indexNames = indexNames;
    }

    public Alias getAlias() {
        return alias;
    }

    public void setAlias(Alias alias) {
        this.alias = alias;
    }

    public Class getClazz() {
        return clazz;
    }

    public void setClazz(Class clazz) {
        this.clazz = clazz;
    }

    public String getIndexName() {
        return indexNames[0];
    }

    public String[] getIndexNames() {
        return Arrays.copyOf(indexNames, indexNames.length);
    }

    @Override
    public String toString() {
        return "IndexCoordinates{" + "indexNames=" + Arrays.toString(indexNames) + '}';
    }
}