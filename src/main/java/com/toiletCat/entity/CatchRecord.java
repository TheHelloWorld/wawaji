package com.toiletCat.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 抓取记录表
 */
public class CatchRecord extends BaseEntity implements Serializable {

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
     * 抓取Id
     */
    private String catchId;

    /**
     * 抓取结果
     */
    private Integer catchResult;

    /**
     * 抓取时间
     */
    private Date catchTime;

    /**
     * 抓取状态
     */
    private Integer catchStatus;


    /****************************************/

    /**
     * 玩具图片地址
     */
    private String toyImg;

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

    public String getCatchId() {
        return catchId;
    }

    public void setCatchId(String catchId) {
        this.catchId = catchId;
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

    public Integer getCatchStatus() {
        return catchStatus;
    }

    public void setCatchStatus(Integer catchStatus) {
        this.catchStatus = catchStatus;
    }

    public String getToyImg() {
        return toyImg;
    }

    public void setToyImg(String toyImg) {
        this.toyImg = toyImg;
    }
}
