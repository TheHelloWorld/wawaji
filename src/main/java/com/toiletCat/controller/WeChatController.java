package com.toiletCat.controller;

import com.alibaba.fastjson.JSONObject;
import com.toiletCat.utils.PropertiesUtil;
import com.toiletCat.utils.UUIDUtil;
import com.toiletCat.utils.WeChatUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.MessageDigest;
import java.util.Formatter;

@RequestMapping("/toiletCat/api/weChat")
@Controller
public class WeChatController {

    private static final Logger logger = LoggerFactory.getLogger(WeChatController.class);

    /**
     * 获得微信朋友圈分享信息
     * @param url 链接
     * @return
     */
    @RequestMapping(value = "/getWeChatShareInfo", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String getWeChatShareInfo(String url) {

        String jsApiTicket;

        PropertiesUtil propertiesUtil = PropertiesUtil.getInstance("system");

        String appId = propertiesUtil.getProperty("we_chat_app_id");

        String appSecret = propertiesUtil.getProperty("we_chat_app_secret");

        String accessToken = WeChatUtil.getAccessToken(appId, appSecret);

        if(StringUtils.isBlank(accessToken)) {

            logger.warn("getWxShareInfo getAccessToken fail");

            return null;
        }

        logger.info("getWxShareInfo get access_token:" + accessToken);

        jsApiTicket = WeChatUtil.getJsApiTicket(accessToken);

        return weChatSign(jsApiTicket, url);
    }

    /**
     * 根据参数获得微信分享所需信息
     * @param jsApiTicket js ticket
     * @param url url
     * @return
     */
    private String weChatSign(String jsApiTicket, String url) {

        JSONObject json = new JSONObject();

        String nonce_str = UUIDUtil.generateUUID();

        String timestamp = create_timestamp();

        String signStr = "jsapi_ticket=%s&noncestr=%s&timestamp=%s&url=%s";

        String signature = "";

        // 注意这里参数名必须全部小写，且必须有序
        signStr =String.format(signStr, jsApiTicket, nonce_str, timestamp, url);

        logger.info("weChatSign sign: " + signStr);

        try {
            MessageDigest crypt = MessageDigest.getInstance("SHA-1");

            crypt.reset();

            crypt.update(signStr.getBytes("UTF-8"));

            signature = byteToHex(crypt.digest());

        } catch (Exception e) {

            logger.error("weChatSign error:" + e, e);
        }

        PropertiesUtil propertiesUtil = PropertiesUtil.getInstance("system");

        json.put("url", url);

        json.put("appId",propertiesUtil.getProperty("we_chat_app_id"));

        json.put("jsapi_ticket", jsApiTicket);

        json.put("nonceStr", nonce_str);

        json.put("timestamp", timestamp);

        json.put("signature", signature);

        logger.info("weChatSign url: "+ url);

        logger.info("weChatSign signature: " + signature);

        return json.toJSONString();
    }

    /**
     * 转换
     * @param hash hash数组
     * @return
     */
    private static String byteToHex(final byte[] hash) {

        Formatter formatter = new Formatter();

        for (byte b : hash) {

            formatter.format("%02x", b);
        }

        String result = formatter.toString();

        formatter.close();

        return result;
    }

    /**
     * 创建随机时间戳
     * @return
     */
    private static String create_timestamp() {

        return Long.toString(System.currentTimeMillis() / 1000);
    }

}
