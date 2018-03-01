package com.toiletCat.controller;

import com.alibaba.fastjson.JSONObject;
import com.toiletCat.bean.CommonResult;
import com.toiletCat.constants.BaseConstant;
import com.toiletCat.entity.User;
import com.toiletCat.service.UserService;
import com.toiletCat.utils.*;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.awt.*;
import java.io.File;
import java.security.MessageDigest;
import java.util.Formatter;

@RequestMapping("/toiletCat/api/weChat")
@Controller
public class WeChatController {

    private static final Logger logger = LoggerFactory.getLogger(WeChatController.class);

    private static final float alpha = 0.9f;

    private static final String font = "宋体";

    private static final int fontStyle = Font.BOLD;

    private static final int fontSize = 100;

    private static final Color color = Color.decode("#824121");

    private static final int x = 600;

    private static final int y = 1306;

    private static final String imageFormat = "png";

    @Autowired
    private UserService userService;

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
     * 获得微信朋友圈分享图片
     * @param userNo 用户编号
     * @param inviteCode 用户邀请码
     * @return
     */
    @RequestMapping(value = "/getUserShareImgByUserNo", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String getUserShareImgByUserNo(String userNo, String inviteCode) {

        CommonResult<User> result = userService.getUserByUserNo(userNo);

        if(!result.success()) {

            return JSONUtil.getErrorJson();
        }

        if(result.getValue() == null) {

            return JSONUtil.getOtherMsgJson("用户不存在");
        }

        // 用户分享图片名  用户编号 + .png
        String userSharePng = userNo + ".png";

        PropertiesUtil propertiesUtil = PropertiesUtil.getInstance("system");

        // 获取微信分享图片存放路径
        String srcPath = propertiesUtil.getProperty("user_share_background_image");

        // 获取微信分享图片生成路径
        String toPath = propertiesUtil.getProperty("user_share_output_path");

        toPath += userSharePng;

        // 若图片已经存在则直接返回
        if(new File(toPath).exists()) {

            JSONObject json = new JSONObject();

            // 成功
            json.put("is_success", BaseConstant.SUCCESS);

            // 用户分享图片访问路径
            json.put("url", "http://www.9w83c6.cn/static/image/share/" + userSharePng);

            return json.toJSONString();
        }

        // 用户邀请码
        String inputWords = result.getValue().getInvitationCode();

        if(!inputWords.equals(inviteCode)) {

            return JSONUtil.getOtherMsgJson("邀请码错误");

        }

        if(ImageUtil.alphaWords2Image(srcPath, alpha, font, fontStyle, fontSize, color, inputWords, x, y, imageFormat,
                toPath)) {

            JSONObject json = new JSONObject();

            // 成功
            json.put("is_success", BaseConstant.SUCCESS);

            // 用户分享图片访问路径
            json.put("url", "http://www.9w83c6.cn/static/image/share/" + userSharePng);

            return json.toJSONString();
        }

        return JSONUtil.getErrorJson();
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
        signStr = String.format(signStr, jsApiTicket, nonce_str, timestamp, url);

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
