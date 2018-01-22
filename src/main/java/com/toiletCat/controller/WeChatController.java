package com.toiletCat.controller;

import com.toiletCat.constants.BaseConstant;
import com.toiletCat.utils.PropertiesUtil;
import com.toiletCat.utils.RedisUtil;
import com.toiletCat.utils.UUIDUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.MessageDigest;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Map;

@RequestMapping("/toiletCat/api/weChat")
@Controller
public class WeChatController {

    private static final Logger logger = LoggerFactory.getLogger(WeChatController.class);


    @RequestMapping("/initWXJSInterface")
    @ResponseBody
    public Map<String, String> init(String url) {

        String jsapi_ticket = null;

        try (RedisUtil redisUtil = new RedisUtil(BaseConstant.REDIS)) {

            jsapi_ticket = redisUtil.get("jsapi_ticket");

        } catch (Exception e) {
            logger.error("init redis error:" + e, e);
        }

        Map<String, String> ret = sign(jsapi_ticket, url);

        logger.info("init currurl = "+ url);

        logger.info("init signature =" + ret.get("signature"));

        return ret;
    }

    public Map<String, String> sign(String jsapi_ticket, String url) {

        Map<String, String> ret = new HashMap<>();

        String nonce_str = UUIDUtil.generateUUID();

        String timestamp = create_timestamp();

        String string1;

        String signature = "";

        // 注意这里参数名必须全部小写，且必须有序
        string1 = "jsapi_ticket=" + jsapi_ticket +
                "&noncestr=" + nonce_str +
                "×tamp=" + timestamp +
                "&url=" + url;

        logger.info("sign "+string1);

        try {
            MessageDigest crypt = MessageDigest.getInstance("SHA-1");

            crypt.reset();

            crypt.update(string1.getBytes("UTF-8"));

            signature = byteToHex(crypt.digest());

        } catch (Exception e) {
            e.printStackTrace();
        }

        PropertiesUtil propertiesUtil = PropertiesUtil.getInstance("system");

        ret.put("url", url);

        ret.put("appId",propertiesUtil.getProperty("wei_xin_app_id"));

        ret.put("jsapi_ticket", jsapi_ticket);

        ret.put("nonceStr", nonce_str);

        ret.put("timestamp", timestamp);

        ret.put("signature", signature);

        return ret;
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
