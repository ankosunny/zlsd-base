package com.zhilingsd.base.es.bo;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @program: 智灵时代广州研发中心
 * @description:
 * @author: 吞星(yangguojun)
 * @create: 2020-03-03 15:56
 **/
@Data
public class ESQueryBasicBO {

    /*查询条件 key:esFullField;value:ESQueryField*/
    Map<String, ESQueryField> queryFieldMap;
    /*排序字段集合*/
    List<ESQuerySortBO> esQuerySortBO;

    /*索引名数组*/
    String[] indexNames;

}
