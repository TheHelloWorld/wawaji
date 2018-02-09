package com.toiletCat.controller;

import com.alibaba.fastjson.JSONObject;
import com.toiletCat.constants.BaseConstant;
import com.toiletCat.utils.CommonHandle;
import com.toiletCat.utils.PropertiesUtil;
import com.toiletCat.utils.UUIDUtil;
import com.toiletCat.utils.WxUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.MessageDigest;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Map;

@RequestMapping("/toiletCat/api/weChat")
@Controller
public class WeChatController {

    private static final Logger logger = LoggerFactory.getLogger(WeChatController.class);

    /**
     * 获得微信朋友圈分享信息
     * @param url 链接
     * @return
     */
    @RequestMapping(value = "/getWxShareInfo", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String getWxShareInfo(String url) {

        String jsApiTicket;

        PropertiesUtil propertiesUtil = PropertiesUtil.getInstance("system");

        String appId = propertiesUtil.getProperty("wei_xin_app_id");

        String appSecret = propertiesUtil.getProperty("wei_xin_app_secret");

        String accessToken = WxUtil.getAccessToken(appId, appSecret);

        if(StringUtils.isBlank(accessToken)) {

            logger.warn("getWxShareInfo getAccessToken fail");

            return null;
        }

        logger.info("getWxShareInfo get access_token:" + accessToken);

        jsApiTicket = WxUtil.getJsApiTicket(accessToken);

        return wxSign(jsApiTicket, url);
    }

    /**
     * 根据参数获得微信分享所需信息
     * @param jsApiTicket js ticket
     * @param url url
     * @return
     */
    private String wxSign(String jsApiTicket, String url) {

        JSONObject json = new JSONObject();

        String nonce_str = UUIDUtil.generateUUID();

        String timestamp = create_timestamp();

        String string1;

        String signature = "";

        // 注意这里参数名必须全部小写，且必须有序
        string1 = "jsapi_ticket=" + jsApiTicket +
                "&noncestr=" + nonce_str +
                "&timestamp=" + timestamp +
                "&url=" + url;

        logger.info("wxSign sign "+string1);

        try {
            MessageDigest crypt = MessageDigest.getInstance("SHA-1");

            crypt.reset();

            crypt.update(string1.getBytes("UTF-8"));

            signature = byteToHex(crypt.digest());

        } catch (Exception e) {
            e.printStackTrace();
        }

        PropertiesUtil propertiesUtil = PropertiesUtil.getInstance("system");

        json.put("url", url);

        json.put("appId",propertiesUtil.getProperty("wei_xin_app_id"));

        json.put("jsapi_ticket", jsApiTicket);

        json.put("nonceStr", nonce_str);

        json.put("timestamp", timestamp);

        json.put("signature", signature);

        logger.info("wxSign currurl = "+ url);

        logger.info("wxSign signature =" + signature);

        return json.toJSONString();
    }

    private static String byteToHex(final byte[] hash) {

        Formatter formatter = new Formatter();

        for (byte b : hash) {

            formatter.format("%02x", b);
        }

        String result = formatter.toString();

        formatter.close();

        return result;
    }

    private static String create_timestamp() {

        return Long.toString(System.currentTimeMillis() / 1000);
    }

}
