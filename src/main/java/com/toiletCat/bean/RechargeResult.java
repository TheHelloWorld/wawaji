package com.toiletCat.bean;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

import java.io.Serializable;

/**
 * 充值返回结果Bean
 */
public class RechargeResult implements Serializable {

    private static final long serialVersionUID = -4954366162493302680L;

    /**
     * 我方在支付机构id
     */
    private String pId;

    /**
     * 交易金额
     */
    private String money;

    /**
     * 交易商品名
     */
    private String name;

    /**
     * 我方订单号
     */
    private String orderNo;

    /**
     * 支付机构订单号
     */
    private String tradeNo;

    /**
     * 交易状态
     */
    private String tradeStatus;

    /**
     * 支付类型
     */
    private String type;

    /**
     * 签名
     */
    private String sign;

    /**
     * 签名类型
     */
    private String signType;

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }

    public String getTradeStatus() {
        return tradeStatus;
    }

    public void setTradeStatus(String tradeStatus) {
        this.tradeStatus = tradeStatus;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getSignType() {
        return signType;
    }

    public void setSignType(String signType) {
        this.signType = signType;
    }

    /**
     * 重写toString方法
     * @return
     */
    @Override
    public String toString() {
        return JSON.toJSONString(this, SerializerFeature.WriteMapNullValue, SerializerFeature.WriteNullListAsEmpty);
    }
}
