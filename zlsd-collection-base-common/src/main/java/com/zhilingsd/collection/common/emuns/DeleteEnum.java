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
package com.zhilingsd.collection.common.emuns;


/**
 * 
 * @author linguangliang
 * @version Id: DeleteEnum.java, v 0.1 2018年4月25日 下午4:50:04 linguangliang Exp $
 */
public enum DeleteEnum {
    /**0-否*/
    NO(0, "否"),
    /**1-是*/
    YES(1, "是");

    /** 状态码 **/
    private int code;
    /** 状态描述 **/
    private String description;

    /**
     * 私有构造方法
     * 
     * @param code
     *            编码
     * @param description
     *            描述
     **/
    private DeleteEnum(int code, String description) {
        this.code = code;
        this.description = description;
    }



    /**
     * Getter method for property <tt>code</tt>.
     * 
     * @return property value of code
     **/
    public int getCode() {
        return code;
    }

    /**
     * Getter method for property <tt>description</tt>.
     * 
     * @return property value of description
     **/
    public String getDescription() {
        return description;
    }
}
