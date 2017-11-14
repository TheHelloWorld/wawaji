package com.lzg.wawaji.controller;

import com.lzg.wawaji.service.UserService;
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
     * 用户登陆
     *
     * @param ticket    手机验证码
     * @param mobile    手机号
     * @param source    用户来源
     * @param sourceUrl 来源url
     * @return
     */
    @RequestMapping(value = "/userLogin", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String userLogin(String ticket, String mobile, String source, String sourceUrl) {
        return null;
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
    public String userPlay(String userNo,String machineNo){
        return userService.userPlay(userNo, machineNo);
    }
}
