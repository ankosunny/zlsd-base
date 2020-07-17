package com.zhilingsd.base.es.bo;

import lombok.Data;
import org.elasticsearch.search.aggregations.bucket.histogram.DateHistogramInterval;

import java.util.TimeZone;

/**
 * @program: 智灵时代广州研发中心
 * @description:
 * @author: 吞星(yangguojun)
 * @create: 2020-03-03 15:56
 **/
@Data
public class EsDateHistogramBO {

    //字段名
    private String esField;

    //时间分组维度
    private DateHistogramInterval dateHistogramInterval;

    //时区
    private TimeZone timeZone;

    //格式化 -暂未确定对Long类型是否有效
    private String formart;


}
