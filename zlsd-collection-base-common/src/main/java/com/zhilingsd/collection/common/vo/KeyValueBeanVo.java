package com.zhilingsd.collection.common.vo;

import java.io.Serializable;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: liushiyang
 * \* Date: 2018/3/20
 * \* Time: 18:27
 * \* To change this template use File | Settings | File Templates.
 * \* Description:
 * \
 */
public class KeyValueBeanVo implements Serializable{
    private static final long serialVersionUID = 8876184661040900899L;

    private Integer code;
    private String name;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public KeyValueBeanVo() {
    }

    public KeyValueBeanVo(Integer code, String name) {
        this.code = code;
        this.name = name;
    }
}