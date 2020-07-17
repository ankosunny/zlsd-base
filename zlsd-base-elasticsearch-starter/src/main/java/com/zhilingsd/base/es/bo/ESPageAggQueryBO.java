package com.zhilingsd.base.es.bo;

import com.google.common.collect.Maps;
import lombok.Data;

import java.util.Map;

/**
 * @program: 智灵时代广州研发中心
 * @description:
 * @author: 吞星(yangguojun)
 * @create: 2020-03-03 15:56
 **/
@Data
public class ESPageAggQueryBO {
    Integer pageIndex;
    Integer pageSize;
    //条件
    Map<String, Object> query = Maps.newHashMap();
    Class clazz;
    String[] indexNames;
    String[] types;
    //聚合(聚合count,sum) value 字段
    Map<String,String> agg = Maps.newHashMap();
    //时间范围
    Map<String, Map<String, Object>> rangeMap = Maps.newHashMap();
    String order;

}
