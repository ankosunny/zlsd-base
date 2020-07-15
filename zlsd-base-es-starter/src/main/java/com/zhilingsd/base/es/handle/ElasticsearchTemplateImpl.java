package com.zhilingsd.base.es.handle;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zhilingsd.base.common.emuns.ReturnCode;
import com.zhilingsd.base.common.exception.BusinessException;
import com.zhilingsd.base.common.utils.DateUtil;
import com.zhilingsd.base.es.bo.ESPageQueryBO;
import com.zhilingsd.base.es.bo.HitBO;
import com.zhilingsd.base.es.bo.QueryDocumentOutBO;
import com.zhilingsd.base.es.client.ElasticSearchClientConfig;
import com.zhilingsd.base.es.emuns.ESReturnCode;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
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
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.elasticsearch.index.reindex.DeleteByQueryRequest;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @program: 智灵时代广州研发中心
 * @description:
 * @author: 吞星(yangguojun)
 * @create: 2020-07-14 17:56
 **/
@Data
@Slf4j
public class ElasticsearchTemplateImpl implements ElasticsearchTemplate {


    private RestHighLevelClient restHighLevelClient;

    private ElasticsearchHandle elasticsearchHandle;

    public static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String PROPERTIES = "properties";
    public static final String TYPE = "type";
    public static final String FORMAT = "format";

    public static final String DATE_ = "yyyyMM";

    public static String UNDER_LINE = "_";

    public static String INDEX_DEFAULT_TYPE = "_doc";

    /**
     * 功能描述 判断索引是否已经存在
     *
     * @param indexName 索引名
     * @return boolean
     * @auther 吞星（yangguojun）
     * @date 2020/2/29-14:41
     */
    @Override
    public Boolean existsIndex(String indexName) {
        try {
            GetIndexRequest request = new GetIndexRequest(indexName);
            boolean exists = restHighLevelClient.indices().exists(request, RequestOptions.DEFAULT);
            return exists;
        } catch (Exception e) {
            log.error("判断索引是否存在失败：{}", e);
        }
        return null;
    }


    @Override
    public Boolean createDefaultIndex(String indexName, Class clazz) {
        try {
            CreateIndexRequest request = new CreateIndexRequest(indexName);
            XContentBuilder builder = elasticsearchHandle.getIndexBuilder(clazz);
            Settings.Builder put = Settings.builder()
                    //设置index分区数为3
                    .put("index.number_of_shards", 3)
                    //设置副本数为1
                    .put("index.number_of_replicas", 1)
                    //关闭自动创建type
                    .put("index.mapper.dynamic", false)
                    //设置查询最大返回数为3万
                    .put("index.max_result_window", 30000);
            request.settings(put);
            request.mapping(builder);
            CreateIndexResponse createIndexResponse = restHighLevelClient.indices().create(request, RequestOptions.DEFAULT);
            boolean acknowledged = createIndexResponse.isAcknowledged();
            return acknowledged;
        } catch (Exception e) {
            log.error("创建索引：{}，失败：{}", indexName, e);
        }
        return null;
    }

    @Override
    public Boolean createIndex(String indexName, Class clazz, Settings.Builder put) {
        try {
            CreateIndexRequest request = new CreateIndexRequest(indexName);
            XContentBuilder builder = elasticsearchHandle.getIndexBuilder(clazz);
            request.settings(put);
            request.mapping(builder);
            CreateIndexResponse createIndexResponse = restHighLevelClient.indices().create(request, RequestOptions.DEFAULT);
            boolean acknowledged = createIndexResponse.isAcknowledged();
            return acknowledged;
        } catch (Exception e) {
            log.error("创建索引：{}，失败：{}", indexName, e);
        }
        return null;
    }

    @Override
    public IndexResponse addDocument(Object object) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String jsonStr = objectMapper.writeValueAsString(object);
            String indexName = getAddIndexName(object);
            IndexRequest request = new IndexRequest(indexName, INDEX_DEFAULT_TYPE);
            //request.setRefreshPolicy(WriteRequest.RefreshPolicy.WAIT_UNTIL);
            request.source(jsonStr, XContentType.JSON);
            IndexResponse indexResponse = restHighLevelClient.index(request, RequestOptions.DEFAULT);
            if (indexResponse != null && indexResponse.getResult() == DocWriteResponse.Result.CREATED) {
                return indexResponse;
            } else {
                log.error("新增一个document记录失败");
            }
        } catch (Exception e) {
            log.error("新增一个document记录失败：{}", e);
        }
        return null;
    }

    /**
     * 获得新增的indexName
     *
     * @param object
     * @return
     */
    public String getAddIndexName(Object object) {
        Date date = new Date();
        String indexNamesuffix = DateUtil.getDate(date, DATE_);
        String indexName = getIndexName(object.getClass(), indexNamesuffix);
        return indexName;
    }

    /**
     * 功能描述 获得索引名称
     *
     * @param clazz           实体类对象
     * @param indexNamesuffix 索引后缀
     * @return java.lang.String
     * @auther 吞星（yangguojun）
     * @date 2020/2/29-16:11
     */
    public String getIndexName(Class clazz, String indexNamesuffix) {
        String prefix = ESAnnotationHandle.getPrefix(clazz);
        StringBuffer buffer = new StringBuffer();
        buffer.append(prefix);
        buffer.append(UNDER_LINE);
        buffer.append(indexNamesuffix);
        String indexName = buffer.toString();
        return indexName;
    }

    @Override
    public IndexResponse updateDocument(Object object, String id, String indexName) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String jsonStr = objectMapper.writeValueAsString(object);
            IndexRequest request = new IndexRequest(indexName, INDEX_DEFAULT_TYPE, id);
            request.setRefreshPolicy(WriteRequest.RefreshPolicy.WAIT_UNTIL);
            request.source(jsonStr, XContentType.JSON);
            IndexResponse indexResponse = restHighLevelClient.index(request, RequestOptions.DEFAULT);
            if (indexResponse != null && indexResponse.getResult() == DocWriteResponse.Result.UPDATED) {
                return indexResponse;
            } else {
                log.error("修改一个document记录失败");
            }
        } catch (Exception e) {
            log.error("修改一个document记录失败：{}", e);
        }
        return null;
    }

    @Override
    public BulkByScrollResponse deleteDocument(String routeIdKey, List<Long> routeIdValue, String[] indexNames) {
        try {
            BoolQueryBuilder must = QueryBuilders.boolQuery().must(QueryBuilders.termsQuery(routeIdKey, routeIdValue));
            DeleteByQueryRequest deleteByQueryRequest = new DeleteByQueryRequest(indexNames).setQuery(must);
            BulkByScrollResponse response = restHighLevelClient.deleteByQuery(deleteByQueryRequest, RequestOptions.DEFAULT);
            if (response != null) {
                return response;
            } else {
                log.error("删除document记录失败");
            }
        } catch (Exception e) {
            log.error("删除document记录失败：{}", e);
        }
        return null;
    }

    @Override
    public QueryDocumentOutBO queryDocument(ESPageQueryBO esPageQueryBO) {
        String[] indexNames = esPageQueryBO.getIndexNames();
        if (indexNames.length < 1) {
            throw new BusinessException(ReturnCode.BUSINESS_ERROR, "索引名称不能为空");
        }
        List<HitBO> list = new ArrayList<>(20);
        Long totalHits = null;
        SearchRequest searchRequest = new SearchRequest(indexNames);
        searchRequest.types(INDEX_DEFAULT_TYPE);
        searchRequest.source(elasticsearchHandle.builderEsQuery(esPageQueryBO));
        try {
            log.info("ES查询DSL：{}", searchRequest.source().toString());
            SearchResponse response = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
            SearchHits hits = response.getHits();
            totalHits = hits.getTotalHits();
            for (SearchHit hit : hits) {
                Map<String, Object> source = hit.getSourceAsMap();
                Object object = JSONObject.parseObject(JSON.toJSONString(source), esPageQueryBO.getClazz());
                HitBO hitBO = new HitBO();
                hitBO.setDocumentId(hit.getId());
                hitBO.setIndexName(hit.getIndex());
                hitBO.setDucumentContent(object);
                list.add(hitBO);
            }
        } catch (Exception e) {
            log.error("查询document异常：{}", e);
        }
        QueryDocumentOutBO documentOutBO = new QueryDocumentOutBO();
        documentOutBO.setHitBOS(list);
        documentOutBO.setTotalHits(totalHits);
        return documentOutBO;
    }


}
