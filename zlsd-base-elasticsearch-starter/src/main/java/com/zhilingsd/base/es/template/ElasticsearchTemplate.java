package com.zhilingsd.base.es.template;

import com.zhilingsd.base.es.bo.ESNormalQueryBO;
import com.zhilingsd.base.es.bo.ESPageQueryBO;
import com.zhilingsd.base.es.bo.EsQueryBO;
import com.zhilingsd.base.es.bo.HitBO;
import com.zhilingsd.base.es.bo.PageDocumentOutBO;
import org.elasticsearch.action.bulk.BulkResponse;
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

    public static final String YEAR_ = "yyyy";

    public static final String YYYY_MM = "yyyyMM";
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
     * 功能描述 获得索引名称
     *
     * @param clazz           实体类对象
     * @param indexNamesuffix 索引后缀
     * @return java.lang.String
     * @auther 吞星（yangguojun）
     * @date 2020/2/29-16:11
     */
    public String getIndexName(Class clazz, String indexNamesuffix);

    /**
     * 功能描述 创建自定义的index
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
     * 功能描述 以周来分index的时候，获得当前周的indexName
     *
     * @param object
     * @return java.lang.String
     * @auther 吞星（yangguojun）
     * @date 2020/9/21-11:00
     */
    String getCurWeekIndexName(Object object);

    /**
     * 功能描述 以周来分index的时候，获得当前周的indexName
     *
     * @param object
     * @return java.lang.String
     * @auther 吞星（yangguojun）
     * @date 2020/9/21-11:00
     */
    String getNextWeekIndexName(Object object);


    /**
     * 功能描述 添加document,当每个月一个index的时候，会自动添加到当月的index中
     *
     * @param object
     * @return org.elasticsearch.action.index.IndexResponse
     * *@auther 吞星（yangguojun）
     * @date 2020/7/15-13:42
     */
    IndexResponse addDocument(Object object, String indexName);


    /**
     * 功能描述 添加document,当一周一个index的时候，会自动添加到当周的index中
     *
     * @param object
     * @return org.elasticsearch.action.index.IndexResponse
     * *@auther 吞星（yangguojun）
     * @date 2020/7/15-13:42
     */
    IndexResponse addCurWeekDocument(Object object);

    /**
     * 功能描述 添加document,需要自己指定indexName
     *
     * @param object
     * @param id        documentId
     * @param indexName
     * @return org.elasticsearch.action.index.IndexResponse
     * *@auther 吞星（yangguojun）
     * @date 2020/7/15-13:42
     */
    IndexResponse addDocument(Object object, String id, String indexName);


    IndexResponse updateDocument(Object object, String id, String indexName);

    BulkByScrollResponse deleteDocument(String routeIdKey, List<Long> routeIdValue, String[] indexNames);

    /**
     * 功能描述 批量添加document,fieldName获取指定字段的值作为docid，不填默认不指定docid
     *
     * @param object    ArrayList对象
     * @param fieldName
     * @param indexName
     * @return org.elasticsearch.action.bulk.BulkResponse
     * *@auther longhui
     * @date 2020/9/16
     */
    BulkResponse batchAddDocument(Object object, String fieldName, String indexName);

    /**
     * 功能描述：标准查询，非分页
     *
     * @param esNormalQueryBO
     * @return com.zhilingsd.base.es.bo.QueryDocumentOutBO
     * @auther 吞星（yangguojun）
     * @date 2020/7/17-16:29
     */
    List<HitBO> normalQueryDocument(ESNormalQueryBO esNormalQueryBO);

    /**
     * 功能描述：滚动查询
     *
     * @param esNormalQueryBO
     * @param scrollTimeOut
     * @return
     */
    List<HitBO> normalScrollQueryDocument(ESNormalQueryBO esNormalQueryBO, Long scrollTimeOut);


    /**
     * 功能描述：分页查询
     *
     * @param esPageQueryBO
     * @return com.zhilingsd.base.es.bo.QueryDocumentOutBO
     * @auther 吞星（yangguojun）
     * @date 2020/7/17-16:29
     */
    PageDocumentOutBO pageQueryDocument(ESPageQueryBO esPageQueryBO);

    /**
     * 功能描述：根据id和索引查询
     *
     * @param indexName
     * @param id
     * @return com.zhilingsd.base.es.bo.QueryDocumentOutBO
     * @auther linmenghuai
     * @date 2020年9月15日18:59:55
     */
    String getDocumentByKey(String indexName, String id);

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

    /**
     * 功能：
     * @param object
     * @param patterns yyyy,yyyyMM
     * @return
     */
    String getIndexName(Object object, String patterns);


}
