package com.zhilingsd.base.es.bo;

import lombok.Data;

/**
 * @program: 智灵时代广州研发中心
 * @description:
 * @author: 吞星(yangguojun)
 * @create: 2020-03-03 15:56
 **/
@Data
public class ESNormalQueryBO extends ESQueryBasicBO {

    /*返回值类型*/
    Class clazz;

    /*返回的记录数*/
    Integer size = 10;

    /**
     * 返回指定字段
     * */
    private String[] includes = new String[0];
    /**
     * 返回排除指定字段
     * */
    private String[] excludes = new String[0];
}
