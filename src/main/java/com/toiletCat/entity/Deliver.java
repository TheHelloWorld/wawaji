package com.toiletCat.entity;

import java.io.Serializable;

/**
 * 发货表
 */
public class Deliver extends BaseEntity implements Serializable {

    private static final long serialVersionUID = -4954366162493302680L;

    /**
     * 用户编号
     */
    private String userNo;

    /**
     * 用户姓名
     */
    private String userName;

    /**
     * 手机号
     */
    private String mobileNo;

    /**
     * 地址
     */
    private String address;

    /**
     * 娃娃信息
     */
    private String toyNameArray;

    /**
     * 发货单号
     */
    private String deliverNo;

    /**
     * 快递公司
     */
    private String company;

    /**
     * 货物信息
     */
    private String deliverMsg;

    /**
     * 发货状态
     */
    private Integer deliverStatus;

    public String getUserNo() {
        return userNo;
    }

    public void setUserNo(String userNo) {
        this.userNo = userNo;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getToyNameArray() {
        return toyNameArray;
    }

    public void setToyNameArray(String toyNameArray) {
        this.toyNameArray = toyNameArray;
    }

    public String getDeliverNo() {
        return deliverNo;
    }

    public void setDeliverNo(String deliverNo) {
        this.deliverNo = deliverNo;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getDeliverMsg() {
        return deliverMsg;
    }

    public void setDeliverMsg(String deliverMsg) {
        this.deliverMsg = deliverMsg;
    }

    public Integer getDeliverStatus() {
        return deliverStatus;
    }

    public void setDeliverStatus(Integer deliverStatus) {
        this.deliverStatus = deliverStatus;
    }
}
