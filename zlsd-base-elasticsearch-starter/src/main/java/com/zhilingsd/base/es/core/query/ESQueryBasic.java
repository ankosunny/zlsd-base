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

import java.util.List;

import lombok.Data;

/**
 * @className ESQueryBasic.java
 * @description //TODO
 * @author linmenghuai
 * @version 1.0
 * @date 2020/9/27 11:17
 */
@Data
public class ESQueryBasic {


    List<ESQuerySort> esQuerySort;

    String[] indexNames;
    /**
     * 返回数量
     * */
    Integer defaultSize = 10_000;

    SourceFilter sourceFilter;


}