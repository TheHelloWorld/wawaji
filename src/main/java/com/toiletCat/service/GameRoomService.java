package com.toiletCat.service;

import com.toiletCat.bean.CommonResult;
import com.toiletCat.bean.UserSeeGameRoom;
import com.toiletCat.entity.GameRoom;
import com.toiletCat.enums.HandleType;

import java.util.List;

public interface GameRoomService {

    /**
     * 添加游戏房间
     * @param gameRoom 游戏房间
     */
    CommonResult addGameRoom(GameRoom gameRoom);

    /**
     * 获得所有游戏房间的数量
     * @return
     */
    CommonResult<Integer> countAllGameRoom();

    /**
     * 分页获得所有游戏房间
     * @param startPage 开始页
     * @return
     */
    CommonResult<List<GameRoom>> getGameRoomListByPage(int startPage);

    /**
     * 获得所有用户可见游戏房间数量
     * @return
     */
    CommonResult<Integer> countAllUserSeeGamRoom();

    /**
     * 分页获得所有用户可见的游戏房间
     * @param startPage 开始页
     * @return
     */
    CommonResult<List<UserSeeGameRoom>> getUserSeeGameRoomListByPage(int startPage);

    /**
     * 根据游戏房间号码获得用户可见游戏房间
     * @param gameRoomNo 游戏房间号码
     * @return
     */
    CommonResult<UserSeeGameRoom> getUserSeeGameRoomByGameRoomNo(String gameRoomNo);

    /**
     * 根据游戏房间号码获得用户可见游戏房间
     * @param userNo 用户编号
     * @param gameRoomNo 游戏房间号码
     * @return
     */
    CommonResult<String> getUserGameRoomInfoByGameRoomNo(String userNo, String gameRoomNo);

    /**
     * 根据游戏房间编号获得所需游戏币数
     * @param gameRoomNo 游戏房间编号
     * @return
     */
    CommonResult<Integer> getCoinByGameRoomNo(String gameRoomNo);

    /**
     * 根据游戏房间编号和id获得游戏房间数据
     * @param gameRoomNo 游戏房间编号
     * @param id id
     * @return
     */
    CommonResult<GameRoom> getGameRoomByGameRoomNoAndId(String gameRoomNo, Long id);

    /**
     * 根据游戏房间编号和id修改游戏房间
     * @param gameRoom 游戏房间
     */
    CommonResult updateGameRoomByGameRoomNoAndId(GameRoom gameRoom);

    /**
     * 累加游戏房间幸运值
     * @param gameRoomNo 游戏房间编号
     */
    CommonResult addRoomLuckyNumByGameRoomNo(String gameRoomNo);

    /**
     * 重置游戏房间幸运值
     * @param gameRoomNo 游戏房间编号
     */
    CommonResult resetRoomLuckyNumByGameRoomNo(String gameRoomNo);

    /**
     * 根据游戏房间编号获得房间幸运值及当前幸运值
     * @param gameRoomNo 游戏房间编号
     * @return
     */
    CommonResult<GameRoom> getLuckyNumByGameRoomNo(String gameRoomNo);

    /**
     * 根据游戏房间编号获得玩具名称
     * @param gameRoomNo 游戏房间编号
     * @return
     */
    CommonResult<String> getToyNameByGameRoomNo(String gameRoomNo);

    /**
     * 操作游戏房间围观人数
     * @param gameRoomNo 游戏房间编号
     * @param handleType 操作类型
     * @return
     */
    CommonResult<Long> handleGameRoomViewer(String gameRoomNo, HandleType handleType);
}
