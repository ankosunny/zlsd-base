package com.zhilingsd.base.common.utils;

import com.zhilingsd.base.common.emuns.ReturnCode;
import com.zhilingsd.base.common.exception.BusinessException;
import org.apache.http.*;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.params.ConnRouteParams;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class HttpClientUtil {
    public static boolean USE_PROXY;
    public static String PROXY_HOST;
    public static int PROXY_PORT;
    public static String USER_NAME;
    public static String PASSWORD;
    private static Logger LOGGER = LoggerFactory.getLogger(HttpClientUtil.class);
    private boolean useProxy = false;
    private String proxyHost = "10.214.169.77";
    private int proxyPort = 3128;
    private String userName = "userapp";
    private String password = "userapp";

    /**
     * 设置http代理
     *
     * @return
     */
    public static DefaultHttpClient getHttpClient() {
        if (USE_PROXY) {
            DefaultHttpClient httpClient = new DefaultHttpClient();
            httpClient.getCredentialsProvider().setCredentials(
                    new AuthScope(PROXY_HOST, PROXY_PORT),
                    new UsernamePasswordCredentials(USER_NAME, PASSWORD));
            HttpHost proxy = new HttpHost(PROXY_HOST, PROXY_PORT);
            httpClient.getParams().setParameter(ConnRouteParams.DEFAULT_PROXY, proxy);
            return httpClient;
        } else {
            return new DefaultHttpClient();
        }
    }

    public static String doPostByForm(String url, Map<String, String> params) {
        CloseableHttpClient httpClient = getHttpClient();

        HttpPost post = new HttpPost(url);
        Header contentType = new BasicHeader("Content-Type",
                "application/x-www-form-urlencoded;charset=GBK");
        post.setHeader(contentType);
        post.setEntity(buildRequestEntity(params));
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(10000)
                .setConnectionRequestTimeout(10000).setSocketTimeout(10000).build();
        post.setConfig(requestConfig);
        try {
            try {
                long start = System.currentTimeMillis();
                HttpResponse execute = httpClient.execute(post);
                int statusCode = execute.getStatusLine().getStatusCode();
                if (statusCode != 200) {
                    LOGGER.error("请求URL:{}异常,CODE:{}", url, statusCode);
                }
                String responseStr = EntityUtils.toString(execute.getEntity(), "UTF-8");
                return responseStr;
            } finally {
                httpClient.close();
            }
        } catch (IOException e) {//网络异常时，做业务时也要认为处理中
            LOGGER.warn("网络异常", e);

        } catch (Exception e) {
            LOGGER.warn("", e);
        }

        return "";
    }

    private static HttpEntity buildRequestEntity(Map<String, String> params) {
        List<NameValuePair> nameValuePairs = new ArrayList();
        if (params != null) {
            Iterator var5 = params.keySet().iterator();

            while (var5.hasNext()) {
                String key = (String) var5.next();
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
            contentType = new BasicHeader("Content-Type",
                    "application/x-www-form-urlencoded;charset=GBK");
        } else {
            contentType = new BasicHeader("Content-Type", bodyType);
        }

        post.setHeader(contentType);
        post.setEntity(new StringEntity(strSendData, requestCharset));
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(10000)
                .setConnectionRequestTimeout(10000).setSocketTimeout(10000).build();
        post.setConfig(requestConfig);
        try {
            try {
                long start = System.currentTimeMillis();
                HttpResponse execute = httpClient.execute(post);
                int statusCode = execute.getStatusLine().getStatusCode();
                if (statusCode != 200) {
                    LOGGER.error("请求URL:{}异常,CODE:{}", url, statusCode);
                }
                String responseStr = EntityUtils.toString(execute.getEntity(), responseCharset);
                return responseStr;
            } finally {
                httpClient.close();
            }
        } catch (IOException e) {//网络异常时，做业务时也要认为处理中
            LOGGER.warn("网络异常", e);
            throw new BusinessException(ReturnCode.NETWORK_ERROR.getCode(),
                    ReturnCode.NETWORK_ERROR.getMsg(), e);
        } catch (Exception e) {
            LOGGER.warn("执行post请求异常", e);
            throw new BusinessException(ReturnCode.SYSTEM_ERROR.getCode(),
                    ReturnCode.SYSTEM_ERROR.getMsg(), e);
        }
    }

    /**
     * 发送http GET请求，并返回http响应字符串
     *
     * @param urlstr 完整的请求url字符串
     * @return
     */
    public static String doGetRequest(String urlstr) {
        return doGetRequest(urlstr, "GBK");
    }

    public static String doGetRequest(String url, String responseCharset) {
        CloseableHttpClient httpClient = getHttpClient();

        //url
        HttpGet httpGet = new HttpGet(url);
        Header contentType = new BasicHeader("Content-Type",
                "application/x-www-form-urlencoded;charset=GBK");
        httpGet.setHeader(contentType);
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(10000)
                .setConnectionRequestTimeout(10000).setSocketTimeout(10000).build();
        httpGet.setConfig(requestConfig);

        try {
            try {
                long start = System.currentTimeMillis();
                HttpResponse execute = httpClient.execute(httpGet);
                int statusCode = execute.getStatusLine().getStatusCode();
                if (statusCode != 200) {
                    LOGGER.error("请求URL:{}异常,CODE:{}", url, statusCode);
                }
                String responseStr = EntityUtils.toString(execute.getEntity(), responseCharset);
                return responseStr;
            } finally {
                httpClient.close();
            }
        } catch (IOException e) {//网络异常时，做业务时也要认为处理中
            LOGGER.warn("网络异常", e);

        } catch (Exception e) {
            LOGGER.warn("", e);
            //            throw new HsjryPayChanelFailException("响应报文解析异常");//此类异常，业务认为处理中
        }

        return "";
    }

    public static String postJson(String url, String jsonString)  {

        org.apache.http.client.HttpClient httpclient = HttpClients
                .createDefault();
        HttpPost post = new HttpPost(url);

        StringEntity entity = new StringEntity(jsonString, "utf-8");
        entity.setContentEncoding("UTF-8");
        entity.setContentType("application/json");
        post.setEntity(entity);

        try{
            HttpResponse response = httpclient.execute(post);
            HttpEntity entitys = response.getEntity();

            if (response.getStatusLine().getStatusCode() == 200) {
                String result = EntityUtils.toString(entitys);
                return result;
            }
        }catch (Exception e) {//网络异常时，做业务时也要认为处理中
            LOGGER.error("网络异常", e);

        }
        return "";

    }


    public static void main(String[] args) {
        String url = "https://59.41.103.102/gzdsf/ProcessServlet";
        url = "http://59.41.103.98:333/gzdsf/ProcessServlet";
        String postContent = "<?xml version=\"1.0\" encoding=\"GBK\" standalone=\"yes\"?><GZELINK><INFO><TRX_CODE>100005</TRX_CODE><VERSION>05</VERSION><DATA_TYPE></DATA_TYPE><LEVEL></LEVEL><USER_NAME>operator</USER_NAME><USER_PASS>ora_123456</USER_PASS><REQ_SN>000010000002679</REQ_SN><SIGNED_MSG>04f2788e750aac4e0c941dbaa49596e1174841372cadb1673aa239c215c79dfd6224bf222a5065e8056a9e23ab70219b5d89cf61fe2967b5b8f830b9b79954a0d658af4c91b69b99603399efc9985beb47e8cf2a6998cbde3fdf558cc32410410acc5b130745dadfd6a5e18a042c75b9ab906117afecc1039c2c7794d5dc6595</SIGNED_MSG></INFO><BODY><TRANS_SUM><BUSINESS_CODE>04900</BUSINESS_CODE><MERCHANT_ID>001053110000001</MERCHANT_ID><SUBMIT_TIME>20171220113549</SUBMIT_TIME><TOTAL_ITEM>1</TOTAL_ITEM><TOTAL_SUM>100100</TOTAL_SUM></TRANS_SUM><TRANS_DETAILS><TRANS_DETAIL><SN>000010000002679</SN><E_USER_CODE></E_USER_CODE><BANK_CODE></BANK_CODE><ACCOUNT_TYPE>00</ACCOUNT_TYPE><ACCOUNT_NO>6227008888888999900</ACCOUNT_NO><ACCOUNT_NAME>[0xcd][0xf5][0xcc][0xec][0xc5][0xf4]</ACCOUNT_NAME><PROVINCE></PROVINCE><CITY></CITY><BANK_NAME></BANK_NAME><ACCOUNT_PROP>0</ACCOUNT_PROP><AMOUNT>100100</AMOUNT><CURRENCY>CNY</CURRENCY><PROTOCOL></PROTOCOL><PROTOCOL_USERID></PROTOCOL_USERID><ID_TYPE></ID_TYPE><ID></ID><TEL></TEL><CUST_USERID></CUST_USERID><RECKON_ACCOUNT></RECKON_ACCOUNT><REMARK></REMARK><RESERVE1></RESERVE1><RESERVE2></RESERVE2></TRANS_DETAIL></TRANS_DETAILS></BODY></GZELINK>";
        postContent = "<?xml version=\"1.0\" encoding=\"GBK\" standalone=\"yes\"?><GZELINK><INFO><TRX_CODE>100005</TRX_CODE><VERSION>05</VERSION><DATA_TYPE></DATA_TYPE><LEVEL></LEVEL><USER_NAME>operator</USER_NAME><USER_PASS>ora_123456</USER_PASS><REQ_SN>WDH297991908796480</REQ_SN><SIGNED_MSG>1cf68e26ddf0590a3d2aa402afdf279d3928fe842c83f7c5b8bb11a02dd5092a09a1f72bdce52eac911f86ff5cad7a03e578a07a81d2bdb356da081d88a0269d34b9512930466423517dc5d589767bf1427ba6ea5fc000dcbf606b9acebc406ddc92dcc4cf08751319799c9eb31858cc14b4dc80c429aafcb69c36ed08cb83ce</SIGNED_MSG></INFO><BODY><TRANS_SUM><BUSINESS_CODE>04900</BUSINESS_CODE><MERCHANT_ID>001053110000001</MERCHANT_ID><SUBMIT_TIME>20180122110109</SUBMIT_TIME><TOTAL_ITEM>1</TOTAL_ITEM><TOTAL_SUM>10</TOTAL_SUM></TRANS_SUM><TRANS_DETAILS><TRANS_DETAIL><SN>WDH297991908796480</SN><E_USER_CODE></E_USER_CODE><BANK_CODE></BANK_CODE><ACCOUNT_TYPE>00</ACCOUNT_TYPE><ACCOUNT_NO>6227007200230670000</ACCOUNT_NO><ACCOUNT_NAME>test</ACCOUNT_NAME><PROVINCE></PROVINCE><CITY></CITY><BANK_NAME></BANK_NAME><ACCOUNT_PROP>1</ACCOUNT_PROP><AMOUNT>10</AMOUNT><CURRENCY>CNY</CURRENCY><PROTOCOL></PROTOCOL><PROTOCOL_USERID></PROTOCOL_USERID><ID_TYPE></ID_TYPE><ID></ID><TEL></TEL><CUST_USERID></CUST_USERID><RECKON_ACCOUNT></RECKON_ACCOUNT><REMARK></REMARK><RESERVE1></RESERVE1><RESERVE2></RESERVE2></TRANS_DETAIL></TRANS_DETAILS></BODY></GZELINK>\n";

        HttpClientUtil.doPostRequest(url, postContent);

    }

    @PostConstruct
    private void init() {
        USE_PROXY = useProxy;
        PROXY_HOST = proxyHost;
        PROXY_PORT = proxyPort;
        USER_NAME = userName;
        PASSWORD = password;
    }
}
