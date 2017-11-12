package com.lzg.wawaji.service;

import com.lzg.wawaji.entity.User;

public interface UserService {

    /**
     * 添加用户
     * @param user 用户Bean
     */
    void addUser(User user);

    /**
     * 根据用户编号,娃娃机编号判断用户是否可以进行游戏若可以则直接扣除相应游戏币数
     * @param userNo 用户编号
     * @param machineNo 娃娃机编号
     * @return
     */
    String userPlay(String userNo, String machineNo);
}
