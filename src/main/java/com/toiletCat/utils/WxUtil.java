package com.toiletCat.utils;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.JsonNode;
import com.toiletCat.constants.BaseConstant;
import com.toiletCat.constants.RedisConstant;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;

public class WxUtil {

    private static final Logger logger = LoggerFactory.getLogger(WxUtil.class);

    // 凭证获取（GET）——access_token
    private final static String ACCESS_TOKEN_URL =
            "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";

    // 微信JSSDK的ticket请求URL地址——jsapi_ticket
    private final static String JSAPI_TICKET_URL =
            "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=ACCESS_TOKEN&type=jsapi";



    private final static String REQUEST_USER_INFO_URL =
            "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx88888888&redirect_uri=http://lovebread.tunnel.qydev.com/auth&response_type=code&scope=snsapi_base&state=xxxx_state#wechat_redirect";

    /**
     * 获取接口访问凭证
     *
     * @param app_id 凭证
     * @param app_secret 密钥
     * @return
     */
    public static String getAccessToken(String app_id, String app_secret) {


        try(RedisUtil redisUtil = new RedisUtil(RedisConstant.REDIS)) {

            if(StringUtils.isNotBlank(redisUtil.get("access_token"))) {

                return redisUtil.get("access_token");
            }

            String requestUrl = ACCESS_TOKEN_URL.replace("APPID", app_id).replace("APPSECRET", app_secret);

            logger.info("requestUrl:" + requestUrl);

            // 发起GET请求获取凭证
            JsonNode rootNode = HttpClientUtil.wxHttpsRequest(requestUrl, "GET", null);

            logger.info("rootNode:" + rootNode);

            if (null != rootNode.get("access_token")) {

                // 缓存118分钟
                redisUtil.set(118*60, "access_token", rootNode.get("access_token").textValue());

                return rootNode.get("access_token").textValue();

            }

        } catch(Exception e) {
            logger.error("getAccessToken redis error:" + e, e);
        }
        return null;
    }

    /**
     * 调用微信JS接口的临时票据
     *
     * @param access_token 接口访问凭证
     * @return
     */
    public static String getJsApiTicket(String access_token) {

        // 判断参数是否为空
        if(StringUtils.isBlank(access_token)) {

            logger.warn("getJsApiTicket access_token is null!");

            return null;
        }

        try(RedisUtil redisUtil = new RedisUtil(RedisConstant.REDIS)) {

            if(StringUtils.isNotBlank(redisUtil.get("jsapi_ticket"))) {

                return redisUtil.get("jsapi_ticket");
            }

            String requestUrl = JSAPI_TICKET_URL.replace("ACCESS_TOKEN", access_token);
            // 发起GET请求获取凭证
            JsonNode rootNode = HttpClientUtil.wxHttpsRequest(requestUrl, "GET", null);

            logger.info("getJsApiTicket rootNode:" + rootNode);

            if (null != rootNode.get("ticket")) {

                // 缓存118分钟
                redisUtil.set(118*60, "jsapi_ticket", rootNode.get("ticket").textValue());

                return rootNode.get("ticket").textValue();
            }

        } catch(Exception e) {
            logger.error("getJsApiTicket redis error:" + e, e);
        }

        return null;
    }

    private static Integer toInt(String str) {

        if (str == null || str.equals("")) {
            return null;
        }
        return Integer.valueOf(str);
    }

    public static String SHA1(String decript) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            digest.update(decript.getBytes());
            byte messageDigest[] = digest.digest();
            // Create Hex String
            StringBuffer hexString = new StringBuffer();
            // 字节数组转换为 十六进制 数
            for (int i = 0; i < messageDigest.length; i++) {
                String shaHex = Integer.toHexString(messageDigest[i] & 0xFF);
                if (shaHex.length() < 2) {
                    hexString.append(0);
                }
                hexString.append(shaHex);
            }
            return hexString.toString();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static Map<String, String> getUserInfoAccessToken(String code) {

        JSONObject object = null;

        Map<String, String> data = new HashMap<>();

        try {

            PropertiesUtil propertiesUtil = PropertiesUtil.getInstance("system");

            String appId = propertiesUtil.getProperty("wei_xin_app_id");

            String secret = propertiesUtil.getProperty("wei_xin_app_secret");

            String url = String.format("https://api.weixin.qq.com/sns/oauth2/access_token?appid=%s&secret=%s&code=%s&grant_type=authorization_code",
                    appId, secret, code);
            logger.info("request accessToken from url: {}", url);

            logger.info(HttpClientUtil.httpsRequest(url, "GET", null));
//            DefaultHttpClient httpClient = new DefaultHttpClient();
//            HttpGet httpGet = new HttpGet(url);
//            HttpResponse httpResponse = httpClient.execute(httpGet);
//            HttpEntity httpEntity = httpResponse.getEntity();
//            String tokens = EntityUtils.toString(httpEntity, "utf-8");
//            Gson token_gson = new Gson();
//            object = token_gson.fromJson(tokens, JsonObject.class);
            logger.info("request accessToken success. [result={}]", object);
            data.put("openid", object.get("openid").toString().replaceAll("\"", ""));
            data.put("access_token", object.get("access_token").toString().replaceAll("\"", ""));
        } catch (Exception ex) {
            logger.error("fail to request wechat access token. [error={}]", ex);
        }
        return data;
    }

    public static Map<String, String> getUserInfo(String accessToken, String openId) {
        Map<String, String> data = new HashMap();
        String url = "https://api.weixin.qq.com/sns/userinfo?access_token=" + accessToken + "&openid=" + openId + "&lang=zh_CN";
        logger.info("request user info from url: {}", url);
        JSONObject userInfo = null;
        try {
//            DefaultHttpClient httpClient = new DefaultHttpClient();
//            HttpGet httpGet = new HttpGet(url);
//            HttpResponse httpResponse = httpClient.execute(httpGet);
//            HttpEntity httpEntity = httpResponse.getEntity();
//            String response = EntityUtils.toString(httpEntity, "utf-8");

            logger.info(HttpClientUtil.httpsRequest(url, "GET", null));

//            Gson token_gson = new Gson();
//            userInfo = token_gson.fromJson(response, JsonObject.class);
            logger.info("get userinfo success. [result={}]", userInfo);
            data.put("openid", userInfo.get("openid").toString().replaceAll("\"", ""));
            data.put("nickname", userInfo.get("nickname").toString().replaceAll("\"", ""));
            data.put("city", userInfo.get("city").toString().replaceAll("\"", ""));
            data.put("province", userInfo.get("province").toString().replaceAll("\"", ""));
            data.put("country", userInfo.get("country").toString().replaceAll("\"", ""));
            data.put("headimgurl", userInfo.get("headimgurl").toString().replaceAll("\"", ""));
        } catch (Exception ex) {
            logger.error("fail to request wechat user info. [error={}]", ex);
        }
        return data;
    }


    public static void main(String[] args) {

        PropertiesUtil propertiesUtil = PropertiesUtil.getInstance("system");

        String appId = propertiesUtil.getProperty("wei_xin_app_id");

        String requestUrl = propertiesUtil.getProperty("wei_xin_redirect_uri");

        String url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + appId + "&redirect_uri="
                + requestUrl + "&response_type=code&scope=snsapi_userinfo&state=toiletCat#wechat_redirect";

        System.out.println(HttpClientUtil.httpsRequest(url, "GET", null));
    }
}
