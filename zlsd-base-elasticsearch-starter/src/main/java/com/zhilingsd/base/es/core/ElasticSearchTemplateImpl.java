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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zhilingsd.base.es.core.mapping.IndexCoordinates;
import com.zhilingsd.base.es.core.query.*;
import lombok.Data;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.bulk.BulkItemResponse;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.ClearScrollRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchScrollRequest;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zhilingsd.base.common.emuns.ReturnCode;
import com.zhilingsd.base.common.exception.BusinessException;
import com.zhilingsd.base.common.utils.ReflectUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * @author linmenghuai
 * @version 1.0
 * @className ElasticSearchTemplateImpl.java
 * @description //TODO
 * @date 2020/9/27 17:23
 */
@Data
@Slf4j
public class ElasticSearchTemplateImpl extends AbstractElasticSearchTemplate {


    private RestHighLevelClient client;

    @Override
    public boolean doCreate(IndexCoordinates index, Class clazz) {
        return doCreate(index, clazz, getIndexDefaultSetting());
    }

    @Override
    public boolean doCreate(IndexCoordinates index, Class clazz, Settings.Builder setting) {
        String indexName = index.getIndexName();
        try {
            CreateIndexRequest request = new CreateIndexRequest(indexName);
            XContentBuilder builder = generateIndexBuilder(clazz);
            request.source(builder);
            request.settings(setting);
            request.mapping(builder);
            request.alias(index.getAlias());
            GetIndexRequest getIndexRequest = new GetIndexRequest(indexName);
            boolean exists = client.indices().exists(getIndexRequest, RequestOptions.DEFAULT);
            if (!exists) {
                CreateIndexResponse createIndexResponse = client.indices().create(request, RequestOptions.DEFAULT);
                boolean acknowledged = createIndexResponse.isAcknowledged();
                return acknowledged;
            } else {
                log.warn("创建索引：{}，失败：索引已存在", indexName);
                return false;
            }
        } catch (Exception e) {
            log.error("创建索引：{}，失败：{}", indexName, e);
        }
        return false;
    }

    @Override
    public Boolean index(Object record, IndexCoordinates index) {

        try {

            String indexName = index.getIndexName();
            IndexRequest request = prepareIndexRequest(indexName, null);
            return index(request, record);
        } catch (Exception e) {
            log.error("新增一个document记录失败：{}", e);
            return false;
        }
    }

    @Override
    public Boolean index(Object record, String id, IndexCoordinates index) {
        try {
            String indexName = index.getIndexName();
            IndexRequest request = prepareIndexRequest(indexName, id);
            return index(request, record);
        } catch (Exception e) {
            log.error("新增一个document记录失败：{}", e);
            return false;
        }
    }

    public Boolean updateSelective(Object record, String id, IndexCoordinates index) {
        try {
            UpdateRequest updateRequest = new UpdateRequest(index.getIndexName(), IndexCoordinates.TYPE, id);
            updateRequest.doc(JSON.toJSONString(record), XContentType.JSON);
            UpdateResponse response = client.update(updateRequest, RequestOptions.DEFAULT);
            if (response != null && response.getResult() == DocWriteResponse.Result.UPDATED) {
                return true;
            } else {
                log.error("新增一个document记录失败:" + JSON.toJSONString(response));
                return false;
            }
        } catch (Exception e) {
            log.error("更改一个document记录失败：{}", e);
            return false;
        }
    }

    @Override
    public Object getDocumentByKey(IndexCoordinates index, String id) {
        try {
            GetRequest getRequest = new GetRequest(index.getIndexName(), IndexCoordinates.TYPE, id);
            GetResponse getResponse = client.get(getRequest, RequestOptions.DEFAULT);
            if (getResponse != null && getResponse.isExists()) {
                return JSONObject.parseObject(getResponse.getSourceAsString(), index.getClazz());
            }
        } catch (Exception e) {
            log.error("根据索引和id获取ocument记录失败：{}", e);
        }
        return null;

    }


    @Override
    public List<HitEntity> query(Object queryObject, ESNormalQuery normalQuery) throws Exception {

        List<HitEntity> list = new ArrayList<>();
        SearchSourceBuilder searchSourceBuilder = builderEsNormalQuery(queryObject, normalQuery.getEsQuerySort());
        SearchRequest searchRequest = prepareSearchRequest(normalQuery, searchSourceBuilder);
        try {
            log.info("ES查询DSL：{}", searchRequest.source().toString());
            long start = System.currentTimeMillis();
            SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);
            log.info("ES查询耗时:{}ms", System.currentTimeMillis() - start);
            return initResult(response, normalQuery.getClazz());
        } catch (Exception e) {
            log.error("查询document异常：{}", e);
        }
        return list;
    }

    @Override
    public ScrollResult queryScroll(Object queryObject, ESNormalQuery normalQuery) throws Exception {
        List<HitEntity> list = new ArrayList<>();
        SearchSourceBuilder searchSourceBuilder = builderEsNormalQuery(queryObject, normalQuery.getEsQuerySort());
        SearchRequest searchRequest = prepareSearchRequest(normalQuery, searchSourceBuilder);
        SearchResponse response;
        String scrollId = null;
        try {
            searchRequest.scroll("3m");
            log.info("ES查询DSL：{}", searchRequest.source().toString());
            long start = System.currentTimeMillis();
            response = client.search(searchRequest, RequestOptions.DEFAULT);
            scrollId = response.getScrollId();
            List<HitEntity> hits = initResult(response, normalQuery.getClazz());
            while (!CollectionUtils.isEmpty(hits)) {
                list.addAll(hits);
                //后续的请求
                SearchScrollRequest searchScrollRequest = new SearchScrollRequest(scrollId);
                searchScrollRequest.scroll("3m");
                log.info("ES深度查询DSL：{}", searchScrollRequest.toString());
                response = client.scroll(searchScrollRequest, RequestOptions.DEFAULT);
                hits = initResult(response, normalQuery.getClazz());
            }
            log.info("ES查询耗时:{}ms", System.currentTimeMillis() - start);
            ScrollResult scrollResult = new ScrollResult();
            scrollResult.setScrollId(response.getScrollId());
            scrollResult.setTotal(response.getHits().getTotalHits());
            scrollResult.setList(list);
            return scrollResult;
        } catch (Exception e) {
            log.error("查询document异常：{}", e);
        } finally {
            //及时清除es快照，释放资源
            ClearScrollRequest clearScrollRequest = new ClearScrollRequest();
            clearScrollRequest.addScrollId(scrollId);
            client.clearScroll(clearScrollRequest, RequestOptions.DEFAULT);
        }
        return new ScrollResult();
    }

    @Override
    public ScrollResult queryScroll(Object queryObject, ESNormalQuery normalQuery, String scrollId) throws Exception {
        List<HitEntity> list = new ArrayList<>();
        SearchSourceBuilder searchSourceBuilder = builderEsNormalQuery(queryObject, normalQuery.getEsQuerySort());
        SearchRequest searchRequest = prepareSearchRequest(normalQuery, searchSourceBuilder);
        try {
            SearchResponse response;
            long start = System.currentTimeMillis();
            if (StringUtils.isEmpty(scrollId)) {
                searchRequest.scroll("3m");
                log.info("ES查询DSL：{}", searchRequest.source().toString());
                response = client.search(searchRequest, RequestOptions.DEFAULT);
            } else {
                //后续的请求
                SearchScrollRequest searchScrollRequest = new SearchScrollRequest(scrollId);
                searchScrollRequest.scroll("3m");
                log.info("ES深度查询DSL：{}", searchScrollRequest.toString());
                response = client.scroll(searchScrollRequest, RequestOptions.DEFAULT);
            }
            log.info("ES查询耗时:{}ms", System.currentTimeMillis() - start);
            list = initResult(response, normalQuery.getClazz());
            ScrollResult scrollResult = new ScrollResult();
            scrollResult.setScrollId(response.getScrollId());
            scrollResult.setTotal(response.getHits().getTotalHits());
            scrollResult.setList(list);
            return scrollResult;
        } catch (Exception e) {
            log.error("查询document异常：{}", e);
        }
        return new ScrollResult();
    }

    @Override
    public PageDocumentResult queryPage(Object queryObject, ESNormalQuery normalQuery) throws Exception {
        List<HitEntity> list = new ArrayList<>();
        Integer totalHits = 0;
        SearchSourceBuilder searchSourceBuilder = builderEsNormalQuery(queryObject, normalQuery.getEsQuerySort());
        SearchRequest searchRequest = prepareSearchRequest(normalQuery, searchSourceBuilder);

        try {
            log.info("ES查询DSL：{}", searchRequest.source().toString());
            long start = System.currentTimeMillis();
            SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);
            log.info("ES查询耗时:{}ms", System.currentTimeMillis() - start);
            totalHits = (int) response.getHits().getTotalHits();
            list = initResult(response, normalQuery.getClazz());
        } catch (Exception e) {
            log.error("查询document异常：{}", e);
        }
        PageDocumentResult pageDocumentResult = new PageDocumentResult();
        pageDocumentResult.setList(list);
        pageDocumentResult.setCurPage(normalQuery.getPageable().getPageNumber());
        pageDocumentResult.setPageSize(normalQuery.getPageable().getPageSize());
        pageDocumentResult.setTotal(totalHits);
        return pageDocumentResult;
    }

    @Override
    public BulkIndexResult bulkIndex(Object object, String fieldName, IndexCoordinates index) throws IOException {
        BulkIndexResult bulkIndexResult = new BulkIndexResult();
        ObjectMapper objectMapper = new ObjectMapper();
        String indexName = index.getIndexName();
        Assert.notNull(indexName, "indexName is not null");
        try {
            if (object instanceof ArrayList<?>) {
                BulkRequest bulkRequest = new BulkRequest();
                for (Object obj : (List<?>) object) {
                    IndexRequest indexRequest = new IndexRequest(indexName, IndexCoordinates.TYPE);
                    if (!StringUtils.isEmpty(fieldName)) {
                        Object fieldValue = ReflectUtil.getFieldValue(obj, fieldName);
                        Assert.notNull(fieldValue, "fieldValue is not null");
                        indexRequest = new IndexRequest(indexName, IndexCoordinates.TYPE, fieldValue.toString());
                    }
                    String jsonStr = objectMapper.writeValueAsString(obj);
                    indexRequest.source(jsonStr, XContentType.JSON);
                    bulkRequest.add(indexRequest);
                }
                BulkResponse bulkResponse = client.bulk(bulkRequest, RequestOptions.DEFAULT);
                Map<String, String> failedDocuments = new HashMap<>();
                if (bulkResponse.hasFailures()) {
                    for (BulkItemResponse item : bulkResponse.getItems()) {
                        if (item.isFailed()) {
                            failedDocuments.put(item.getId(), item.getFailureMessage());
                        }
                    }
                    bulkIndexResult.setSuccessful(false);
                    bulkIndexResult.setFailedDocuments(failedDocuments);
                }else {
                    bulkIndexResult.setSuccessful(true);
                }
                return bulkIndexResult;
            } else {
                throw new BusinessException(ReturnCode.BUSINESS_ERROR, "Object参数类型错误，必须是ArrayList");
            }
        } catch (Exception e) {
            log.error("批量新增document记录失败：{}", e);
            throw e;
        }
    }

    private Boolean index(IndexRequest request, Object record) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonStr = objectMapper.writeValueAsString(record);
        request.source(jsonStr, XContentType.JSON);
        IndexResponse indexResponse = client.index(request, RequestOptions.DEFAULT);
        if (indexResponse != null && (indexResponse.getResult() == DocWriteResponse.Result.CREATED || indexResponse.getResult() == DocWriteResponse.Result.UPDATED)) {
            return true;
        } else {
            log.error("新增一个document记录失败:" + JSON.toJSONString(indexResponse));
            return false;
        }
    }
}