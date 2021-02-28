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
package com.zhilingsd.base.es.client.config;

import java.io.Serializable;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

/**
 * @author linmenghuai
 * @version 1.0
 * @className ElasticSearchProperties.java
 * @description //TODO
 * @date 2020/9/27 10:28
 */
@Component
@ConfigurationProperties(prefix = ElasticSearchProperties.ES_PREFIX)
@Data
public class ElasticSearchProperties implements Serializable {

    public static final String ES_PREFIX = "zlsd.es";


    private String nodes;
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


    /**
     * 从连接池获取连接的超时时间,不宜过长,单位ms
     */
    private int connectionRequestTimout = 500;

}