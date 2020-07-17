package com.zhilingsd.base.es.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.context.annotation.Configuration;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @program: 智灵时代广州研发中心
 * @description:
 * @author: 吞星(yangguojun)
 * @create: 2020-02-26 17:53
 **/

@ConfigurationProperties(prefix = ElasticSearchProperties.ES_PREFIX)
@Configuration
@Data
public class ElasticSearchProperties implements Serializable {

    public static final String ES_PREFIX = "zlsd.es";

    private String userName;
    private String password;
    private String clustername;
    private String index;

    @NestedConfigurationProperty
    private List<HttpHostProperties> hosts;

    /**
     * 连接池的最大连接数
     */
    private int maxTotalConnect = 100;
    /**
     * maxConnectPerRoute是根据连接到的主机对连接池的最大连接数(maxTotalConnect)的一个细分；比如：
     * maxTotalConnect=400 maxConnectPerRoute=200
     * 而我只连接到http://sishuok.com时，到这个主机的并发最多只有200；而不是400；
     * 而我连接到http://sishuok.com 和 http://qq.com时，到每个主机的并发最多只有200；即加起来是400（但不能超过400）；所以起作用的设置是maxConnectPerRoute
     */
    private int maxConnectPerRoute = 50;
    /**
     * 客户端和服务器建立连接超时，默认2s
     */
    private int connectTimeout = 2 * 1000;
    /**
     * 处理时间，默认3S
     */
    private int readTimeout = 50 * 1000;

    private String charset = "UTF-8";
    /**
     * 重试次数,默认2次
     */
    private int retryTimes = 2;
    /**
     * 从连接池获取连接的超时时间,不宜过长,单位ms
     */
    private int connectionRequestTimout = 500;
    /**
     * 长连接保持时间, http1.1都是默认开启长连接的,如果不配置这个时间,连接池会默认永久保持连接,这显然是不合理的. 对于需要频繁调用的服务端,
     * 我们可以开启长连接,然后将这个保持时间设置小一些,能保证相邻两次请求都长连接都还在就可以了. 并不是越久越好,如果是不频繁访问的服务端,
     * 长期保持一个无用的连接也会大大占用资源
     * 针对不同的地址,特别设置不同的长连接保持时间
     */
    private Map<String, Integer> keepAliveTargetHost;
    /**
     * 设置正常情况下长连接保持时间,单位 s
     */
    private int keepAliveTime = 60;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getClustername() {
        return clustername;
    }

    public void setClustername(String clustername) {
        this.clustername = clustername;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public List<HttpHostProperties> getHosts() {
        return hosts;
    }

    public void setHosts(List<HttpHostProperties> hosts) {
        this.hosts = hosts;
    }
}
