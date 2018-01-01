package com.toiletCat.entity;

import java.io.Serializable;

/**
 * 用户表
 */
public class User extends BaseEntity implements Serializable {

	private static final long serialVersionUID = -4954366162493302680L;

	/**
	 * 用户编号
	 */
	private String userNo;

	/**
	 * 手机号
	 */
	private String mobileNo;

	/**
	 * 用户名(微信获取)
	 */
	private String userName;

	/**
	 * 用户邀请码
	 */
	private String invitationCode;

	/**
	 * 用户头像
	 */
	private String userImg;

	/**
	 * 用户游戏币数
	 */
	private Integer userCoin;

	public String getUserNo() {
		return userNo;
	}

	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getInvitationCode() {
		return invitationCode;
	}

	public void setInvitationCode(String invitationCode) {
		this.invitationCode = invitationCode;
	}

	public String getUserImg() {
		return userImg;
	}

	public void setUserImg(String userImg) {
		this.userImg = userImg;
	}

	public Integer getUserCoin() {
		return userCoin;
	}

	public void setUserCoin(Integer userCoin) {
		this.userCoin = userCoin;
	}
}
