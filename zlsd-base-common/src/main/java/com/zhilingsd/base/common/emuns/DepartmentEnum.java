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
package com.zhilingsd.base.common.emuns;

public enum DepartmentEnum {

	/**
	 * 部门主兼职-主部门 MASTER -兼职部门 SLAVE
	 */
	MASTER("1", "master"),

	SLAVE("0", "slave");

	private String code;

	private String value;

	DepartmentEnum(String code, String value) {
		this.code = code;
		this.value = value;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public static String getValue(String code) {
		for (DepartmentEnum gender : DepartmentEnum.values()) {
			if (gender.getCode().equals(code)) {
				return gender.getValue();
			}
		}
		return null;
	}

	public static String getCode(String value) {
		for (DepartmentEnum gender : DepartmentEnum.values()) {
			if (gender.getValue().equals(value)) {
				return gender.getCode();
			}
		}
		return null;
	}
}
