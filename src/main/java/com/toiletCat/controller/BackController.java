package com.toiletCat.controller;

import com.alibaba.fastjson.JSONObject;
import com.toiletCat.constants.BaseConstant;
import com.toiletCat.utils.JSONUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping("/toiletCat/api/back")
@Controller
public class BackController {

    private static final Logger logger = LoggerFactory.getLogger(BackController.class);

    /**
     * 后台登录登陆
     * @param loginName 登录名
     * @param password 密码
     * @return
     */
    @RequestMapping(value = "/backLogin", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String backLogin(String loginName, String password) {

        logger.info("backLogin loginName:" + loginName + ", password:" + password);

        // 判断用户名密码是否正确
        if(BaseConstant.BACK_USER_NAME.equals(loginName) && BaseConstant.BACK_USER_PASSWORD.equals(password)) {

            JSONObject json = new JSONObject();

            json.put("result_flag", "login_success");
            json.put("result_msg", "toiletCat_true");

            return JSONUtil.getSuccessReturnJSON(json);
        }

        return JSONUtil.getSuccessReturnJSON("login_fail");
    }

}
