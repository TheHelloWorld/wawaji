package com.toiletCat.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户消费记录
 */
public class UserSpendRecord extends BaseEntity implements Serializable {

    private static final long serialVersionUID = -4954366162493302680L;

    /**
     * 用户编号
     */
    private String userNo;

    /**
     * 消费游戏币数
     */
    private Integer coin;

    /**
     * 交易类型
     */
    private Integer tradeType;

    /**
     * 交易状态
     */
    private Integer tradeStatus;

    /**
     * 交易日期
     */
    private Integer tradeDate;

    /**
     * 交易时间
     */
    private Date tradeTime;

    public String getUserNo() {
        return userNo;
    }

    public void setUserNo(String userNo) {
        this.userNo = userNo;
    }

    public Integer getCoin() {
        return coin;
    }

    public void setCoin(Integer coin) {
        this.coin = coin;
    }

    public Integer getTradeType() {
        return tradeType;
    }

    public void setTradeType(Integer tradeType) {
        this.tradeType = tradeType;
    }

    public Integer getTradeStatus() {
        return tradeStatus;
    }

    public void setTradeStatus(Integer tradeStatus) {
        this.tradeStatus = tradeStatus;
    }

    public Integer getTradeDate() {
        return tradeDate;
    }

    public void setTradeDate(Integer tradeDate) {
        this.tradeDate = tradeDate;
    }

    public Date getTradeTime() {
        return tradeTime;
    }

    public void setTradeTime(Date tradeTime) {
        this.tradeTime = tradeTime;
    }
}
