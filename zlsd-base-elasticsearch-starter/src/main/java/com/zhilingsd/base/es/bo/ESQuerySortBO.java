package com.zhilingsd.base.es.bo;


import com.zhilingsd.base.es.emuns.ESFieldType;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.elasticsearch.search.sort.SortOrder;


/**
 * @program: 智灵时代广州研发中心
 * @description:
 * @author: 吞星(yangguojun)
 * @create: 2020-03-04 17:32
 **/
@Data
@AllArgsConstructor
public class ESQuerySortBO {

    /*排序字段,全路径*/
    private String sortFullField;

    /*排序字段类型*/
    private ESFieldType esFieldType;

    /*排序方式*/
    private SortOrder sortOrder;
}
