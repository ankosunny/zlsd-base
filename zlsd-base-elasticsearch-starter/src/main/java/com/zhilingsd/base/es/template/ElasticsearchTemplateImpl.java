package com.zhilingsd.base.es.template;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zhilingsd.base.common.emuns.ReturnCode;
import com.zhilingsd.base.common.exception.BusinessException;
import com.zhilingsd.base.common.utils.DateUtil;
import com.zhilingsd.base.es.bo.*;
import com.zhilingsd.base.es.emuns.AggreatinEnum;
import com.zhilingsd.base.es.handle.ESAnnotationHandle;
import com.zhilingsd.base.es.handle.ElasticsearchHandle;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
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
import org.elasticsearch.index.query.*;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.elasticsearch.index.reindex.DeleteByQueryRequest;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.composite.CompositeAggregationBuilder;
import org.elasticsearch.search.aggregations.bucket.composite.CompositeValuesSourceBuilder;
import org.elasticsearch.search.aggregations.bucket.composite.DateHistogramValuesSourceBuilder;
import org.elasticsearch.search.aggregations.bucket.composite.TermsValuesSourceBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.joda.time.DateTimeZone;

import java.io.IOException;
import java.util.*;

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

    public static final String COMPOSITE_NAME = "_composite";
    //默认返回每批桶数量
    public static final int DEFAULT_RETURN_EACH_BATCH_BUCKET_SIZE = 1000;
    //最大返回每批桶数量
    public static final int MAX_RETURN_EACH_BATCH_BUCKET_SIZE = 10000;

    public static final String PERCENTAGE = "%";

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
    public QueryDocumentOutBO normalQueryDocument(ESPageQueryBO esPageQueryBO) {
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

    @Override
    public PageDocumentOutBO pageQueryDocument(ESPageQueryBO esPageQueryBO) {
        return null;
    }




    @Override
    public SearchResponse aggreationQuery(EsQueryBO esQueryBO, Map<String, Object> offset) throws IOException {
        SearchRequest searchRequest = new SearchRequest(esQueryBO.getIndexNames());
        searchRequest.types(INDEX_DEFAULT_TYPE);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        // 获取的字段（列）和不需要获取的列
        searchSourceBuilder.fetchSource(esQueryBO.getIncludes(), esQueryBO.getExcludes());
        //处理普通查询 query
        buildNormalQuery(esQueryBO, searchSourceBuilder);
        //处理聚合分组层 group add,处理聚合数据层 count sum max min
        buildAggGroupMap(esQueryBO, searchSourceBuilder, offset);
        searchRequest.source(searchSourceBuilder);
        SearchResponse search = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        return search;
    }

    /**
     * 功能描述：构建普通查询
     *
     * @param esQueryBO
     * @param searchSourceBuilder
     * @return void
     * @auther 吞星（yangguojun）
     * @date 2020/7/16-20:51
     */
    public void buildNormalQuery(EsQueryBO esQueryBO, SearchSourceBuilder searchSourceBuilder) {
        Map<String, ESQueryField> query = esQueryBO.getQueryMap();
        Integer pageIndex = esQueryBO.getPageIndex();
        Integer pageSize = esQueryBO.getPageSize();
        if (query != null && !query.keySet().isEmpty()) {
            if (pageIndex != null && pageSize != null) {
                searchSourceBuilder.size(pageSize);
                if (pageIndex <= 0) {
                    pageIndex = 0;
                }
                searchSourceBuilder.from((pageIndex - 1) * pageSize);
                searchSourceBuilder.size(pageSize);
            }
        }
        BoolQueryBuilder boolBuilder = handleQueryBoolQueryBuilder(query);
        searchSourceBuilder.query(boolBuilder);
    }


    private BoolQueryBuilder handleQueryBoolQueryBuilder(Map<String, ESQueryField> query) {
        BoolQueryBuilder boolBuilder = QueryBuilders.boolQuery();
        if (MapUtils.isNotEmpty(query)) {
            query.keySet().forEach(key -> {
                ESQueryField bo = query.get(key);
                elasticsearchHandle.builderFieldQuery(key, bo, boolBuilder);
            });
        }
        return boolBuilder;
    }


    /**
     * 构建聚合分组查询 agg  group
     *
     * @param esQueryBO
     * @param searchSourceBuilder
     */
    public void buildAggGroupMap(EsQueryBO esQueryBO, SearchSourceBuilder searchSourceBuilder, Map<String, Object> offset) {
        if (MapUtils.isEmpty(esQueryBO.getAggGroupFieldMap())) {
            return;
        }
        Map<String, Object> aggGroupMap = esQueryBO.getAggGroupFieldMap();
        Map<String, List<String>> aggMetricMap = esQueryBO.getAggMetricMap();
        //默认每批聚合返回桶数量
        Integer  returnBucketSize = DEFAULT_RETURN_EACH_BATCH_BUCKET_SIZE;
        //如果需要返回全部桶，修改每批聚合返回桶数量为1万
        if (esQueryBO.getNeedCount()) {
            returnBucketSize = MAX_RETURN_EACH_BATCH_BUCKET_SIZE;
        }
        //设置分组查询
        CompositeAggregationBuilder composite = buildAggComposite(aggGroupMap, returnBucketSize);
        //设置分批查询
        if (MapUtils.isNotEmpty(offset)) {
            composite.aggregateAfter(offset);
        }
        //如果有需要，可以大桶套一个小桶
        if (MapUtils.isNotEmpty(esQueryBO.getAggGroupSubFieldMap())) {
            esQueryBO.getAggGroupSubFieldMap().forEach(
                    (key, value) -> composite.subAggregation(AggregationBuilders.terms(key)
                            .field(String.valueOf(value))
                            .size(DEFAULT_RETURN_EACH_BATCH_BUCKET_SIZE))
            );
        }
        //设置指标量聚合查询
        if (MapUtils.isNotEmpty(aggMetricMap)) {
            buildAggMetricMap(aggMetricMap, composite);
        }
        searchSourceBuilder.aggregation(composite);
    }

    /**
     * 功能描述 :构建CompositeAggregationBuilder
     *
     * @param aggGroupMap  分组字段 key:分组字段key；value:elasticsearch字段（全名称），如果分组字段要求有序，可以用LinkedHashMap
     * @param returnBucketSize
     * @return org.elasticsearch.search.aggregations.bucket.composite.CompositeAggregationBuilder
     * @auther 吞星（yangguojun）
     * @date 2020/7/17-11:06
     */
    public CompositeAggregationBuilder buildAggComposite(Map<String, Object> aggGroupMap, Integer returnBucketSize) {
        /***********组装字段*************/
        List<CompositeValuesSourceBuilder<?>> sources = new ArrayList<>();
        aggGroupMap.forEach((key, value) -> {
            if (value instanceof EsDateHistogramBO) {
                //时间分组处理
                EsDateHistogramBO esDateHistogramBo = (EsDateHistogramBO) aggGroupMap.get(key);
                //违规率统计使用、需要按照时间分组进行处理
                DateHistogramValuesSourceBuilder dateHistogramValuesSourceBuilder = new DateHistogramValuesSourceBuilder(key)
                        .field(String.valueOf(esDateHistogramBo.getEsField()))
                        .timeZone(DateTimeZone.forOffsetHours(8))
                        .dateHistogramInterval(esDateHistogramBo.getDateHistogramInterval())
                        .missingBucket(true);
                sources.add(dateHistogramValuesSourceBuilder);
            } else {
                //missingBucket的设置，这个的意思是如果该字段没值，为true的时候会返回null，为false不返回整条数据，注意这里是整条数据，而不是单单这个字段而已。
                TermsValuesSourceBuilder sourceBuilder = new TermsValuesSourceBuilder(key)
                        .field(String.valueOf(value))
                        .missingBucket(true);
                sources.add(sourceBuilder);
            }
        });
        CompositeAggregationBuilder composite = new CompositeAggregationBuilder(COMPOSITE_NAME, sources);
        composite.size(returnBucketSize);
        return composite;
    }



    /**
     * 功能描述：构建聚合查询  count  max  min  avg  sum
     *
     * @param aggMetricMap
     * @param aggregationBuilder
     * @return void
     * @auther 吞星（yangguojun）
     * @date 2020/7/17-15:13
     */
    public void buildAggMetricMap(Map<String, List<String>> aggMetricMap, AggregationBuilder aggregationBuilder) {
        aggMetricMap.forEach(
                (key, value) -> {
                    if (AggreatinEnum.SUM.getCode().equals(key)) {
                        value.forEach(
                                aggField -> {
                                    aggregationBuilder.subAggregation(AggregationBuilders.sum(key.concat(UNDER_LINE).concat(aggField)).field(aggField));
                                }
                        );
                    }
                    if (AggreatinEnum.COUNT.getCode().equals(key)) {
                        value.forEach(
                                aggField -> {
                                    aggregationBuilder.subAggregation(AggregationBuilders.count(key.concat(UNDER_LINE).concat(aggField)).field(aggField));
                                }
                        );
                    }
                    if (AggreatinEnum.MAX.getCode().equals(key)) {
                        value.forEach(
                                aggField -> {
                                    aggregationBuilder.subAggregation(AggregationBuilders.max(key.concat(UNDER_LINE).concat(aggField)).field(aggField));
                                }
                        );
                    }
                    if (AggreatinEnum.MIN.getCode().equals(key)) {
                        value.forEach(
                                aggField -> {
                                    aggregationBuilder.subAggregation(AggregationBuilders.min(key.concat(UNDER_LINE).concat(aggField)).field(aggField));
                                }
                        );
                    }
                    if (AggreatinEnum.AVG.getCode().equals(key)) {
                        value.forEach(
                                aggField -> {
                                    aggregationBuilder.subAggregation(AggregationBuilders.avg(key.concat(UNDER_LINE).concat(aggField)).field(aggField));
                                }
                        );
                    }
                }
        );
    }


}
