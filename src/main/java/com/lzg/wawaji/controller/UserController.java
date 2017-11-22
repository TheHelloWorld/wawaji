package com.lzg.wawaji.controller;

import com.lzg.wawaji.bean.CommonResult;
import com.lzg.wawaji.constants.BaseConstant;
import com.lzg.wawaji.entity.User;
import com.lzg.wawaji.service.UserService;
import com.lzg.wawaji.utils.*;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@RequestMapping("/wawaji/user")
@Controller
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    /**
     * 用户自动登陆
     *
     * @return
     */
    @RequestMapping(value = "/autoLogin", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String autoLogin() {

        // 从cookie中获取userNo
        String userNo = CommonHandle.getCookieValue("userNo");

        if(StringUtils.isBlank(userNo)) {
            JSONUtil.getSuccessReturnJSON(BaseConstant.FAIL);
        }

        CommonResult<User> result = userService.getUserByUserNo(userNo);

        if(result.success()) {

            if(result.getValue() == null) {
                logger.warn("wawaji warn: 没有对应的用户编号 userNO:{}", userNo);
                JSONUtil.getSuccessReturnJSON(BaseConstant.FAIL);
            }

            return JSONUtil.getSuccessReturnJSON(result.getValue());
        }

        return JSONUtil.getErrorJson();
    }

    /**
     * 用户登录(若用户不存在则添加)
     *
     * @param ticket    手机验证码
     * @param mobile    手机号
     * @return
     */
    @RequestMapping(value = "/registerOrLoginUser", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String registerOrLoginUser(String ticket, String mobile) {

        CommonResult<String> verifyResult = userService.verifyCode(ticket, mobile);

        // 系统异常
        if(!verifyResult.success()) {
            return JSONUtil.getErrorJson();
        }

        // 若短信验证码不通过
        if(!BaseConstant.SUCCESS.equals(verifyResult.getValue())) {
            // 此处前端需特殊判断
            return JSONUtil.getSuccessReturnJSON(verifyResult.getValue());
        }

        // 获取用户信息并返回
        CommonResult<User> result = userService.registerOrLoginUser(mobile);

        if(result.success()) {

            setUserNoInCookie(result.getValue().getUserNo());
            return JSONUtil.getSuccessReturnJSON(result.getValue());
        }

        return JSONUtil.getErrorJson();
    }

    /**
     * 将用户userNo放入cookie
     *
     * @param userNo 用户编号
     */
    private void setUserNoInCookie(String userNo) {

        //将用户ID放入COOKIE中
        HttpServletResponse response = CommonHandle.getResponse();

        Cookie passport = new Cookie("userNo", userNo);
        logger.info("用户userNo放入cookie中,userNo:{}", userNo);
        //设定有效时间  以秒(s)为单位
        passport.setMaxAge(25920000);
        //设置Cookie路径和域名
        passport.setPath("/");
        response.addCookie(passport);
    }

    /**
     * 发送短信验证码
     * @param mobileNo 手机号
     * @return
     */
    @RequestMapping(value = "/sendMobileVerificationCode", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String sendMobileVerificationCode(String mobileNo) {

        CommonResult<String> result = userService.sendMobileVerificationCode(mobileNo);

        if(result.success()) {
            return JSONUtil.getSuccessReturnJSON(result.getValue());
        }

        return JSONUtil.getErrorJson();
    }

    /**
     * 用户玩游戏
     *
     * @param userNo    用户编号
     * @param machineNo 机器编号
     * @return
     */
    @RequestMapping(value = "/userPlay", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String userPlay(String userNo,String machineNo) {

        CommonResult<String> result = userService.userPlay(userNo, machineNo);

        if(result.success()) {
            return JSONUtil.getSuccessReturnJSON(result.getValue());
        }

        return JSONUtil.getErrorJson();
    }
}
