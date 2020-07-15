/*
 * Decompiled with CFR 0.148.
 *
 * Could not load the following classes:
 *  org.elasticsearch.common.xcontent.XContentBuilder
 *  org.elasticsearch.common.xcontent.XContentFactory
 *  org.springframework.stereotype.Component
 *  org.springframework.util.Assert
 */
package com.zhilingsd.base.es.handle;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zhilingsd.base.common.exception.BusinessException;
import com.zhilingsd.base.common.utils.BeanUtils;
import com.zhilingsd.base.common.utils.DateUtil;
import com.zhilingsd.base.common.utils.ReflectionUtils;
import com.zhilingsd.base.es.bo.*;
import com.zhilingsd.base.es.client.ElasticSearchClientConfig;
import com.zhilingsd.base.es.emuns.AggreatinEnum;
import com.zhilingsd.base.es.emuns.ESFieldType;
import com.zhilingsd.base.es.emuns.ESReturnCode;
import com.zhilingsd.base.es.emuns.ESearchType;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.lucene.search.join.ScoreMode;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.WriteRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.elasticsearch.index.reindex.DeleteByQueryRequest;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.AbstractAggregationBuilder;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.nested.NestedAggregationBuilder;
import org.elasticsearch.search.aggregations.bucket.nested.ParsedNested;
import org.elasticsearch.search.aggregations.bucket.terms.ParsedTerms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.metrics.sum.ParsedSum;
import org.elasticsearch.search.aggregations.metrics.valuecount.ParsedValueCount;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.*;

/**
 * @program: 智灵时代广州研发中心
 * @description:
 * @author: 吞星(yangguojun)
 * @create: 2020-02-26 21:40
 **/
@Slf4j
public class ElasticsearchHandle {

    public static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String PROPERTIES = "properties";
    public static final String TYPE = "type";
    public static final String FORMAT = "format";


    /**
     * 功能描述:构建es建index所用的builder
     *
     * @param: clazz
     * @return: org.elasticsearch.common.xcontent.XContentBuilder
     * @auther: 吞星（yangguojun）
     * @date: 2020/2/28-15:36
     */
    public XContentBuilder getIndexBuilder(Class clazz) throws IOException {
        Assert.notNull(clazz, "Class must not be null");
        XContentBuilder xContentBuilder = XContentFactory.jsonBuilder();
        xContentBuilder.startObject();
        // 构建字段映射
        xContentBuilder.startObject(PROPERTIES);
        xContentBuilder = resolveEntity(clazz, xContentBuilder);
        xContentBuilder.endObject();
        xContentBuilder.endArray();
        xContentBuilder.endObject();
        return xContentBuilder;
    }

    /**
     * 功能描述:通过解析实体，来构建XContentBuilder
     *
     * @param: clazz
     * @param: builder
     * @return: org.elasticsearch.common.xcontent.XContentBuilder
     * @auther: 吞星（yangguojun）
     * @date: 2020/2/28-15:37
     */
    private XContentBuilder resolveEntity(Class clazz, XContentBuilder builder) throws IOException {
        Field[] declaredFields = clazz.getDeclaredFields();
        for (Field javaField : declaredFields) {
            Class<?> javaFieldType = javaField.getType();
            String esFileName = ESAnnotationHandle.getManualName(javaField);
            ESFieldType esFieldType = ESAnnotationHandle.getFieldType(javaField);
            String esFieldTypeCode = esFieldType.getCode();
            builder.startObject(esFileName).field(TYPE, esFieldTypeCode);
            switch (esFieldType) {
                case Object: {
                    builder.startObject(PROPERTIES);
                    if (Collection.class.isAssignableFrom(javaFieldType)) {
                        builder = resolveCollection(javaField, builder);
                    } else {
                        builder = resolveEntity(javaFieldType, builder);
                    }
                    builder.endObject().endObject();
                    continue;
                }
                case Nested: {
                    builder.startObject(PROPERTIES);
                    if (Collection.class.isAssignableFrom(javaFieldType)) {
                        builder = resolveCollection(javaField, builder);
                    } else if (Object.class.isAssignableFrom(javaFieldType)) {
                        builder = resolveEntity(javaFieldType, builder);
                    }
                    builder.endObject().endObject();
                    continue;
                }
                default: {
                    if (ESAnnotationHandle.getFieldType(javaField).equals(ESFieldType.Date)) {
                        builder.field(FORMAT, DATE_FORMAT);
                    }
                    builder.endObject();
                    continue;
                }
            }
        }
        return builder;
    }


    private XContentBuilder resolveCollection(Field field, XContentBuilder builder) throws IOException {
        Class parameterizedType = ReflectionUtils.getParameterizedType(field);
        return this.resolveNested(parameterizedType, builder);
    }

    private XContentBuilder resolveNested(Class type, XContentBuilder builder) throws IOException {
        return this.resolveEntity(type, builder);
    }







    public SearchSourceBuilder builderEsQuery(ESPageQueryBO esPageQueryBO) {
        Map<String, ESQueryOperationBO> query = esPageQueryBO.getQuery();
        Integer pageIndex = esPageQueryBO.getPageIndex();
        Integer pageSize = esPageQueryBO.getPageSize();
        if (query != null && !query.keySet().isEmpty()) {
            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
            if (pageIndex != null && pageSize != null) {
                searchSourceBuilder.size(pageSize);
                if (pageIndex <= 0) {
                    pageIndex = 0;
                }
                searchSourceBuilder.from((pageIndex - 1) * pageSize);
                searchSourceBuilder.size(pageSize);
            }
            List<ESQuerySortBO> esQuerySortBOs = esPageQueryBO.getEsQuerySortBO();
            if (CollectionUtils.isNotEmpty(esQuerySortBOs)) {
                esQuerySortBOs.forEach(value -> searchSourceBuilder.sort(SortBuilders.fieldSort(value.getSortFullField()).unmappedType(value.getEsFieldType().getCode()).order(value.getSortOrder())));
            }
            BoolQueryBuilder boolBuilder = QueryBuilders.boolQuery();
            if (MapUtils.isNotEmpty(query)) {
                query.keySet().forEach(key -> {
                    ESQueryOperationBO bo = query.get(key);
                    builderFieldQuery(key, bo, boolBuilder);
                });
            }
            searchSourceBuilder.query(boolBuilder);
            return searchSourceBuilder;
        }
        return new SearchSourceBuilder();
    }

    private void builderFieldQuery(String key, ESQueryOperationBO bo, BoolQueryBuilder boolBuilder) {
        ESearchType eSearchType = bo.getESearchType();
        switch (eSearchType) {
            case TERM: {
                boolBuilder.must(QueryBuilders.termQuery(key, bo.getValue()));
                break;
            }
            case TERM_NOT: {
                boolBuilder.mustNot(QueryBuilders.termsQuery(key, bo.getValue()));
                break;
            }
            case TERMS: {
                boolBuilder.must(QueryBuilders.termsQuery(key, (List<Object>) bo.getValue()));
                break;
            }
            case NESTED: {
                try {
                    BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
                    Map<String, ESQueryOperationBO> nestedMap = buildESQueryOperationBOMap(bo.getValue());
                    for (String nestedKey : nestedMap.keySet()) {
                        builderFieldQuery(nestedKey, nestedMap.get(nestedKey), boolQueryBuilder);
                    }
                    boolBuilder.must().add(QueryBuilders.nestedQuery(key, boolQueryBuilder, ScoreMode.None));
                } catch (Exception e) {
                    log.error(ESReturnCode.ERROR_CODE_110507.getMsg(), e);
                    throw new BusinessException(ESReturnCode.ERROR_CODE_110507);
                }
            }
            case RANGE: {
                if (bo.getValue() instanceof ESRangeQueryBO) {
                    ESRangeQueryBO esRangeQueryBO = (ESRangeQueryBO) bo.getValue();
                    RangeQueryBuilder rangeQueryBuilder = QueryBuilders.rangeQuery(key);
                    if (esRangeQueryBO != null) {
                        Object start = esRangeQueryBO.getStart();
                        Object end = esRangeQueryBO.getEnd();
                        if (start != null) {
                            rangeQueryBuilder.gte(start);
                        }
                        if (end != null) {
                            rangeQueryBuilder.lte(end);
                        }
                        boolBuilder.must(rangeQueryBuilder);
                    }
                }
                break;
            }
        }
    }


    /**
     * 功能描述:构建用于es查询的ESQueryOperationBOMap
     *
     * @param object
     * @return java.util.Map<java.lang.String, com.zhilingsd.qi.business.common.bo.rule.ESQueryOperationBO>
     * @auther 吞星（yangguojun）
     * @date 2020/3/4-18:46
     */
    public Map<String, ESQueryOperationBO> buildESQueryOperationBOMap(Object object) throws Exception {
        try {
            Map<String, ESQueryOperationBO> map = new HashMap<>();
            Field[] declaredFields = object.getClass().getDeclaredFields();
            for (Field declaredField : declaredFields) {
                declaredField.setAccessible(true);
                Object value = declaredField.get(object);
                if (value != null && ESAnnotationHandle.getSearchField(declaredField) != null) {
                    map.put(ESAnnotationHandle.getSearchField(declaredField), new ESQueryOperationBO(value, ESAnnotationHandle.getESearchType(declaredField)));
                }
            }
            return map;
        } catch (Exception e) {
            throw e;
        }
    }

    public void buildRangeQuery(Date start, Date end, String fieldName, Map<String, ESQueryOperationBO> map) {
        ESRangeQueryBO esRangeQueryBO = new ESRangeQueryBO();
        if (start != null) {
            String StartStr = DateUtil.getDate(start, DateUtil.DATE_TIME);
            esRangeQueryBO.setStart(StartStr);
        }
        if (end != null) {
            String endStr = DateUtil.getDate(end, DateUtil.DATE_TIME);
            esRangeQueryBO.setEnd(endStr);
        }
        boolean judgeBeanAllFieldIsNull = BeanUtils.judgeBeanAllFieldIsNull(esRangeQueryBO);
        if (!judgeBeanAllFieldIsNull) {
            ESQueryOperationBO esQueryOperationBO = new ESQueryOperationBO();
            esQueryOperationBO.setValue(esRangeQueryBO);
            esQueryOperationBO.setESearchType(ESearchType.RANGE);
            map.put(fieldName, esQueryOperationBO);
        }
    }

    public void buildRangeQuery(Double start, Double end, String fieldName, Map<String, ESQueryOperationBO> map) {
        ESRangeQueryBO esRangeQueryBO = new ESRangeQueryBO();
        if (start != null) {
            esRangeQueryBO.setStart(start);
        }
        if (end != null) {
            esRangeQueryBO.setEnd(end);
        }
        boolean judgeBeanAllFieldIsNull = BeanUtils.judgeBeanAllFieldIsNull(esRangeQueryBO);
        if (!judgeBeanAllFieldIsNull) {
            ESQueryOperationBO esQueryOperationBO = new ESQueryOperationBO();
            esQueryOperationBO.setValue(esRangeQueryBO);
            esQueryOperationBO.setESearchType(ESearchType.RANGE);
            map.put(fieldName, esQueryOperationBO);
        }
    }


    private List<GroupCountRecordBo> getGroupCountRecordBos(List<? extends Terms.Bucket> buckets) {
        List<GroupCountRecordBo> groupCountRecordBos = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(buckets)) {
            buckets.forEach(
                    bucket -> {
                        GroupCountRecordBo groupCountRecordBo = new GroupCountRecordBo();
                        String keyAsString = bucket.getKeyAsString();
                        long docCount = bucket.getDocCount();
                        groupCountRecordBo.setCountNum(docCount);
                        groupCountRecordBo.setGroupByColum(keyAsString);
                        groupCountRecordBos.add(groupCountRecordBo);
                    }
            );
        }
        return groupCountRecordBos;
    }

    private BoolQueryBuilder buildBoolQueryBuilder(Map<String, Object> queryMap, Map<String, Map<String, Object>> rangeMap) {
        BoolQueryBuilder boolBuilder = QueryBuilders.boolQuery();
        //条件
        if (queryMap != null) {
            queryMap.forEach(
                    (k, v) -> {
                        boolBuilder.must(QueryBuilders.termsQuery(k, v));
                    }
            );
        }
        //时间范围查询
        if (rangeMap != null) {
            rangeMap.forEach(
                    (k, map) -> {
                        RangeQueryBuilder from = QueryBuilders.rangeQuery(k).from(map.get("start")).to(map.get("end")).format(DateUtil.DATE_TIME);
                        boolBuilder.must(from);
                    }
            );
        }
        return boolBuilder;
    }

}



