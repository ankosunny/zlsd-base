package com.zhilingsd.base.es.bo;

import com.google.common.collect.Maps;
import lombok.Data;

import java.util.Map;

/**
 * @program: 智灵时代广州研发中心
 * @description: 分组聚合结果BO
 * @author: 吞星(yangguojun)
 * @create: 2021-09-10 10:53
 **/
@Data
public class GroupAggregateResultBO {
    /**
     * 返回buckets（桶）的数量
     */
    private Integer total = 0;

    //返回桶的偏移量
    Map<String, Object> afterKeyMap = Maps.newHashMap();

    //分组结果
    Map<String, Object> groupMap = Maps.newHashMap();

    //分组后聚合结果
    Map<String, Double> resultMap = Maps.newHashMap();
}
