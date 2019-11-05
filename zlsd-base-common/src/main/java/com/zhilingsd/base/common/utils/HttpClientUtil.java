package com.zhilingsd.base.common.utils;

import com.zhilingsd.base.common.emuns.ReturnCode;
import com.zhilingsd.base.common.exception.BusinessException;
import org.apache.http.*;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HttpClientUtil {
    private static Logger LOGGER = LoggerFactory.getLogger(HttpClientUtil.class);

    /**
     * 设置http代理
     *
     * @return
     */
    private static CloseableHttpClient getHttpClient() {
        HttpRequestRetryHandler myRetryHandler = (exception, executionCount, context) -> false;
        return HttpClients.custom().setRetryHandler(myRetryHandler).build();
    }

    public static String doPostByForm(String url, Map<String, String> params) {
        CloseableHttpClient httpClient = getHttpClient();

        HttpPost post = new HttpPost(url);
        Header contentType = new BasicHeader("Content-Type", "application/x-www-form-urlencoded;charset=GBK");
        post.setHeader(contentType);
        post.setEntity(buildRequestEntity(params));
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(10000)
                .setConnectionRequestTimeout(10000).setSocketTimeout(10000).build();
        post.setConfig(requestConfig);
        try {
            HttpResponse execute = httpClient.execute(post);
            int statusCode = execute.getStatusLine().getStatusCode();
            if (statusCode != HttpStatus.SC_OK) {
                LOGGER.warn("请求URL:{}异常,CODE:{}", url, statusCode);
            }
            return EntityUtils.toString(execute.getEntity(), "UTF-8");
        } catch (IOException ignore) {
            LOGGER.warn("网络异常", ignore);
        } finally {
            try {
                httpClient.close();
            } catch (IOException ignore) {
                LOGGER.warn("网络异常", ignore);
            }
        }

        return "";
    }

    private static HttpEntity buildRequestEntity(Map<String, String> params) {
        List<NameValuePair> nameValuePairs = new ArrayList();
        if (params != null) {

            for (String key : params.keySet()) {
                nameValuePairs.add(new BasicNameValuePair(key, params.get(key)));
            }
        }
        return new UrlEncodedFormEntity(nameValuePairs, Charset.forName("UTF-8"));
    }

    public static String doPostRequest(String url, String strSendData) {
        return doPostRequest(url, strSendData, null, "GBK");
    }

    public static String doPostRequest(String url, String strSendData, String bodyType) {
        return doPostRequest(url, strSendData, bodyType, "GBK");
    }

    public static String doPostRequest(String url, String strSendData, String bodyType,
                                       String responseCharset) {
        return doPostRequest(url, strSendData, bodyType, responseCharset, "GBK");
    }

    public static String doPostRequest(String url, String strSendData, String bodyType,
                                       String responseCharset, String requestCharset) {
        CloseableHttpClient httpClient = getHttpClient();

        HttpPost post = new HttpPost(url);
        Header contentType = null;

        if (bodyType == null) {
            contentType = new BasicHeader("Content-Type", "application/x-www-form-urlencoded;charset=GBK");
        } else {
            contentType = new BasicHeader("Content-Type", bodyType);
        }

        post.setHeader(contentType);
        post.setEntity(new StringEntity(strSendData, requestCharset));
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(10000)
                .setConnectionRequestTimeout(10000).setSocketTimeout(10000).build();
        post.setConfig(requestConfig);
        try {
            HttpResponse execute = httpClient.execute(post);
            int statusCode = execute.getStatusLine().getStatusCode();
            if (statusCode != HttpStatus.SC_OK) {
                LOGGER.error("请求URL:{}异常,CODE:{}", url, statusCode);
            }
            return EntityUtils.toString(execute.getEntity(), responseCharset);
        } catch (IOException e) {//网络异常时，做业务时也要认为处理中
            LOGGER.warn("网络异常", e);
            throw new BusinessException(ReturnCode.NETWORK_ERROR.getCode(), ReturnCode.NETWORK_ERROR.getMsg(), e);
        } catch (Exception e) {
            LOGGER.warn("执行post请求异常", e);
            throw new BusinessException(ReturnCode.SYSTEM_ERROR.getCode(), ReturnCode.SYSTEM_ERROR.getMsg(), e);
        }
    }

    /**
     * 发送http GET请求，并返回http响应字符串
     *
     * @param url 完整的请求url字符串
     * @return
     */
    public static String doGetRequest(String url) {
        return doGetRequest(url, "GBK");
    }

    public static String doGetRequest(String url, String responseCharset) {
        CloseableHttpClient httpClient = getHttpClient();

        HttpGet httpGet = new HttpGet(url);
        Header contentType = new BasicHeader("Content-Type", "application/x-www-form-urlencoded;charset=GBK");
        httpGet.setHeader(contentType);
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(10000)
                .setConnectionRequestTimeout(10000).setSocketTimeout(10000).build();
        httpGet.setConfig(requestConfig);

        try {
            try {
                HttpResponse execute = httpClient.execute(httpGet);
                int statusCode = execute.getStatusLine().getStatusCode();
                if (statusCode != HttpStatus.SC_OK) {
                    LOGGER.error("请求URL:{}异常,CODE:{}", url, statusCode);
                }
                return EntityUtils.toString(execute.getEntity(), responseCharset);
            } finally {
                httpClient.close();
            }
        } catch (IOException ignore) {
            LOGGER.warn("网络异常", ignore);
        } finally {
            try {
                httpClient.close();
            } catch (IOException ignore) {
                LOGGER.warn("网络异常", ignore);
            }
        }
        return "";
    }

    public static String postJson(String url, String jsonString)  {
        HttpPost post = new HttpPost(url);

        StringEntity entity = new StringEntity(jsonString, "utf-8");
        entity.setContentEncoding("UTF-8");
        entity.setContentType("application/json");
        post.setEntity(entity);

        //设置请求和传输超时时间
        RequestConfig requestConfig = RequestConfig.custom().setConnectionRequestTimeout(1000)
                .setConnectTimeout(1000).setSocketTimeout(1000).build();
        post.setConfig(requestConfig);

        CloseableHttpClient httpClient = getHttpClient();
        try {
            HttpResponse response = httpClient.execute(post);
            HttpEntity entitys = response.getEntity();

            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                return EntityUtils.toString(entitys);
            }
        } catch (IOException ignore) {
            LOGGER.warn("网络异常", ignore);
        } finally {
            try {
                httpClient.close();
            } catch (IOException ignore) {
                LOGGER.warn("网络异常", ignore);
            }
        }
        return "";

    }


    public static void main(String[] args) {
        String url = "http://10.30.1.153:14502/recordService";
        String postContent = "{\"callid\":\"C201911051506000A1E01150612619858\",\"request\":\"get_cti_callrecord_callid\",\"endtime\":\"20191105150610\",\"page_no\":\"1\",\"starttime\":\"20\n" +
                "191105150610\",\"page_size\":\"10\"}";

        String s = HttpClientUtil.postJson(url, postContent);
        System.out.println("s = " + s);

    }
}
