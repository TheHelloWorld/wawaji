package com.lzg.wawaji.entity;

import java.io.Serializable;

/**
 * 用户娃娃表
 */
public class UserToy extends BaseEntity implements Serializable {

    private static final long serialVersionUID = -4954366162493302680L;

    /**
     * 用户编号
     */
    private String userNo;

    /**
     * 娃娃编号
     */
    private String toyNo;

    /**
     * 选择方式 0 兑换游戏币 1 寄送
     */
    private Integer choiceType;

    /**
     * 处理状态
     */
    private Integer handleStatus;

    /************************* 链表查询用 toy *******************************/

    /**
     * 娃娃图片
     */
    private String toyImg;

    /**
     * 娃娃名称
     */
    private String toyName;

    /**
     * 娃娃兑换游戏币数
     */
    private Integer toyForCoin;

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

    public Integer getHandleStatus() {
        return handleStatus;
    }

    public void setHandleStatus(Integer handleStatus) {
        this.handleStatus = handleStatus;
    }

    public String getToyName() {
        return toyName;
    }

    public void setToyName(String toyName) {
        this.toyName = toyName;
    }

    public Integer getToyForCoin() {
        return toyForCoin;
    }

    public void setToyForCoin(Integer toyForCoin) {
        this.toyForCoin = toyForCoin;
    }
}
