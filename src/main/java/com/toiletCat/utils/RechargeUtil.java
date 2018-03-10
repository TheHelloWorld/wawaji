package com.toiletCat.utils;

import com.alibaba.fastjson.JSONObject;
import com.toiletCat.constants.BaseConstant;
import com.toiletCat.constants.RechargeConstant;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.TreeMap;

/**
 * 充值工具类
 */
public class RechargeUtil {

    private static final Logger logger = LoggerFactory.getLogger(RechargeUtil.class);

    private RechargeUtil() {
        
    }

    /**
     * md5加密
     * @param s 要加密的字符串
     * @return
     */
    private static String GetMd5(String s) {
        char hexDigits[] = {
                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'a', 'b', 'c', 'd', 'e', 'f'
        };
        char str[];
        byte strTemp[] = s.getBytes();
        try {
            MessageDigest mdTemp = MessageDigest.getInstance("MD5");
            mdTemp.update(strTemp);
            byte md[] = mdTemp.digest();
            int j = md.length;
            str = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++)
            {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }

            return new String(str);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 获得签名
     * @param json 请求参数
     * @return
     */
    private static String getSign(JSONObject json) {

        PropertiesUtil propertiesUtil = PropertiesUtil.getInstance("system");

        TreeMap<String, String> treeMap = new TreeMap<>();

        treeMap.put("pid", "");
        treeMap.put("type", "");
        treeMap.put("out_trade_no", "");
        treeMap.put("trade_no", "");
        treeMap.put("notify_url", "");
        treeMap.put("return_url", "");
        treeMap.put("name", "");
        treeMap.put("money", "");
        treeMap.put("sitename", "");
        treeMap.put("trade_status", "");

        for(String key : treeMap.keySet()) {
            if(StringUtils.isBlank(json.getString(key))) {
                continue;
            }
            treeMap.put(key, json.getString(key));
        }

        StringBuilder stringBuilder = new StringBuilder();

        for(String key : treeMap.keySet()) {
            if(StringUtils.isBlank(treeMap.get(key))) {
                continue;
            }
            stringBuilder.append(key);
            stringBuilder.append("=");
            stringBuilder.append(treeMap.get(key));
            stringBuilder.append("&");
        }

        String str = stringBuilder.toString();

        str = str.substring(0, str.length()-1);

        String key = propertiesUtil.getProperty("recharge_key");

        str += key;

        return GetMd5(str);
    }

    /**
     * 获得支付请求url
     * @param orderNo 我方订单号
     * @param money 支付金额
     * @return
     */
    public static String getRequestUrl(String orderNo, String money) {

        PropertiesUtil propertiesUtil = PropertiesUtil.getInstance("system");

        JSONObject json = new JSONObject();

        json.put("pid", propertiesUtil.getProperty("recharge_pid"));
        json.put("type", propertiesUtil.getProperty("recharge_type"));
        json.put("out_trade_no", orderNo);
        json.put("notify_url", propertiesUtil.getProperty("recharge_notify_url"));
        json.put("return_url", propertiesUtil.getProperty("recharge_return_url"));
        json.put("name", propertiesUtil.getProperty("recharge_name"));
        json.put("money", money);
        json.put("sitename", propertiesUtil.getProperty("recharge_site_name"));

        String sign = getSign(json);

        logger.info(BaseConstant.LOG_MSG + " request sign:" + sign);

        String url = propertiesUtil.getProperty("recharge_url");

        StringBuilder request = new StringBuilder();

        request.append(url);
        request.append("?pid=");
        request.append(json.getString("pid"));
        request.append("&type=");
        request.append(json.getString("type"));
        request.append("&out_trade_no=");
        request.append(json.getString("out_trade_no"));
        request.append("&notify_url=");
        request.append(json.getString("notify_url"));
        request.append("&return_url=");
        request.append(json.getString("return_url"));
        request.append("&name=");
        request.append(json.getString("name"));
        request.append("&money=");
        request.append(json.getString("money"));
        request.append("&sitename=");
        request.append(json.getString("sitename"));
        request.append("&sign=");
        request.append(sign);
        request.append("&sign_type=MD5");

        logger.info(BaseConstant.LOG_MSG + " request url:" + request);

        return request.toString();

    }

    /**
     * 获得微信支付前端所需参数
     * @param orderNo 我方订单号(ToiletCat + 时间戳)
     * @param money 交易金额
     * @param openId 用户openId
     * @param ip 用户ip
     * @param type 类型
     * @return
     */
    public static String getWxPayRequestInfo(String orderNo, String money, String openId, String ip, String type) {

        logger.info("getWxPayRequestInfo param: orderNo:" + orderNo + ", money:" + money + ", openId:" + openId +
                ", ip:" + ip);

        try {

            PropertiesUtil propertiesUtil = PropertiesUtil.getInstance("system");

            JSONObject json = new JSONObject();

            String appId = propertiesUtil.getProperty("we_chat_app_id");

            String mch_id = propertiesUtil.getProperty("we_chat_merchant_no");

            String trade_type = "JSAPI";

            // 若是app调用支付请求
            if(type.startsWith("app")) {

                appId = propertiesUtil.getProperty("we_chat_app_app_id");

                mch_id = propertiesUtil.getProperty("we_chat_app_merchant_no");

                trade_type = "APP";
            }

            // appId
            json.put("appid", appId);

            // 商户编号(微信分配)
            json.put("mch_id", mch_id);

            // 随机字符串
            json.put("nonce_str", WeChatUtil.generateUUID());

            // 商品描述
            json.put("body", "马桶猫抓娃娃-游戏充值");

            // 我方订单号
            json.put("out_trade_no", orderNo);

            // 交易金额(单位为分)
            json.put("total_fee", (int)(Math.rint(Double.valueOf(money) * 100)));

            // 终端ip
            json.put("spbill_create_ip", ip);

            // 结果异步通知地址
            json.put("notify_url", propertiesUtil.getProperty("recharge_notify_url"));

            // 交易类型
            json.put("trade_type", trade_type);

            // 用户openId
            json.put("openid", openId);

            Map<String, String> map = new TreeMap<>();

            System.out.println(json);

            map.put("sign", WeChatUtil.weChatSign(json));

            for(String key : json.keySet()) {

                map.put(key, json.getString(key));
            }

            logger.info(WeChatUtil.mapToXml(map));

            String response = HttpClientUtil.httpsRequest(RechargeConstant.WE_CHAT_REQUEST_URL, BaseConstant.HTTP_POST,
                    WeChatUtil.mapToXml(map));

            System.out.println(response);

            logger.info("response is :" + response);

            Map<String, String> responseMap = WeChatUtil.xmlToMap(response);

            if(responseMap == null) {

                logger.error("getWxPayRequestInfo responseMap is null");

                return null;
            }

            String prepay_id = "";

            if("SUCCESS".equals(responseMap.get("result_code")) && "SUCCESS".equals(responseMap.get("return_code"))) {

                prepay_id = responseMap.get("prepay_id");

            }

            if(StringUtils.isBlank(prepay_id)) {

                logger.error("getWxPayRequestInfo prepay_id is null");

                return null;
            }

            JSONObject returnJson = new JSONObject();

            if(type.startsWith("app")) {

                returnJson.put("appId", appId);

                returnJson.put("partnerid", propertiesUtil.getProperty("we_chat_merchant_no"));

                returnJson.put("prepayid", prepay_id);

                returnJson.put("package", "Sign=WXPay");

                returnJson.put("packageValue", "Sign=WXPay");

                returnJson.put("nonceStr", WeChatUtil.generateUUID());

                returnJson.put("timeStamp", WeChatUtil.getCurrentTimestamp());

                String paySign = WeChatUtil.weChatSign(returnJson);

                returnJson.put("sign", paySign);

            } else {

                returnJson.put("appId", appId);

                returnJson.put("timeStamp", WeChatUtil.getCurrentTimestamp());

                returnJson.put("nonceStr", WeChatUtil.generateUUID());

                returnJson.put("package", "prepay_id=" + prepay_id);

                returnJson.put("signType", "MD5");

                String paySign = WeChatUtil.weChatSign(returnJson);

                returnJson.put("paySign", paySign);

                returnJson.put("orderNo", orderNo);
            }

            return returnJson.toJSONString();

        } catch(Exception e) {

            logger.error("getWxPayRequestInfo error:" + e.getMessage(), e);

            return null;
        }
    }

    /**
     * 获得微信支付订单查询参数
     * @param orderNo 我方订单号(ToiletCat + 时间戳)
     * @return
     */
    public static Map<String, String> getWxPayQueryRequestInfo(String orderNo) {

        try {
            PropertiesUtil propertiesUtil = PropertiesUtil.getInstance("system");

            JSONObject json = new JSONObject();

            String appId = propertiesUtil.getProperty("we_chat_app_id");

            // appId
            json.put("appid", appId);

            // 商户编号(微信分配)
            json.put("mch_id", propertiesUtil.getProperty("we_chat_merchant_no"));

            // 随机字符串
            json.put("nonce_str", WeChatUtil.generateUUID());

            // 我方订单号
            json.put("out_trade_no", orderNo);

            Map<String, String> map = new TreeMap<>();

            map.put("sign", WeChatUtil.weChatSign(json));

            for(String key : json.keySet()) {

                map.put(key, json.getString(key));
            }

            String xmlStr = WeChatUtil.mapToXml(map);

            logger.info(xmlStr);

            String response = HttpClientUtil.httpsRequest(RechargeConstant.QUERY_ORDER_URL, BaseConstant.HTTP_POST,
                    xmlStr);

            logger.info("response is :" + response);

            Map<String, String> responseMap = WeChatUtil.xmlToMap(response);

            if(responseMap == null) {

                logger.error("getWxPayQueryRequestInfo responseMap is null");

                return null;
            }

            return responseMap;

        } catch(Exception e) {

            logger.error("createWxPayRequest error:" + e.getMessage(), e);

            return null;
        }
    }

    /**
     * 验签
     * @param resultSign 返回签名
     * @param json 参数
     * @return
     */
    public static Boolean checkSign(String resultSign, JSONObject json) {

        return resultSign.equals(WeChatUtil.weChatSign(json));
    }
}
