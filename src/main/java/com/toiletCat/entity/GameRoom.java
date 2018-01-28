package com.toiletCat.entity;

import java.io.Serializable;

/**
 * 游戏房间
 */
public class GameRoom extends BaseEntity implements Serializable {

    private static final long serialVersionUID = -4954366162493302680L;

    /**
     * 游戏房间编号
     */
    private String gameRoomNo;

    /**
     * 玩具编号
     */
    private String toyNo;

    /**
     * 玩具名称
     */
    private String toyName;

    /**
     * 房间幸运值
     */
    private Integer roomLuckyNum;

    /**
     * 房间当前幸运值
     */
    private Integer roomNowLuckyNum;

    /**
     * 房间每次累加幸运值
     */
    private Integer addLuckyNum;

    /**
     * 房间顺序
     */
    private Integer roomOrder;

    /**
     * 是否可用 可用 禁用
     */
    private Integer currentState;

    /**
     * 真实在线人数
     */
    private Integer realPlayNum;

    public String getGameRoomNo() {
        return gameRoomNo;
    }

    public void setGameRoomNo(String gameRoomNo) {
        this.gameRoomNo = gameRoomNo;
    }

    public String getToyNo() {
        return toyNo;
    }

    public void setToyNo(String toyNo) {
        this.toyNo = toyNo;
    }

    public String getToyName() {
        return toyName;
    }

    public void setToyName(String toyName) {
        this.toyName = toyName;
    }

    public Integer getRoomOrder() {
        return roomOrder;
    }

    public void setRoomOrder(Integer roomOrder) {
        this.roomOrder = roomOrder;
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

    public Integer getAddLuckyNum() {
        return addLuckyNum;
    }

    public void setAddLuckyNum(Integer addLuckyNum) {
        this.addLuckyNum = addLuckyNum;
    }

    public Integer getCurrentState() {
        return currentState;
    }

    public void setCurrentState(Integer currentState) {
        this.currentState = currentState;
    }

    public Integer getRealPlayNum() {
        return realPlayNum;
    }

    public void setRealPlayNum(Integer realPlayNum) {
        this.realPlayNum = realPlayNum;
    }
}
