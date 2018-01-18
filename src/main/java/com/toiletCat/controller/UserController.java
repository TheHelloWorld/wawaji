package com.toiletCat.controller;

import com.toiletCat.bean.CommonResult;
import com.toiletCat.constants.BaseConstant;
import com.toiletCat.entity.User;
import com.toiletCat.service.UserService;
import com.toiletCat.utils.CommonHandle;
import com.toiletCat.utils.JSONUtil;
import org.apache.commons.lang.StringUtils;
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

@RequestMapping("/toiletCat/api/user")
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
    public String autoLogin(HttpServletRequest request, HttpServletResponse response) {

        // 从cookie中获取userNo
        String userNo = CommonHandle.getCookieValue(request, BaseConstant.COOKIE_USER_NO);

        if(StringUtils.isBlank(userNo) || "null".equals(userNo)) {
            return JSONUtil.getSuccessReturnJSON(BaseConstant.FAIL);
        }

        CommonResult<User> result = userService.getUserByUserNo(userNo);

        if(result.success()) {

            if(result.getValue() == null) {
                logger.warn("toiletCat warn: 没有对应的用户编号 userNO:{}", userNo);
                return JSONUtil.getSuccessReturnJSON(BaseConstant.FAIL);
            }

            // 将用户编号放入cookie中
            setUserNoInCookie(response, result.getValue().getUserNo());

            return JSONUtil.getSuccessReturnJSON(result.getValue());
        }

        return JSONUtil.getErrorJson();
    }

    /**
     * 用户登录(若用户不存在则添加)
     *
     * @param ticket 手机验证码
     * @param mobile 手机号
     * @return
     */
    @RequestMapping(value = "/registerOrLoginUser", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String registerOrLoginUser(String ticket, String mobile, HttpServletResponse response) {

        CommonResult<String> verifyResult = userService.verifyCode(ticket, mobile);

        // 系统异常
        if(verifyResult.isSysError()) {
            return JSONUtil.getErrorJson();
        }

        // 若短信验证码不通过
        if(verifyResult.otherMsg()) {
            return JSONUtil.getOtherMsgJson(verifyResult.getValue());
        }

        // 获取用户编号并返回
        CommonResult<User> result = userService.registerOrLoginUser(mobile);

        if(result.success()) {

            // 将用户编号放入cookie中
            setUserNoInCookie(response, result.getValue().getUserNo());

            return JSONUtil.getSuccessReturnJSON(result.getValue());
        }

        return JSONUtil.getErrorJson();
    }

    /**
     * 将用户userNo放入cookie
     *
     * @param userNo 用户编号
     */
    private void setUserNoInCookie(HttpServletResponse response, String userNo) {

        //将用户No放入COOKIE中
        Cookie passport = new Cookie(BaseConstant.COOKIE_USER_NO, userNo);

        logger.info("用户userNo放入cookie中,userNo:{}", userNo);

        //设定有效时间  以秒(s)为单位
        passport.setMaxAge(25920000);

        //设置Cookie路径和域名
        passport.setPath("/");

        response.addCookie(passport);
    }

    /**
     * 根据用户编号获得用户信息
     * @param userNo 用户编号
     * @return
     */
    @RequestMapping(value = "/getUserByUserNo", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String getUserByUserNo(String userNo) {

        CommonResult<User> result = userService.getUserByUserNo(userNo);

        return JSONUtil.getReturnBeanString(result);
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

        return JSONUtil.getReturnBeanString(result);
    }

    /**
     * 用户玩游戏(娃娃机)
     *
     * @param userNo    用户编号
     * @param machineNo 机器编号
     * @return
     */
    @RequestMapping(value = "/userPlayMachine", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String userPlayMachine(String userNo, String machineNo) {

        CommonResult<String> result = userService.userPlayMachine(userNo, machineNo);

        return JSONUtil.getReturnBeanString(result);
    }

    /**
     * 用户玩游戏(游戏)
     *
     * @param userNo    用户编号
     * @param gameRoomNo 游戏房间编号
     * @return
     */
    @RequestMapping(value = "/userPlayGame", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String userPlayGame(String userNo, String gameRoomNo) {

        CommonResult<String> result = userService.userPlayGame(userNo, gameRoomNo);

        return JSONUtil.getReturnBeanString(result);
    }

    /**
     * 获得游戏抓取结果
     *
     * @param userNo    用户编号
     * @param gameRoomNo 游戏房间编号
     * @param catchId 抓取id
     * @param status 抓取状态
     * @return
     */
    @RequestMapping(value = "/getGameCatchResultByUserNoAndGameRoomNo", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String getGameCatchResultByUserNoAndGameRoomNo(String userNo, String gameRoomNo, String catchId, Integer status) {

        CommonResult<String> result = userService.getGameCatchResultByUserNoAndGameRoomNo(userNo, gameRoomNo, catchId, status);

        return JSONUtil.getReturnBeanString(result);
    }

    /**
     * 根据用户编号修改用户名和用户头像
     * @param userNo 用户编号
     * @param userName 用户名
     * @param userImg 用户头像
     */
    @RequestMapping(value = "/updateUserInfoByIdAndUserNo", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String updateUserInfoByIdAndUserNo(String userNo, String userName, String userImg) {

        CommonResult result = userService.updateUserInfoByIdAndUserNo(userNo, userName, userImg);

        return JSONUtil.getReturnStrString(result, BaseConstant.SUCCESS);
    }

    /**
     * 用户充值
     * @param userNo 用户编号
     * @param amount 金额
     * @param rechargeType 充值类型
     * @return
     */
    @RequestMapping(value = "/userRecharge", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String userRecharge(String userNo, String amount, String rechargeType) {

        CommonResult<String> result = userService.userRecharge(userNo, amount, rechargeType);

        return JSONUtil.getReturnBeanString(result);
    }

    /**
     * 用户邀请码
     * @param userNo 用户编号
     * @param inviteCode 邀请码
     * @return
     */
    @RequestMapping(value = "/userInvite", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String userInvite(String userNo, String inviteCode) {

        CommonResult<String> result = userService.userInvite(userNo, inviteCode);

        return JSONUtil.getReturnBeanString(result);
    }

}
