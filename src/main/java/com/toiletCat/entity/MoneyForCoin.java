package com.toiletCat.entity;

import java.io.Serializable;

/**
 * 钱和游戏币对应关系表
 */
public class MoneyForCoin extends BaseEntity implements Serializable {

    private static final long serialVersionUID = -4954366162493302680L;

    /**
     * 钱数
     */
    private String money;

    /**
     * 游戏币数
     */
    private Integer coin;

    /**
     * 前端展示的游戏币文案
     */
    private String showText;

    /**
     * 前端展示的游戏币充值文案
     */
    private String coinText;

    /**
     * 每天充值限制次数
     */
    private Integer rechargeLimit;

    /**
     * 首充标志位
     */
    private Integer firstFlag;

    /**
     * 赠送的游戏币数
     */
    private Integer giveCoin;

    /**
     * 是否可用 可用 禁用
     */
    private Integer currentState;

    /**
     * 用户首充标志位
     */
    private String userFirstFlag;

    /**
     * 用户现充标志位
     */
    private Integer userLimitFlag;

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public Integer getCoin() {
        return coin;
    }

    public void setCoin(Integer coin) {
        this.coin = coin;
    }

    public String getShowText() {
        return showText;
    }

    public void setShowText(String showText) {
        this.showText = showText;
    }

    public String getCoinText() {
        return coinText;
    }

    public void setCoinText(String coinText) {
        this.coinText = coinText;
    }

    public Integer getRechargeLimit() {
        return rechargeLimit;
    }

    public void setRechargeLimit(Integer rechargeLimit) {
        this.rechargeLimit = rechargeLimit;
    }

    public Integer getFirstFlag() {
        return firstFlag;
    }

    public void setFirstFlag(Integer firstFlag) {
        this.firstFlag = firstFlag;
    }

    public Integer getGiveCoin() {
        return giveCoin;
    }

    public void setGiveCoin(Integer giveCoin) {
        this.giveCoin = giveCoin;
    }

    public Integer getCurrentState() {
        return currentState;
    }

    public void setCurrentState(Integer currentState) {
        this.currentState = currentState;
    }

    public String getUserFirstFlag() {
        return userFirstFlag;
    }

    public void setUserFirstFlag(String userFirstFlag) {
        this.userFirstFlag = userFirstFlag;
    }

    public Integer getUserLimitFlag() {
        return userLimitFlag;
    }

    public void setUserLimitFlag(Integer userLimitFlag) {
        this.userLimitFlag = userLimitFlag;
    }
}
