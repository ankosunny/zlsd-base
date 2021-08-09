package com.zhilingsd.base.es.config;

import com.zhilingsd.base.es.handle.ElasticsearchHandle;
import com.zhilingsd.base.es.template.ElasticsearchTemplate;
import com.zhilingsd.base.es.template.ElasticsearchTemplateImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @program: 智灵时代广州研发中心
 * @description:
 * @author: 吞星(yangguojun)
 * @create: 2020-02-26 15:04
 **/

@Slf4j
@Configuration
@ConditionalOnProperty(prefix = ElasticSearchProperties.ES_PREFIX, name = "whether.start", havingValue = "true", matchIfMissing = false)
@EnableConfigurationProperties(ElasticSearchProperties.class)
public class ZLElasticSearchClientConfig {


    private ElasticSearchProperties esProperties;


    public ZLElasticSearchClientConfig(ElasticSearchProperties properties) {
        this.esProperties = properties;
    }


    @Bean
    public RestHighLevelClient restHighLevelClient() {
        HttpHost[] httpHosts = new HttpHost[esProperties.getHosts().size()];
        for (int i = 0; i < httpHosts.length; i++) {
            HttpHostProperties host = esProperties.getHosts().get(i);
            httpHosts[i] = new HttpHost(host.getIp(), Integer.parseInt(host.getPort()), "http");
        }

        RestClientBuilder restClientBuilder = RestClient.builder(httpHosts);
        restClientBuilder.setRequestConfigCallback(requestConfigBuilder -> {
            requestConfigBuilder.setConnectTimeout(esProperties.getConnectTimeout());
            requestConfigBuilder.setSocketTimeout(esProperties.getReadTimeout());
            requestConfigBuilder.setConnectionRequestTimeout(esProperties.getConnectionRequestTimout());
            return requestConfigBuilder;
        });
        restClientBuilder.setHttpClientConfigCallback(httpClientBuilder -> {
            //连接池的最大连接数
            httpClientBuilder.setMaxConnTotal(esProperties.getMaxTotalConnect());
            /**
             * maxConnectPerRoute是根据连接到的主机对连接池的最大连接数(maxTotalConnect)的一个细分；比如：
             * maxTotalConnect=400 maxConnectPerRoute=200
             * 而我只连接到http://zhilingsd.com时，到这个主机的并发最多只有200；而不是400；
             * 而我连接到http://zhilingsd.com 和 http://mujin.com时，到每个主机的并发最多只有200；即加起来是400（但不能超过400）；所以起作用的设置是maxConnectPerRoute
             */
            httpClientBuilder.setMaxConnPerRoute(esProperties.getMaxConnectPerRoute());
            Boolean isNeedIdentification = esProperties.getIsNeedIdentification();
            if (isNeedIdentification){
                //需要用户名和密码的认证
                final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
                credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(esProperties.getUserName(), esProperties.getPassword()));
                httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
            }
            return httpClientBuilder;
        });
        System.out.println(restClientBuilder);
        RestHighLevelClient restHighLevelClient = new RestHighLevelClient(restClientBuilder);

        return restHighLevelClient;

    }

    @Bean
    public ElasticsearchHandle elasticsearchHandle() {
        return new ElasticsearchHandle();
    }

    @Bean
    public ElasticsearchTemplate elasticsearchTemplate(RestHighLevelClient restHighLevelClient) {
        ElasticsearchTemplateImpl elasticsearchTemplate = new ElasticsearchTemplateImpl();
        elasticsearchTemplate.setRestHighLevelClient(restHighLevelClient);
        elasticsearchTemplate.setElasticsearchHandle(elasticsearchHandle());
        return elasticsearchTemplate;
    }

}
