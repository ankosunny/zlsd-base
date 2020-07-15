package com.zhilingsd.base.es.bo;

import com.zhilingsd.base.es.handle.ESAnnotationHandle;
import lombok.Data;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: 智灵时代广州研发中心
 * @description:
 * @author: 吞星(yangguojun)
 * @create: 2020-03-03 15:56
 **/
@Data
public class ESPageQueryBO {

    /*第几页,如果不分页，请不要设置值*/
    Integer pageIndex =null;
    /*页大小,如果不分页，请不要设置值*/
    Integer pageSize =null;

    /*查询条件*/
    Map<String, ESQueryOperationBO> query;
    /*排序字段集合*/
    List<ESQuerySortBO> esQuerySortBO;
    /*返回值类型*/
    Class clazz;
    /*索引名数组*/
    String[] indexNames;
    /*type数组*/
    List[] types;

}
