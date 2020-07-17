package com.zhilingsd.base.es.template;

import com.zhilingsd.base.es.bo.ESPageQueryBO;
import com.zhilingsd.base.es.bo.QueryDocumentOutBO;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import java.util.List;

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
     * @param indexName  索引名称
     * @param clazz      实体类
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
     * 功能描述
     *
     * @param object
     * @return org.elasticsearch.action.index.IndexResponse
     * *@auther 吞星（yangguojun）
     * @date 2020/7/15-13:42
     */
    IndexResponse addDocument(Object object);

    IndexResponse updateDocument(Object object, String id, String indexName);

    BulkByScrollResponse deleteDocument(String routeIdKey, List<Long> routeIdValue, String[] indexNames);

    QueryDocumentOutBO queryDocument(ESPageQueryBO esPageQueryBO);

}
