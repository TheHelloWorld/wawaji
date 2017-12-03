package com.lzg.wawaji.entity;

import java.io.Serializable;

/**
 * 用户游戏房间
 */
public class UserGameRoom extends BaseEntity implements Serializable {

    private static final long serialVersionUID = -4954366162493302680L;

    /**
     * 用户编号
     */
    private String userNo;

    /**
     * 游戏房间编号
     */
    private String gameRoomNo;

    /**
     * 用户房间幸运值
     */
    private Integer userRoomLuckyNum;

    public String getUserNo() {
        return userNo;
    }

    public void setUserNo(String userNo) {
        this.userNo = userNo;
    }

    public String getGameRoomNo() {
        return gameRoomNo;
    }

    public void setGameRoomNo(String gameRoomNo) {
        this.gameRoomNo = gameRoomNo;
    }

    public Integer getUserRoomLuckyNum() {
        return userRoomLuckyNum;
    }

    public void setUserRoomLuckyNum(Integer userRoomLuckyNum) {
        this.userRoomLuckyNum = userRoomLuckyNum;
    }
}
