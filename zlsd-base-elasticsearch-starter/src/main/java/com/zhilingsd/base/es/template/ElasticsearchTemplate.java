package com.zhilingsd.base.es.template;

import com.zhilingsd.base.es.bo.ESPageQueryBO;
import com.zhilingsd.base.es.bo.EsQueryBO;
import com.zhilingsd.base.es.bo.PageDocumentOutBO;
import com.zhilingsd.base.es.bo.QueryDocumentOutBO;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.index.reindex.BulkByScrollResponse;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @program: 智灵时代广州研发中心
 * @description:
 * @author: 吞星(yangguojun)
 * @create: 2020-07-14 16:24
 **/

public interface ElasticsearchTemplate {


    /**
     * 功能描述 判断索引是否已经存在
     *
     * @param indexName 索引名
     * @return boolean
     * @auther 吞星（yangguojun）
     * @date 2020/2/29-14:41
     */
    Boolean existsIndex(String indexName);

    /**
     * 功能描述 创建默认配置的index
     *
     * @param indexName 索引名称
     * @param clazz     实体类
     * @return java.lang.Boolean
     * @auther 吞星（yangguojun）
     * @date 2020/7/15-11:30
     */
    Boolean createDefaultIndex(String indexName, Class clazz);

    /**
     * 功能描述 创建默认配置的index
     *
     * @param indexName
     * @param clazz
     * @param builder
     * @return java.lang.Boolean
     * @auther 吞星（yangguojun）
     * @date 2020/7/15-11:30
     */
    Boolean createIndex(String indexName, Class clazz, Settings.Builder builder);

    /**
     * 功能描述 添加document
     *
     * @param object
     * @return org.elasticsearch.action.index.IndexResponse
     * *@auther 吞星（yangguojun）
     * @date 2020/7/15-13:42
     */
    IndexResponse addDocument(Object object);


    IndexResponse updateDocument(Object object, String id, String indexName);

    BulkByScrollResponse deleteDocument(String routeIdKey, List<Long> routeIdValue, String[] indexNames);

    /**
     * 功能描述：标准查询，非分页
     *
     * @param esPageQueryBO
     * @return com.zhilingsd.base.es.bo.QueryDocumentOutBO
     * @auther 吞星（yangguojun）
     * @date 2020/7/17-16:29
     */
    QueryDocumentOutBO normalQueryDocument(ESPageQueryBO esPageQueryBO);

    PageDocumentOutBO pageQueryDocument(ESPageQueryBO esPageQueryBO);


    /**
     * 功能描述
     *
     * @param esQueryBO
     * @param offset    聚合查询的时候，返回桶结果的偏移量，第一次调用传null,以后的调用，通过以下方式获得偏移量
     *                  Aggregations aggregations = searchResponse.getAggregations();
     *                  ParsedComposite currTerms = (ParsedComposite) aggregations.getAsMap().get("_composite");
     *                  Map<String, Object> offset = currTerms.afterKey()
     * @return org.elasticsearch.action.search.SearchResponse
     * @auther 吞星（yangguojun）
     * @date 2020/7/17-15:15
     */
    SearchResponse aggreationQuery(EsQueryBO esQueryBO, Map<String, Object> offset) throws IOException;

}