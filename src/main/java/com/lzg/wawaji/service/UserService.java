package com.lzg.wawaji.service;

import com.lzg.wawaji.bean.CommonResult;
import com.lzg.wawaji.entity.User;

public interface UserService {

    /**
     * 添加用户
     * @param mobileNo 手机号
     */
    CommonResult<User> registerOrLoginUser(String mobileNo);

    /**
     * 校验短信验证码
     * @param ticket 短信验证码
     * @param mobileNo 手机号
     * @return
     */
    CommonResult<String> verifyCode(String ticket, String mobileNo);

    /**
     * 根据用户编号,娃娃机编号判断用户是否可以进行游戏若可以则直接扣除相应游戏币数
     * @param userNo 用户编号
     * @param machineNo 娃娃机编号
     * @return
     */
    CommonResult<String> userPlayMachine(String userNo, String machineNo);

    /**
     * 根据用户编号,游戏房间编号判断用户是否可以进行游戏若可以则直接扣除相应游戏币数
     * @param userNo 用户编号
     * @param gameRoomNo 游戏房间编号
     * @return
     */
    CommonResult<String> userPlayGame(String userNo, String gameRoomNo);

    /**
     * 用户登录或注册方法
     * @param mobileNo 手机号
     * @return
     */
    CommonResult<String> sendMobileVerificationCode(String mobileNo);

    /**
     * 根据用户编号获得用户信息
     * @param userNo 用户编号
     * @return
     */
    CommonResult<User> getUserByUserNo(String userNo);

    /**
     * 根据用户编号修改用户名和用户头像
     * @param userNo 用户编号
     * @param userName 用户名
     * @param userImg 用户头像
     */
    CommonResult updateUserInfoByIdAndUserNo(String userNo, String userName, String userImg);

}
