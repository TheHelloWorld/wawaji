package com.toiletCat.entity;

import java.io.Serializable;

/**
 * 用户战利品处理表
 */
public class UserToyHandle extends BaseEntity implements Serializable {

    private static final long serialVersionUID = -4954366162493302680L;

    /**
     * 用户编号
     */
    private String userNo;

    /**
     * 玩具编号
     */
    private String toyNo;

    /**
     * 玩具名称
     */
    private String toyName;

    /**
     * 玩具图片
     */
    private String toyImg;

    /**
     * 选择处理方式
     */
    private Integer choiceType;

    /**
     * 玩具兑换游戏币书
     */
    private Integer toyForCoin;

    /**
     * 发货Id
     */
    private Long deliverId;

    /**
     * 抓取时间
     */
    private String catchTime;

    public String getUserNo() {
        return userNo;
    }

    public void setUserNo(String userNo) {
        this.userNo = userNo;
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

    public Integer getChoiceType() {
        return choiceType;
    }

    public void setChoiceType(Integer choiceType) {
        this.choiceType = choiceType;
    }

    public Integer getToyForCoin() {
        return toyForCoin;
    }

    public void setToyForCoin(Integer toyForCoin) {
        this.toyForCoin = toyForCoin;
    }

    public Long getDeliverId() {
        return deliverId;
    }

    public void setDeliverId(Long deliverId) {
        this.deliverId = deliverId;
    }

    public String getCatchTime() {
        return catchTime;
    }

    public void setCatchTime(String catchTime) {
        this.catchTime = catchTime;
    }
}
