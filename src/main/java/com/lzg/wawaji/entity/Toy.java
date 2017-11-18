package com.lzg.wawaji.entity;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 娃娃表
 */
public class Toy extends BaseEntity implements Serializable {

    private static final long serialVersionUID = -4954366162493302680L;

    /**
     * 娃娃编号
     */
    private String toyNo;

    /**
     * 娃娃兑换游戏币数
     */
    private Integer toyForCoin;

    /**
     * 娃娃图片地址
     */
    private String toyImg;

    /**
     * 娃娃描述
     */
    private String toyDesc;

    /**
     * 娃娃当前游戏币数
     */
    private Integer toyNowCoin;

    /**
     * 娃娃原本游戏币数
     */
    private Integer toyOriginCoin;

    /**
     * 娃娃成本
     */
    private BigDecimal toyCost;

    /**
     * 是否可用
     */
    private Integer currentState;

    public String getToyNo() {
        return toyNo;
    }

    public void setToyNo(String toyNo) {
        this.toyNo = toyNo;
    }

    public Integer getToyForCoin() {
        return toyForCoin;
    }

    public void setToyForCoin(Integer toyForCoin) {
        this.toyForCoin = toyForCoin;
    }

    public String getToyImg() {
        return toyImg;
    }

    public void setToyImg(String toyImg) {
        this.toyImg = toyImg;
    }

    public String getToyDesc() {
        return toyDesc;
    }

    public void setToyDesc(String toyDesc) {
        this.toyDesc = toyDesc;
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

    public BigDecimal getToyCost() {
        return toyCost;
    }

    public void setToyCost(BigDecimal toyCost) {
        this.toyCost = toyCost;
    }

    public Integer getCurrentState() {
        return currentState;
    }

    public void setCurrentState(Integer currentState) {
        this.currentState = currentState;
    }

}
