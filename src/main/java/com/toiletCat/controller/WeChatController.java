package com.toiletCat.controller;

import com.alibaba.fastjson.JSONObject;
import com.toiletCat.constants.BaseConstant;
import com.toiletCat.utils.JSONUtil;
import com.toiletCat.utils.UUIDUtil;
import com.toiletCat.utils.WxConfig;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

@RequestMapping("/toiletCat/api/weChat")
@Controller
public class WeChatController {

    private static final Logger logger = LoggerFactory.getLogger(WeChatController.class);

    private final static String token = "syncoToken";

    protected String doGet(HttpServletRequest request) throws Exception{
        System.out.println("开始签名校验");
        String signature = request.getParameter("signature");
        String timestamp = request.getParameter("timestamp");
        String nonce = request.getParameter("nonce");
        String echostr = request.getParameter("echostr");

        System.out.println("signature=" + signature);
        System.out.println("timestamp=" + timestamp);
        System.out.println("nonce=" + nonce);
        System.out.println("echostr=" + echostr);

        // 排序
        String sortString = sort(token, timestamp, nonce);
        // 加密
        String myToken = WxConfig.SHA1(sortString);
        // 校验签名
        if (StringUtils.isNotBlank(myToken) && myToken.equals(signature)) {
            System.out.println("签名校验通过。");
            // 如果检验成功输出echostr，微信服务器接收到此输出，才会确认检验完成。
            return echostr;

        } else {
            System.out.println("签名校验失败。");
        }
        return "fail";
    }

    /**
     * 排序方法
     *
     * @param token
     * @param timestamp
     * @param nonce
     * @return
     */
    public static String sort(String token, String timestamp, String nonce) {
        String[] strArray = { token, timestamp, nonce };
        Arrays.sort(strArray);

        StringBuilder builder = new StringBuilder();
        for (String str : strArray) {
            builder.append(str);
        }

        return builder.toString();
    }

}
