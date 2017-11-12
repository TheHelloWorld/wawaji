package com.lzg.wawaji.entity;

import java.io.Serializable;

/**
 * 用户地址表
 */
public class UserAddress extends BaseEntity implements Serializable {

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
}
