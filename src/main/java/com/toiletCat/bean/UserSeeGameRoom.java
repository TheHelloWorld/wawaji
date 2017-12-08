package com.toiletCat.bean;

import com.toiletCat.entity.BaseEntity;

import java.io.Serializable;

/**
 * 用户可见游戏房间
 */
public class UserSeeGameRoom extends BaseEntity implements Serializable {

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
     * 娃娃图片地址
     */
    private String toyImg;

    /**
     * 娃娃当前游戏币数
     */
    private Integer toyNowCoin;

    /**
     * 娃娃原本游戏币数
     */
    private Integer toyOriginCoin;

    /**
     * 房间幸运值
     */
    private Integer roomLuckyNum;

    /**
     * 房间当前幸运值
     */
    private Integer roomNowLuckyNum;

    /**
     * 是否可用 可用 禁用
     */
    private Integer currentState;

    /**
     * 围观人数
     */
    private Integer viewer;

    /**
     * 是否空闲
     */
    private boolean available;

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

    public String getToyImg() {
        return toyImg;
    }

    public void setToyImg(String toyImg) {
        this.toyImg = toyImg;
    }

    public Integer getToyNowCoin() {
        return toyNowCoin;
    }

    public void setToyNowCoin(Integer toyNowCoin) {
        this.toyNowCoin = toyNowCoin;
    }

    public Integer getToyOriginCoin() {
        return toyOriginCoin;
    }

    public void setToyOriginCoin(Integer toyOriginCoin) {
        this.toyOriginCoin = toyOriginCoin;
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

    public Integer getCurrentState() {
        return currentState;
    }

    public void setCurrentState(Integer currentState) {
        this.currentState = currentState;
    }

    public Integer getViewer() {
        return viewer;
    }

    public void setViewer(Integer viewer) {
        this.viewer = viewer;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}
