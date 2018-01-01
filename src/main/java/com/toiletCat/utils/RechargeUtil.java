package com.toiletCat.utils;

import com.alibaba.fastjson.JSONObject;
import com.toiletCat.constants.BaseConstant;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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

        PropertiesUtil propertiesUtil = new PropertiesUtil("system");

        TreeMap<String, String> treeMap = new TreeMap<>();

        treeMap.put("pid","");
        treeMap.put("type","");
        treeMap.put("out_trade_no","");
        treeMap.put("notify_url","");
        treeMap.put("return_url","");
        treeMap.put("name","");
        treeMap.put("money","");
        treeMap.put("sitename","");

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
     * @param returnUrl 返回url
     * @return
     */
    public static String getRequestUrl(String orderNo, String money, String returnUrl) {

        PropertiesUtil propertiesUtil = new PropertiesUtil("system");

        JSONObject json = new JSONObject();

        json.put("pid", propertiesUtil.getProperty("recharge_pid"));
        json.put("type", propertiesUtil.getProperty("recharge_type"));
        json.put("out_trade_no", orderNo);
        json.put("notify_url", propertiesUtil.getProperty("notify_url"));
        json.put("return_url", returnUrl);
        json.put("name", propertiesUtil.getProperty("recharge_name"));
        json.put("money", money);
        json.put("sitename", propertiesUtil.getProperty("recharge_sitename"));

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
     * 验签
     * @param resultSign 返回签名
     * @param json 参数
     * @return
     */
    public static Boolean checkSign(String resultSign, JSONObject json) {
        return resultSign.equals(getSign(json));
    }
}
