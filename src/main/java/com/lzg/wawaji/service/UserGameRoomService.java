package com.lzg.wawaji.service;

import com.lzg.wawaji.bean.CommonResult;
import com.lzg.wawaji.entity.UserGameRoom;

public interface UserGameRoomService {

    /**
     * 添加用户游戏房间记录
     * @param userGameRoom 用户游戏房间
     */
    CommonResult addUserGameRoom(UserGameRoom userGameRoom);

    /**
     * 根据用户编号获得用户用户游戏房间数量
     * @param userNo 用户编号
     * @param gameRoomNo 游戏房间编号
     * @return
     */
    CommonResult<Integer> countUserGameRoomByUserNo(String userNo, String gameRoomNo);

    /**
     * 根据用户编号获得用户游戏房间
     * @param userNo 用户编号
     * @param gameRoomNo 游戏房间编号
     * @return
     */
    CommonResult<UserGameRoom> getUserGameRoomByUserNoAndGameRoomNo(String userNo, String gameRoomNo);

    /**
     * 根据用户编号和游戏房间编号累加用户房间幸运值
     * @param userNo 用户编号
     * @param gameRoomNo 游戏房间编号
     * @param userRoomLuckyNum 用户房间幸运值
     */
    CommonResult addUserRoomLuckyNumByUserNoAndGameRoomNo(String userNo, String gameRoomNo, Integer userRoomLuckyNum);

    /**
     * 根据用户编号和房间编号获得用户游戏房间幸运值
     * @param userNo 用户编号
     * @param gameRoomNo 游戏房间编号
     * @return
     */
    CommonResult<Integer> getUserGameRoomLuckyNumByUserNo(String userNo, String gameRoomNo);

    /**
     * 重置用户游戏房间幸运值
     * @param userNo 用户编号
     * @param gameRoomNo 游戏房间编号
     */
    CommonResult resetUserRoomLuckyNum(String userNo, String gameRoomNo);
}
