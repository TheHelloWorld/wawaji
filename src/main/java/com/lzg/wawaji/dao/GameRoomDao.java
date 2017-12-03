package com.lzg.wawaji.dao;

import com.lzg.wawaji.bean.UserSeeGameRoom;
import com.lzg.wawaji.entity.GameRoom;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("gameRoomDao")
public interface GameRoomDao {

    /**
     * 添加游戏房间
     * @param gameRoom 游戏房间
     */
    void addGameRoom(GameRoom gameRoom);

    /**
     * 获得所有游戏房间的数量
     * @return
     */
    Integer countAllGameRoom();

    /**
     * 分页获得所有游戏房间
     * @param startPage 开始页
     * @param pageSize 每页数据数
     * @return
     */
    List<GameRoom> getGameRoomeListByPage(@Param("startPage") int startPage,@Param("pageSize") int pageSize);

    /**
     * 获得所有用户可见游戏房间数量
     * @return
     */
    Integer countAllUserSeeGamRoom();

    /**
     * 分页获得所有用户可见的游戏房间
     * @param startPage 开始页
     * @param pageSize 每页数据数
     * @return
     */
    List<UserSeeGameRoom> getUserSeeGameRoomListByPage(@Param("startPage") int startPage, @Param("pageSize") int pageSize);

    /**
     * 根据游戏房间编号和id获得游戏房间数据
     * @param gameRomNo 游戏房间编号
     * @param id id
     * @return
     */
    GameRoom getGameRoomByGameRoomNoAndId(@Param("gameRoomNo") String gameRomNo, @Param("id") Long id);

    /**
     * 根据游戏房间编号和id修改游戏房间
     * @param gameRoom 游戏房间
     */
    void updateGameRoomeByGameRoomNoAndId(GameRoom gameRoom);

    /**
     * 游戏房间幸运值加一
     * @param gameRoomNo 游戏房间编号
     */
    void addRoomLuckyNumByGameRoomNo(String gameRoomNo);

    /**
     * 重置游戏房间幸运值
     * @param gameRoomNo 游戏房间编号
     * @param roomLuckyNum 房间幸运值
     */
    void resetRoomLuckyNumByGameRoomNo(@Param("gameRoomNo") String gameRoomNo, @Param("roomLuckyNum") Integer roomLuckyNum);
}
