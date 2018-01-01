package com.toiletCat.bean;

import java.util.Date;

public class UserCatchRecord {

    /**
     * 主键id
     */
    private Long id;

    /**
     * 用户编号
     */
    private String userNo;

    /**
     * 用户头像地址
     */
    private String userImg;

    /**
     * 用户姓名
     */
    private String userName;

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
     * 抓取结果
     */
    private Integer catchResult;

    /**
     * 抓取状态
     */
    private Integer catchStatus;

    /**
     * 抓取时间
     */
    private Date catchTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserNo() {
        return userNo;
    }

    public void setUserNo(String userNo) {
        this.userNo = userNo;
    }

    public String getUserImg() {
        return userImg;
    }

    public void setUserImg(String userImg) {
        this.userImg = userImg;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public Integer getCatchStatus() {
        return catchStatus;
    }

    public void setCatchStatus(Integer catchStatus) {
        this.catchStatus = catchStatus;
    }

    public Integer getCatchResult() {
        return catchResult;
    }

    public void setCatchResult(Integer catchResult) {
        this.catchResult = catchResult;
    }

    public Date getCatchTime() {
        return catchTime;
    }

    public void setCatchTime(Date catchTime) {
        this.catchTime = catchTime;
    }
}
