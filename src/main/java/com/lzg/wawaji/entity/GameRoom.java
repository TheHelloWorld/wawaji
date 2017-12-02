package com.lzg.wawaji.entity;

import java.io.Serializable;

public class GameRoom extends BaseEntity implements Serializable {

    private static final long serialVersionUID = -4954366162493302680L;

    /**
     * 游戏房间编号
     */
    private String gameRoomNo;

    /**
     * 房间幸运值
     */
    private Integer roomLuckyNum;

    /**
     * 房间当前幸运值
     */
    private Integer roomNowLuckyNum;

    public String getGameRoomNo() {
        return gameRoomNo;
    }

    public void setGameRoomNo(String gameRoomNo) {
        this.gameRoomNo = gameRoomNo;
    }

    public Integer getRoomLuckyNum() {
        return roomLuckyNum;
    }

    public void setRoomLuckyNum(Integer roomLuckyNum) {
        this.roomLuckyNum = roomLuckyNum;
    }

    public Integer getRoomNowLuckyNum() {
        return roomNowLuckyNum;
    }

    public void setRoomNowLuckyNum(Integer roomNowLuckyNum) {
        this.roomNowLuckyNum = roomNowLuckyNum;
    }
}
