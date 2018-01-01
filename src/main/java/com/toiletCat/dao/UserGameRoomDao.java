package com.toiletCat.dao;

import com.toiletCat.entity.UserGameRoom;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository("userGameRoomDao")
public interface UserGameRoomDao {

    /**
     * 添加用户游戏房间记录
     * @param userGameRoom 用户游戏房间
     */
    void addUserGameRoom(UserGameRoom userGameRoom);

    /**
     * 根据用户编号获得用户用户游戏房间数量
     * @param userNo 用户编号
     * @param gameRoomNo 游戏房间编号
     * @return
     */
    Integer countUserGameRoomByUserNo(@Param("userNo") String userNo, @Param("gameRoomNo") String gameRoomNo);

    /**
     * 根据用户编号和房间编号获得用户游戏房间
     * @param userNo 用户编号
     * @param gameRoomNo 游戏房间编号
     * @return
     */
    UserGameRoom getUserGameRoomByUserNoAndGameRoomNo(@Param("userNo") String userNo, @Param("gameRoomNo") String gameRoomNo);

    /**
     * 根据用户编号和房间编号获得用户游戏房间幸运值
     * @param userNo 用户编号
     * @param gameRoomNo 游戏房间编号
     * @return
     */
    Integer getUserGameRoomLuckyNumByUserNo(@Param("userNo") String userNo, @Param("gameRoomNo") String gameRoomNo);

    /**
     * 根据用户编号和游戏房间编号累加用户房间幸运值
     * @param userNo 用户编号
     * @param gameRoomNo 游戏房间编号
     * @param userRoomLuckyNum 用户房间幸运值
     */
    void addUserRoomLuckyNumByUserNoAndGameRoomNo(@Param("userNo") String userNo, @Param("gameRoomNo") String gameRoomNo,
                                                  @Param("userRoomLuckyNum") Integer userRoomLuckyNum);

    /**
     * 重置用户游戏房间幸运值
     * @param userNo 用户编号
     * @param gameRoomNo 游戏房间编号
     */
    void resetUserRoomLuckyNum(@Param("userNo") String userNo, @Param("gameRoomNo") String gameRoomNo);
}
