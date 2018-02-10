package com.toiletCat.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.JsonNode;
import com.toiletCat.bean.WxUserInfo;
import com.toiletCat.constants.BaseConstant;
import com.toiletCat.constants.RedisConstant;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;

public class WeChatUtil {

    private static final Logger logger = LoggerFactory.getLogger(WeChatUtil.class);

    // 凭证获取（GET）——access_token
    private final static String ACCESS_TOKEN_URL =
            "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s";

    // 微信JSSDK的ticket请求URL地址——jsapi_ticket
    private final static String JSAPI_TICKET_URL =
            "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=%s&type=jsapi";
    // 微信获取code url
    private final static String REQUEST_USER_CODE_URL =
            "https://open.weixin.qq.com/connect/oauth2/authorize?" +
                    "appid=%s&redirect_uri=%s&response_type=code&scope=snsapi_base&state=toiletCat#wechat_redirect";
    // 微信获取用户open_id url
    private final static String REQUEST_USER_OPEN_ID_URL = "https://api.weixin.qq.com/sns/oauth2/access_token?" +
            "appid=%s&secret=%s&code=%s&grant_type=authorization_code";

    // 微信获取用户信息url
    private final static String REQUEST_USER_INFO_URL = "https://api.weixin.qq.com/sns/userinfo?" +
            "access_token=%s&openid=%s&lang=zh_CN";
    /**
     * 获取接口访问凭证
     *
     * @param app_id 凭证
     * @param app_secret 密钥
     * @return
     */
    public static String getAccessToken(String app_id, String app_secret) {

        try(RedisUtil redisUtil = new RedisUtil(RedisConstant.REDIS)) {

            String accessToken = redisUtil.get(RedisConstant.WX_SHARE_ACCESS_TOKEN);

            if(StringUtils.isNotBlank(accessToken)) {

                return accessToken;
            }

            String requestUrl = String.format(ACCESS_TOKEN_URL, app_id, app_secret);

            logger.info("getAccessToken requestUrl:" + requestUrl);

            // 发起GET请求获取凭证
            JsonNode rootNode = HttpClientUtil.wxHttpsRequest(requestUrl, BaseConstant.HTTP_GET, null);

            logger.info("getAccessToken rootNode:" + rootNode);

            if (rootNode.get("access_token") != null) {

                // 缓存118分钟
                redisUtil.set(RedisConstant.WX_EXPIRE_TIME, RedisConstant.WX_SHARE_ACCESS_TOKEN,
                        rootNode.get("access_token").textValue());

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

            String jsApiTicket = redisUtil.get(RedisConstant.WX_SHARE_JS_API_TICKET);

            if(StringUtils.isNotBlank(jsApiTicket)) {

                return jsApiTicket;
            }

            String requestUrl = String.format(JSAPI_TICKET_URL, access_token);

            // 发起GET请求获取凭证
            JsonNode rootNode = HttpClientUtil.wxHttpsRequest(requestUrl, BaseConstant.HTTP_GET, null);

            logger.info("getJsApiTicket rootNode:" + rootNode);

            if (rootNode.get("ticket") != null) {

                // 缓存118分钟
                redisUtil.set(RedisConstant.WX_EXPIRE_TIME, RedisConstant.WX_SHARE_JS_API_TICKET,
                        rootNode.get("ticket").textValue());

                return rootNode.get("ticket").textValue();
            }

        } catch(Exception e) {

            logger.error("getJsApiTicket redis error:" + e, e);
        }

        return null;
    }

    /**
     * 生成 MD5
     *
     * @param data 待处理数据
     * @return MD5结果
     */
    private static String WeChatMD5(String data) throws Exception {

        java.security.MessageDigest md = MessageDigest.getInstance("MD5");

        byte[] array = md.digest(data.getBytes("UTF-8"));

        StringBuilder sb = new StringBuilder();

        for (byte item : array) {

            sb.append(Integer.toHexString((item & 0xFF) | 0x100).substring(1, 3));
        }

        return sb.toString().toUpperCase();
    }

    /**
     * 根据code获得用户微信信息
     * @param code code
     * @return
     */
    public static Map<String, String> getUserInfoAccessToken(String code) {

        JSONObject object;

        Map<String, String> data = new HashMap<>();

        try {

            PropertiesUtil propertiesUtil = PropertiesUtil.getInstance("system");

            String appId = propertiesUtil.getProperty("we_chat_app_id");

            String secret = propertiesUtil.getProperty("we_chat_app_secret");

            String url = String.format(REQUEST_USER_OPEN_ID_URL, appId, secret, code);

            logger.info("getUserInfoAccessToken accessToken request url: " + url);

            String response = HttpClientUtil.httpsRequest(url, BaseConstant.HTTP_GET, null);

            logger.info("getUserInfoAccessToken accessToken response: " + response);

            object = JSON.parseObject(response);

            logger.info("getUserInfoAccessToken accessToken success result: " + object);

            data.put("openid", object.getString("openid").replaceAll("\"", ""));

            data.put("access_token", object.getString("access_token").replaceAll("\"", ""));

        } catch (Exception e) {

            logger.error("getUserInfoAccessToken error:" + e, e);
        }

        return data;
    }

    /**
     * 获得微信用户信息
     * @param accessToken token
     * @param openId 用户openId
     * @return
     */
    public static WxUserInfo getUserInfo(String accessToken, String openId) {

        WxUserInfo wxUserInfo = new WxUserInfo();

        String url = String.format(REQUEST_USER_INFO_URL, accessToken, openId);

        logger.info("getUserInfo user info request url: " + url);

        JSONObject userInfo;

        try {

            String response = HttpClientUtil.httpsRequest(url, BaseConstant.HTTP_GET, null);

            logger.info("getUserInfo user info response url: " + response);

            userInfo = JSON.parseObject(response);

            logger.info("getUserInfo get userInfo success result: " + userInfo);

            wxUserInfo.setOpenId(userInfo.getString("openid").replaceAll("\"", ""));

            String nickName = userInfo.getString("nickname");

            // 判断昵称是否为空
            if(StringUtils.isBlank(nickName)) {

                nickName = BaseConstant.DEFAULT_NAME_FLAG;

            } else {

                nickName = nickName.replaceAll("\"", "");
            }

            wxUserInfo.setNickName(nickName);

            wxUserInfo.setCity(userInfo.getString("city").replaceAll("\"", ""));

            wxUserInfo.setProvince(userInfo.getString("province").replaceAll("\"", ""));

            wxUserInfo.setCountry(userInfo.getString("country").replaceAll("\"", ""));

            String headImageUrl = userInfo.getString("headimgurl");

            // 判断用户头像是否为空
            if(StringUtils.isBlank(headImageUrl)) {

                headImageUrl = BaseConstant.DEFAULT_HEAD_FLAG;

            } else {

                headImageUrl = headImageUrl.replaceAll("\"", "");
            }

            wxUserInfo.setHeadImgUrl(headImageUrl);

        } catch (Exception e) {

            logger.error("getUserInfo error:" + e, e);
        }

        return wxUserInfo;
    }

    public static void main(String[] args) {

        PropertiesUtil propertiesUtil = PropertiesUtil.getInstance("system");

        String appId = propertiesUtil.getProperty("we_chat_app_id");

        String requestUrl = propertiesUtil.getProperty("we_chat_redirect_uri");

        String url = String.format(REQUEST_USER_CODE_URL, appId, requestUrl);

        System.out.println(url);
    }
}
