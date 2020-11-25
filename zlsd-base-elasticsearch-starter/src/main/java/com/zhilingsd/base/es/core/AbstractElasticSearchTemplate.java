/**
 * Software License Declaration.
 * <p>
 * zhilingsd.com, Co,. Ltd.
 * Copyright © 2016 All Rights Reserved.
 * <p>
 * Copyright Notice
 * This documents is provided to zhilingsd contracting agent or authorized programmer only.
 * This source code is written and edited by zhilingsd Co,.Ltd Inc specially for financial
 * business contracting agent or authorized cooperative company, in order to help them to
 * install, programme or central control in certain project by themselves independently.
 * <p>
 * Disclaimer
 * If this source code is needed by the one neither contracting agent nor authorized programmer
 * during the use of the code, should contact to zhilingsd Co,. Ltd Inc, and get the confirmation
 * and agreement of three departments managers  - Research Department, Marketing Department and
 * Production Department.Otherwise zhilingsd will charge the fee according to the programme itself.
 * <p>
 * Any one,including contracting agent and authorized programmer,cannot share this code to
 * the third party without the agreement of zhilingsd. If Any problem cannot be solved in the
 * procedure of programming should be feedback to zhilingsd Co,. Ltd Inc in time, Thank you!
 */
package com.zhilingsd.base.es.core;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.*;

import com.zhilingsd.base.es.core.annotation.ESAnnotationHandle;
import com.zhilingsd.base.es.core.annotation.ESearchType;
import com.zhilingsd.base.es.core.annotation.FieldType;
import com.zhilingsd.base.es.core.mapping.IndexCoordinates;
import com.zhilingsd.base.es.core.query.*;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.zhilingsd.base.common.utils.DateUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * @author linmenghuai
 * @version 1.0
 * @className AbstractElasticSearchTemplate.java
 * @description //TODO
 * @date 2020/9/25 11:27
 */
@Slf4j
public abstract class AbstractElasticSearchTemplate implements ElasticSearchTemplate {

    private static final String PROPERTIES = "properties";
    private static final String TYPE = "type";
    private static final String STORE = "store";
    private static final String INDEX = "index";
    public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String FORMAT = "format";
//    protected static final String INDEX_DEFAULT_TYPE = "_doc";

    protected Settings.Builder getIndexDefaultSetting() {
        Settings.Builder setting = Settings.builder()
                //设置index分区数为5
                .put("index.number_of_shards", 5)
                //设置副本数为1
                .put("index.number_of_replicas", 1)
                //关闭自动创建type
                .put("index.mapper.dynamic", false)
                //设置查询最大返回数为100000
                .put("index.max_result_window", 100_000);
        return setting;
    }

    /**
     * 目前只支持扁平的类(即只包含基础字段, 不包含对象,List,数组等字段)
     */
    public XContentBuilder generateIndexBuilder(Class clazz) throws IOException {
        Assert.notNull(clazz, "Class must not be null");
        XContentBuilder xContentBuilder = XContentFactory.jsonBuilder();
        xContentBuilder.startObject();
        // 构建字段映射
        xContentBuilder.startObject(PROPERTIES);
        resolveEntity(clazz, xContentBuilder);
        xContentBuilder.endObject();
        xContentBuilder.endObject();
        return xContentBuilder;
    }


    private void resolveEntity(Class clazz, XContentBuilder builder) throws IOException {
        Field[] declaredFields = clazz.getDeclaredFields();
        for (Field javaField : declaredFields) {
            String esFileName = ESAnnotationHandle.getFieldName(javaField);
            FieldType esFieldType = ESAnnotationHandle.getFieldType(javaField);
            Boolean isIndex = ESAnnotationHandle.isIndex(javaField);
            Boolean isStore = ESAnnotationHandle.isStore(javaField);
            builder.startObject(esFileName).field(TYPE, esFieldType.name().toLowerCase()).field(INDEX, isIndex).field(STORE, isStore);
            if (ESAnnotationHandle.getFieldType(javaField).equals(FieldType.Date)) {
                builder.field(FORMAT, DEFAULT_DATE_FORMAT);
            }
            builder.endObject();
        }
    }

    public SearchSourceBuilder builderEsNormalQuery(Object object, List<ESQuerySort> esQuerySorts) throws Exception {
        Map<String, ESQueryField> queryFieldMap = buildESQueryOperationBOMap(object);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        if (queryFieldMap != null && !queryFieldMap.keySet().isEmpty()) {
            BoolQueryBuilder boolBuilder = QueryBuilders.boolQuery();
            if (MapUtils.isNotEmpty(queryFieldMap)) {
                queryFieldMap.keySet().forEach(key -> {
                    ESQueryField bo = queryFieldMap.get(key);
                    builderFilterQuery(key, bo, boolBuilder);
                });
            }
            searchSourceBuilder.query(boolBuilder);
        }
        if (CollectionUtils.isNotEmpty(esQuerySorts)) {
            esQuerySorts.forEach(value -> searchSourceBuilder
                    .sort(SortBuilders.fieldSort(value.getFieldName())
                            .unmappedType(value.getEsFieldType().name())
                            .order(value.getSortOrder())));
        }
        return searchSourceBuilder;
    }

    private Map<String, ESQueryField> buildESQueryOperationBOMap(Object object) throws Exception {
        try {
            Map<String, ESQueryField> map = new HashMap<>();
            Field[] declaredFields = object.getClass().getDeclaredFields();
            for (Field declaredField : declaredFields) {
                declaredField.setAccessible(true);
                Object value = declaredField.get(object);
                if (value != null && ESAnnotationHandle.getSearchFieldType(declaredField) != null) {
                    if(value instanceof Collection && ((Collection) value).size() == 0) {
                        continue;
                    }
                    map.put(ESAnnotationHandle.getSearchFieldName(declaredField), new ESQueryField(value, ESAnnotationHandle.getSearchFieldType(declaredField)));
                }
            }
            return map;
        } catch (Exception e) {
            throw e;
        }
    }


    private void builderFilterQuery(String key, ESQueryField bo, BoolQueryBuilder boolBuilder) {


        ESearchType eSearchType = bo.getESearchType();
        switch (eSearchType) {
            case TERM: {
                boolBuilder.must(QueryBuilders.constantScoreQuery(QueryBuilders.termQuery(key, bo.getValue())));
                break;
            }
            case TERM_NOT: {
                boolBuilder.mustNot(QueryBuilders.constantScoreQuery(QueryBuilders.termsQuery(key, bo.getValue())));
                break;
            }
            case TERMS: {
                boolBuilder.must(QueryBuilders.constantScoreQuery(QueryBuilders.termsQuery(key, (List<Object>) bo.getValue())));
                break;
            }
            case RANGE: {
                if (bo.getValue() != null && bo.getValue() instanceof ESRangeQuery) {
                    ESRangeQuery esRangeQuery = (ESRangeQuery) bo.getValue();
                    if(esRangeQuery.getStart() != null || esRangeQuery.getEnd() != null) {
                        if (esRangeQuery.getStart() instanceof Date) {
                            String start = DateUtil.getDate((Date) esRangeQuery.getStart(), DateUtil.DATE_TIME);
                            String end = DateUtil.getDate((Date) esRangeQuery.getEnd(), DateUtil.DATE_TIME);
                            RangeQueryBuilder rangeQueryBuilder = QueryBuilders.rangeQuery(key);
                            rangeQueryBuilder.gte(start).lte(end);
                            boolBuilder.must(QueryBuilders.constantScoreQuery(rangeQueryBuilder));
                        } else {
                            RangeQueryBuilder rangeQueryBuilder = QueryBuilders.rangeQuery(key);
                            rangeQueryBuilder.gte(esRangeQuery.getStart()).lte(esRangeQuery.getEnd());
                            boolBuilder.must(QueryBuilders.constantScoreQuery(rangeQueryBuilder));
                        }
                    }
                }
                break;
            }
            case START_WITH: {
                boolBuilder.must(QueryBuilders.constantScoreQuery(QueryBuilders.wildcardQuery(key, bo.getValue().toString() + "*")));
                break;
            }
            default:{
                boolBuilder.must(QueryBuilders.constantScoreQuery(QueryBuilders.termQuery(key, bo.getValue())));
            }
        }
    }

    protected IndexRequest prepareIndexRequest(String indexName, String id) {
        if (StringUtils.isEmpty(id)) {
            return new IndexRequest(indexName, IndexCoordinates.TYPE);
        } else {
            return new IndexRequest(indexName, IndexCoordinates.TYPE, id);
        }

    }

    /**
     * 预处理搜索请求参数,目前只满足基础功能
     */
    protected SearchRequest prepareSearchRequest(ESNormalQuery query, SearchSourceBuilder sourceBuilder) {

        String[] indexNames = query.getIndexNames();
        Assert.notNull(indexNames, "No index defined for Query");
        Assert.notEmpty(indexNames, "No index defined for Query");

        SearchRequest request = new SearchRequest(indexNames);

        sourceBuilder.version(true);

        if (query.getSourceFilter() != null) {
            SourceFilter sourceFilter = query.getSourceFilter();
            sourceBuilder.fetchSource(sourceFilter.getIncludes(), sourceFilter.getExcludes());
        }

        if (query.getPageable().isPaged()) {
            sourceBuilder.from(query.getPageable().getOffset());
            sourceBuilder.size(query.getPageable().getPageSize());
        } else {
            sourceBuilder.from(0);
            sourceBuilder.size(query.getDefaultSize());
        }

//        if (!query.getFields().isEmpty()) {
//            sourceBuilder.fetchSource(query.getFields().toArray(new String[0]), null);
//        }

//        if (query.getIndicesOptions() != null) {
//            request.indicesOptions(query.getIndicesOptions());
//        }

//        if (query.isLimiting()) {
//            // noinspection ConstantConditions
//            sourceBuilder.size(query.getMaxResults());
//        }

//        if (query.getMinScore() > 0) {
//            sourceBuilder.minScore(query.getMinScore());
//        }

//        if (query.getPreference() != null) {
//            request.preference(query.getPreference());
//        }

//        request.searchType(query.getSearchType());

//        prepareSort(query, sourceBuilder, getPersistentEntity(clazz));

//        HighlightBuilder highlightBuilder = highlightBuilder(query);

//        if (highlightBuilder != null) {
//            sourceBuilder.highlighter(highlightBuilder);
//        }

//        if (query instanceof NativeSearchQuery) {
//            prepareNativeSearch((NativeSearchQuery) query, sourceBuilder);
//
//        }

//        if (query.getTrackTotalHits() != null) {
//            sourceBuilder.trackTotalHits(query.getTrackTotalHits());
//        } else if (query.getTrackTotalHitsUpTo() != null) {
//            sourceBuilder.trackTotalHitsUpTo(query.getTrackTotalHitsUpTo());
//        }
//
//        if (StringUtils.hasLength(query.getRoute())) {
//            request.routing(query.getRoute());
//        }

        request.source(sourceBuilder);
        return request;
    }

    protected SearchScrollHits initScrollResult(SearchResponse response, Class clazz) {
        SearchScrollHits searchScrollHits = new SearchScrollHits();
        List<HitEntity> list = initResult( response,  clazz);
        searchScrollHits.setHitEntities(list);
        searchScrollHits.setScrollId(response.getScrollId());
        return searchScrollHits;
    }

    protected List<HitEntity> initResult(SearchResponse response, Class clazz) {
        List<HitEntity> list = new ArrayList<>();
        SearchHits hits = response.getHits();
        for (SearchHit hit : hits) {
            String source = hit.getSourceAsString();
            Object object = JSONObject.parseObject(source, clazz);
            HitEntity hitEntity = new HitEntity();
            hitEntity.setDocumentId(hit.getId());
            hitEntity.setIndexName(hit.getIndex());
            hitEntity.setDocumentContent(object);
            list.add(hitEntity);
        }
        return list;
    }
}