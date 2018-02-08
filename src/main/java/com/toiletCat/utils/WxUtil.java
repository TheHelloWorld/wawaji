package com.toiletCat.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.toiletCat.constants.BaseConstant;
import com.toiletCat.constants.RedisConstant;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.MessageDigest;

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

    public static void main(String[] args) {

        PropertiesUtil propertiesUtil = PropertiesUtil.getInstance("system");

        String appId = propertiesUtil.getProperty("wei_xin_app_id");

        String requestUrl = propertiesUtil.getProperty("wei_xin_redirect_uri");

        String  url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + appId + "&redirect_uri="
                + requestUrl + "&response_type=code&scope=snsapi_userinfo&state=toiletCat#wechat_redirect";

        System.out.println(HttpClientUtil.httpsRequest(url, "GET", null));
    }
}
