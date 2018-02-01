package com.toiletCat.utils;

import java.io.*;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.Iterator;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author liuzikun
 * @date 2017年12月30日 上午11:27:25
 *
 */
public class HttpClientUtil {

    private static final Logger logger = LoggerFactory.getLogger(HttpClientUtil.class);

    // utf-8字符编码
    private static final String CHARSET_UTF_8 = "utf-8";

    // HTTP内容类型。
    private static final String CONTENT_TYPE_TEXT_HTML = "text/xml";

    // HTTP内容类型。相当于form表单的形式，提交数据
    public static final String CONTENT_TYPE_FORM_URL = "application/x-www-form-urlencoded";

    // HTTP内容类型。相当于form表单的形式，提交数据
    public static final String CONTENT_TYPE_JSON_URL = "application/json;charset=utf-8";

    // 连接管理器
    private static PoolingHttpClientConnectionManager pool;

    // 请求配置
    private static RequestConfig requestConfig;

    static {

        try {
            //System.out.println("初始化HttpClientTest~~~开始");
            SSLContextBuilder builder = new SSLContextBuilder();
            builder.loadTrustMaterial(null, new TrustSelfSignedStrategy());
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
                    builder.build());
            // 配置同时支持 HTTP 和 HTPPS
            Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory> create().register(
                    "http", PlainConnectionSocketFactory.getSocketFactory()).register(
                    "https", sslsf).build();
            // 初始化连接管理器
            pool = new PoolingHttpClientConnectionManager(
                    socketFactoryRegistry);
            // 将最大连接数增加到200，实际项目最好从配置文件中读取这个值
            pool.setMaxTotal(200);
            // 设置最大路由
            pool.setDefaultMaxPerRoute(2);
            // 根据默认超时限制初始化requestConfig
            int socketTimeout = 10000;
            int connectTimeout = 10000;
            int connectionRequestTimeout = 10000;
            requestConfig = RequestConfig.custom().setConnectionRequestTimeout(
                    connectionRequestTimeout).setSocketTimeout(socketTimeout).setConnectTimeout(
                    connectTimeout).build();

            //System.out.println("初始化HttpClientTest~~~结束");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
        // 设置请求超时时间
        requestConfig = RequestConfig.custom().setSocketTimeout(50000).setConnectTimeout(50000)
                .setConnectionRequestTimeout(50000).build();
    }

    private static CloseableHttpClient getHttpClient() {

        return HttpClients.custom()
                // 设置连接池管理
                .setConnectionManager(pool)
                // 设置请求配置
                .setDefaultRequestConfig(requestConfig)
                // 设置重试次数
                .setRetryHandler(new DefaultHttpRequestRetryHandler(0, false))
                .build();
    }

    public static void main(String[] args) {
        String s = "https://pay.v8jisu.cn/api.php?act=order&pid=13806&key=j0UgNf3nKBTJFb20urdb4Jb4tgj8mTT7&out_trade_no=ToiletCat151746390813327e4d67967cc45c6ac8378e37cb179e4";

        httpsRequest(s,"GET", null);
    }

    /**
     * 发送https请求
     *
     * @param requestUrl
     *            请求地址
     * @param requestMethod
     *            请求方式（GET、POST）
     * @param outputStr
     *            提交的数据
     * @return rootNode(通过rootNode.get(key)的方式获取json对象的属性值)
     */
    public static String httpsRequest(String requestUrl, String requestMethod, String outputStr) {

        logger.info("httpsRequest requestUrl:" + requestUrl);

        StringBuffer buffer = new StringBuffer();
        try {
            // 创建SSLContext对象，并使用我们指定的信任管理器初始化
            TrustManager[] tm = { new MyX509TrustManager() };
            SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");

            sslContext.init(null, tm, new java.security.SecureRandom());
            // 从上述SSLContext对象中得到SSLSocketFactory对象
            SSLSocketFactory ssf = sslContext.getSocketFactory();

            URL url = new URL(requestUrl);
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            conn.setSSLSocketFactory(ssf);

            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            // 设置请求方式（GET/POST）
            conn.setRequestMethod(requestMethod);
            //conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            if ("GET".equalsIgnoreCase(requestMethod))
                conn.connect();

            // 当outputStr不为null时向输出流写数据
            if (null != outputStr) {

                OutputStream outputStream = conn.getOutputStream();

                // 注意编码格式
                outputStream.write(outputStr.getBytes("UTF-8"));

                outputStream.close();
            }

            // 从输入流读取返回内容
            InputStream inputStream = conn.getInputStream();

            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");

            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String str;

            while ((str = bufferedReader.readLine()) != null) {

                buffer.append(str);
            }

            // 释放资源
            bufferedReader.close();

            inputStreamReader.close();

            inputStream.close();

            conn.disconnect();

            logger.info("httpsRequest response:" + buffer.toString());

            return buffer.toString();

        } catch (Exception e) {

            e.printStackTrace();
        }

        return null;

    }

    /**
     * 微信获得https请求返回结果
     * @param requestUrl 请求url
     * @param requestMethod 请求方法 get/post
     * @param outputStr 输出(无用目前)
     * @return
     */
    public static JsonNode wxHttpsRequest(String requestUrl, String requestMethod, String outputStr) {

        ObjectMapper mapper = new ObjectMapper();

        JsonNode rootNode = null;

        try {
            String response = httpsRequest(requestUrl, requestMethod, outputStr);

            rootNode = mapper.readTree(response);

        } catch (Exception e) {

            logger.error("wxHttpsRequest error:" + e, e);
        }

        return rootNode;
    }

    /**
     * 发送Post请求
     *
     * @param httpPost http post 请求
     * @return
     */
    private static String sendHttpPost(HttpPost httpPost) {

        return sendHttpRequest(httpPost, null);
    }

    /**
     * 发送Get请求
     *
     * @param httpGet http get 请求
     * @return
     */
    private static String sendHttpGet(HttpGet httpGet) {

        return sendHttpRequest(null, httpGet);

    }

    /**
     * 发送http请求
     * @param httpPost http post 请求
     * @param httpGet http get 请求
     * @return
     */
    private static String sendHttpRequest(HttpPost httpPost, HttpGet httpGet) {
        CloseableHttpClient httpClient;
        CloseableHttpResponse response = null;
        // 响应内容
        String responseContent = null;
        try {
            // 创建默认的httpClient实例.
            httpClient = getHttpClient();

            if(httpPost == null) {

                // 配置请求信息
                httpGet.setConfig(requestConfig);

                // 执行请求
                response = httpClient.execute(httpGet);

            } else {

                // 配置请求信息
                httpPost.setConfig(requestConfig);
                // 执行请求
                response = httpClient.execute(httpPost);

            }


            // 得到响应实例
            HttpEntity entity = response.getEntity();

            // 判断响应状态
            if (response.getStatusLine().getStatusCode() != 200) {
                throw new Exception(
                        "HTTP Request is not success, Response code is " + response.getStatusLine().getStatusCode());
            }

            if (HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) {
                responseContent = EntityUtils.toString(entity, CHARSET_UTF_8);
                EntityUtils.consume(entity);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                // 释放资源
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return responseContent;
    }

    /**
     * 发送 post请求
     *
     * @param httpUrl 地址
     */
    public static String sendHttpPost(String httpUrl) {
        // 创建httpPost
        HttpPost httpPost = new HttpPost(httpUrl);
        return sendHttpPost(httpPost);
    }

    /**
     * 发送 get请求
     *
     * @param httpUrl 请求url
     */
    public static String sendHttpGet(String httpUrl) {
        // 创建get请求
        HttpGet httpGet = new HttpGet(httpUrl);
        return sendHttpGet(httpGet);
    }


    /**
     * 发送 post请求
     *
     * @param httpUrl 地址
     * @param params  参数(格式:key1=value1&key2=value2)
     *
     */
    private static String sendHttpPost(String httpUrl, String params) {
        // 创建httpPost
        HttpPost httpPost = new HttpPost(httpUrl);

        setHttpPostParam(httpPost, params);

        return sendHttpPost(httpPost);
    }

    /**
     * 发送 post请求
     *
     * @param maps  参数
     */
    public static String sendHttpPost(String httpUrl, Map<String, String> maps) {

        String param = convertStringParamter(maps);

        return sendHttpPost(httpUrl, param);
    }

    /**
     * 发送 post请求 发送json数据
     *
     * @param httpUrl
     *            地址
     * @param paramsJson
     *            参数(格式 json)
     *
     */
    public static String sendHttpPostJson(String httpUrl, String paramsJson) {
        // 创建httpPost
        HttpPost httpPost = new HttpPost(httpUrl);

        setHttpPostParam(httpPost, paramsJson);

        return sendHttpPost(httpPost);
    }

    /**
     * 发送 post请求 发送xml数据
     *
     * @param httpUrl   地址
     * @param paramsXml  参数(格式 Xml)
     *
     */
    public static String sendHttpPostXml(String httpUrl, String paramsXml) {
        // 创建httpPost
        HttpPost httpPost = new HttpPost(httpUrl);

        setHttpPostParam(httpPost, paramsXml);

        return sendHttpPost(httpPost);
    }

    private static void setHttpPostParam(HttpPost httpPost, String param) {
        try {
            // 设置参数
            if (param != null && param.trim().length() > 0) {
                StringEntity stringEntity = new StringEntity(param, "UTF-8");
                stringEntity.setContentType(CONTENT_TYPE_TEXT_HTML);
                httpPost.setEntity(stringEntity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 将map集合的键值对转化成：key1=value1&key2=value2 的形式
     *
     * @param parameterMap
     *            需要转化的键值对集合
     * @return 字符串
     */
    private static String convertStringParamter(Map parameterMap) {
        StringBuffer parameterBuffer = new StringBuffer();
        if (parameterMap != null) {
            Iterator iterator = parameterMap.keySet().iterator();
            String key = null;
            String value = null;
            while (iterator.hasNext()) {
                key = (String) iterator.next();
                if (parameterMap.get(key) != null) {
                    value = (String) parameterMap.get(key);
                } else {
                    value = "";
                }
                parameterBuffer.append(key).append("=").append(value);
                if (iterator.hasNext()) {
                    parameterBuffer.append("&");
                }
            }
        }
        return parameterBuffer.toString();
    }

    /**
     * 获取Ip
     *
     * @param request 请求
     * @return
     * @throws Exception
     */
    public static String getUserIp(HttpServletRequest request) throws Exception {
        String ip = "";

        if (request.getHeader("x-forwarded-for") != null && (!request.getHeader("x-forwarded-for").trim().equals("")
        )) {
            ip = request.getHeader("x-forwarded-for").split(",")[0];
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

}
