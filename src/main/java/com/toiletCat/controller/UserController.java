package com.toiletCat.controller;

import com.alibaba.fastjson.JSONObject;
import com.toiletCat.bean.CommonResult;
import com.toiletCat.bean.WxUserInfo;
import com.toiletCat.constants.BaseConstant;
import com.toiletCat.entity.User;
import com.toiletCat.service.UserService;
import com.toiletCat.utils.CommonHandle;
import com.toiletCat.utils.EmojiFilter;
import com.toiletCat.utils.JSONUtil;
import com.toiletCat.utils.WeChatUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@RequestMapping("/toiletCat/api/user")
@Controller
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    /**
     * 公众号访问url
     */
    private static final String redirectUrl = "/toiletCat/gameRoom/gameRoom.html?type=wx_web";

    /**
     * app访问url
     */
    private static final String appUrl = "http://www.9w83c6.cn/toiletCat/gameRoom/gameRoom.html?type=app";

    @Autowired
    private UserService userService;

    /**
     * 用户微信登录
     * @param code code
     * @return
     */
    @RequestMapping(value = "/userWeChatLogin", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
    public void userWeChatLogin(HttpServletRequest request, HttpServletResponse response, String code) {

        try {

            User user;

            Map<String, String> result;

            String openId;

            // 从cookie中获取userNo
            String userNo = CommonHandle.getCookieValue(request, BaseConstant.COOKIE_USER_NO);

            if(StringUtils.isBlank(userNo) || "null".equals(userNo)) {

                // 通过这个code获取access_token,openId
                result = WeChatUtil.getUserInfoAccessToken(code);

                openId = result.get("openid");

                CommonResult<User> openIdCommonResult = userService.getUserByOpenId(openId);

                user = openIdCommonResult.getValue();

                // 若根据openId无法找到用户则注册新用户
                if(user == null) {

                    // 使用access_token获取用户信息
                    WxUserInfo userInfo = WeChatUtil.getUserInfo(result.get("access_token"), openId);

                    // 注册用户
                    CommonResult<User> userResult = userService.registerOrLoginUserByWxUserInfo(userInfo);

                    user = userResult.getValue();
                }

                // 将用户编号放入cookie中
                CommonHandle.setUserNoInCookie(response, user.getUserNo());

                // 重定向跳转页面地址(游戏首页地址)
                response.sendRedirect(redirectUrl);

                return;
            }

            // 查询用户信息
            CommonResult<User> userCommonResult = userService.getUserByUserNo(userNo);

            user = userCommonResult.getValue();

            if(userCommonResult.getValue() == null) {

                logger.warn("autoLogin toiletCat warn: 没有对应的用户编号 userNo:{}", userNo);

                // 通过这个code获取access_token,openId
                result = WeChatUtil.getUserInfoAccessToken(code);

                openId = result.get("openid");

                // 使用access_token获取用户信息
                WxUserInfo userInfo = WeChatUtil.getUserInfo(result.get("access_token"), openId);

                // 注册用户
                CommonResult<User> userResult = userService.registerOrLoginUserByWxUserInfo(userInfo);

                user = userResult.getValue();

                // 将用户编号放入cookie中
                CommonHandle.setUserNoInCookie(response, user.getUserNo());

                // 重定向跳转页面地址(游戏首页地址)
                response.sendRedirect(redirectUrl);

                return;
            }

            // 若用户的OpenId为空
            if(StringUtils.isBlank(user.getOpenId())) {

                user = setUserWxUserInfo(code, user);

                // 将用户编号放入cookie中
                CommonHandle.setUserNoInCookie(response, user.getUserNo());

                // 重定向跳转页面地址(游戏首页地址)
                response.sendRedirect(redirectUrl);

                return;
            }

            logger.info("userWeChatLogin userNo:{}", userNo);

            // 重定向跳转页面地址(游戏首页地址)
            response.sendRedirect(redirectUrl);

        } catch(Exception e) {

            logger.error("userWeChatLogin error:" + e, e);
        }
    }

    /**
     * 设置用户微信信息
     * @param code code
     * @param user 用户信息
     * @return
     */
    private User setUserWxUserInfo(String code, User user) {

        // 通过这个code获取access_token,openId
        Map<String, String> result = WeChatUtil.getUserInfoAccessToken(code);

        String openId = result.get("openid");

        // 使用access_token获取用户信息
        WxUserInfo userInfo = WeChatUtil.getUserInfo(result.get("access_token"), openId);

        user.setUserImg(userInfo.getHeadImgUrl());

        user.setOpenId(userInfo.getOpenId());

        user.setUserName(userInfo.getNickName());

        userService.updateUserInfo(user);

        return user;
    }

    /**
     * 根据openId获取userNo
     * @param openId 微信openId
     * @param userName 用户微信昵称
     * @param headImg 用户微信头像
     * @return
     */
    @RequestMapping(value = "/getUserInfoByOpenId", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String getUserInfoByOpenId(String openId, String userName, String headImg) {

        // 根据openId获取用户信息
        CommonResult<User> openIdCommonResult = userService.getUserByOpenId(openId);

        User user = openIdCommonResult.getValue();

        JSONObject json = new JSONObject();

        json.put("is_success", BaseConstant.SUCCESS);

        json.put("url", appUrl);

        if(user != null) {

            json.put("userNo", user.getUserNo());

            return json.toJSONString();
        }

        WxUserInfo wxUserInfo = new WxUserInfo();

        // 判断用户头像是否为空
        if(StringUtils.isBlank(headImg)) {

            headImg = BaseConstant.DEFAULT_HEAD_FLAG;

        } else {

            headImg = headImg.replaceAll("\"", "");
        }

        wxUserInfo.setHeadImgUrl(headImg);

        // 判断昵称是否为空
        if(StringUtils.isBlank(userName)) {

            userName = BaseConstant.DEFAULT_NAME_FLAG;

        } else {

            userName = EmojiFilter.filterEmoji(userName.replaceAll("\"", ""));
        }

        // 过滤用户名中的emoji表情
        wxUserInfo.setNickName(userName);

        wxUserInfo.setOpenId(openId);

        // 注册用户
        CommonResult<User> userResult = userService.registerOrLoginUserByWxUserInfo(wxUserInfo);

        user = userResult.getValue();

        if(user != null) {

            json.put("userNo", user.getUserNo());

            return json.toJSONString();
        }

        return JSONUtil.getErrorJson();
    }

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

                logger.warn("autoLogin toiletCat warn: 没有对应的用户编号 userNo:{}", userNo);

                return JSONUtil.getSuccessReturnJSON(BaseConstant.FAIL);
            }

            // 将用户编号放入cookie中
            CommonHandle.setUserNoInCookie(response, result.getValue().getUserNo());

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
        CommonResult<User> result = userService.registerOrLoginUserByMobileNo(mobile);

        if(result.success()) {

            // 将用户编号放入cookie中
            CommonHandle.setUserNoInCookie(response, result.getValue().getUserNo());

            return JSONUtil.getSuccessReturnJSON(result.getValue());
        }

        return JSONUtil.getErrorJson();
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
