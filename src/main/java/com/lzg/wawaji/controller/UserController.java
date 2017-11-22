package com.lzg.wawaji.controller;

import com.lzg.wawaji.bean.CommonResult;
import com.lzg.wawaji.constants.BaseConstant;
import com.lzg.wawaji.service.UserService;
import com.lzg.wawaji.utils.PropertiesUtil;
import com.lzg.wawaji.utils.Random;
import com.lzg.wawaji.utils.RedisUtil;
import com.lzg.wawaji.utils.SDKTestSendTemplateSMS;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping("/wawaji/user")
@Controller
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;



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


        return null;
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
            return result.getValue();
        }

        return BaseConstant.FAIL;

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
           return result.getValue();
        }

        return BaseConstant.FAIL;
    }
}
