package com.lzg.wawaji.service;

import com.lzg.wawaji.bean.CommonResult;
import com.lzg.wawaji.bean.UserSeeGameRoom;
import com.lzg.wawaji.entity.GameRoom;

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
    CommonResult<List<GameRoom>> getGameRoomeListByPage(int startPage);

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
    CommonResult<UserSeeGameRoom> getUserSeeGameRoomByGameRoomNo(String  gameRoomNo);

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
    CommonResult updateGameRoomeByGameRoomNoAndId(GameRoom gameRoom);

    /**
     * 游戏房间幸运值加一
     * @param gameRoomNo 游戏房间编号
     */
    CommonResult addRoomLuckyNumByGameRoomNo(String gameRoomNo);

    /**
     * 重置游戏房间幸运值
     * @param gameRoomNo 游戏房间编号
     * @param roomLuckyNum 房间幸运值
     */
    CommonResult resetRoomLuckyNumByGameRoomNo(String gameRoomNo, Integer roomLuckyNum);
}
