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
package com.zhilingsd.base.es.core.query;

import lombok.Data;

/**
 * @className Pageable.java
 * @description 分页插件
 * @author linmenghuai
 * @version 1.0
 * @date 2020/9/27 15:40
 */
@Data
public class Pageable {

    boolean isPaged = true;

    /**
     * 第几页
     * */
    int pageNumber;
    /**
     * 每页大小
     * */
    int pageSize;

    /**
     * 偏移量起点
     * */
    int offset;

   // Sort getSort();

//    default Sort getSortOr(Sort sort) {
//        Assert.notNull(sort, "Fallback Sort must not be null!");
//        return this.getSort().isSorted() ? this.getSort() : sort;
//    }

//    Pageable next;

//    Pageable first();

    boolean previous;

}