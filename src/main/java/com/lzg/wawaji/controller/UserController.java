package com.lzg.wawaji.controller;

import com.alibaba.fastjson.JSONObject;
import com.lzg.wawaji.constants.BaseConstant;
import com.lzg.wawaji.service.UserService;
import com.lzg.wawaji.utils.CommonHandle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@RequestMapping("/wawaji/user")
@Controller
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    /**
     * 手机验证码登录
     *
     * @param ticket    手机验证码
     * @param mobile    手机号
     * @param source    用户来源
     * @param sourceUrl 来源url
     * @return
     */
    @RequestMapping(value = "/loginByTicket", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String loginByTicket(String ticket, String mobile, String source, String sourceUrl) {
        return null;
    }

    /**
     * 将用户uuid放入cookie
     *
     * @param uuid   uuid
     * @param mobile 用户手机号
     */
    private void setUUIDInCookie(String uuid, String mobile) {
        // 将用户信息放入cookie
        HttpSession session = CommonHandle.getSession();
        session.setAttribute("mobile", mobile);
        //将用户ID放入COOKIE中
        HttpServletResponse response = CommonHandle.getResponse();

        Cookie passport = new Cookie("uuid", uuid);
        logger.info("用户uuid放入cookie中,uuid:{}", uuid);
        //设定有效时间  以秒(s)为单位
        passport.setMaxAge(25920000);
        //设置Cookie路径和域名
        passport.setPath("/");
        response.addCookie(passport);
    }



    /**
     * 获得验证错误json
     *
     * @param result 错误原因
     * @return
     */
    private String getReturnValidateErrJson(String result) {
        JSONObject returnJson = new JSONObject();
        returnJson.put(BaseConstant.REASON, result);
        returnJson.put(BaseConstant.SUCCESS, false);
        return returnJson.toJSONString();
    }

    /**
     * 获取Ip
     *
     * @param request 请求
     * @return
     * @throws Exception
     */
    private static String getUserIp(HttpServletRequest request) throws Exception {
        String ip = "";

        if (request.getHeader("x-forwarded-for") != null && (!request.getHeader("x-forwarded-for").trim().equals("")
        )) {
            ip = request.getHeader("x-forwarded-for").split(",")[0];
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}
