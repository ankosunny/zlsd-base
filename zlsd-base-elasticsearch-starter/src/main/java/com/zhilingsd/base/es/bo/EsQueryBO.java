package com.zhilingsd.base.es.bo;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @program: 智灵时代广州研发中心
 * @description:
 * @author: 吞星(yangguojun)
 * @create: 2020-03-03 17:52
 **/
@Data
public class EsQueryBO extends ESQueryBasicBO {

    /*第几页,如果不分页，请不要设置值*/
    Integer pageIndex = null;
    /*页大小,如果不分页，请不要设置值*/
    Integer pageSize = null;

    private String[] includes = new String[]{"aggregations"};
    private String[] excludes = new String[]{"_source"};

    /*
     *  聚合分组查询  agg group
     *  key：分组字段别名；value：分组字段全路径
     */
    private Map<String, Object> aggGroupFieldMap;

    /**
     * 针对composite分组字段查询的结果，再嵌套一个桶聚合，如果不想嵌套分组，就不设置值
     * 比如，compose对学校，班级分组之后，得到每个学校班级数，如果还想得到班级下小组个数，就可以这这里加上小组来个大桶套小桶，得到结果
     * key：分组字段别名；value：分组字段全路径
     */
    private Map<String, Object> aggGroupSubFieldMap;


    /*
     * 聚合查询  count sum  max  min
     * key:AggreatinEnum枚举的值;value:字段的全路径
     * 为什么是list，因为可能多个字段需要求metrix。比如有多个字段，都需要进行sum统计
     */
    Map<String, List<String>> aggMetricMap;


    /*统计总数*/
    private Boolean needCount = false;
}
