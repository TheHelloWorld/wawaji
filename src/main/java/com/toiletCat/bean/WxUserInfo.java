package com.toiletCat.bean;

public class WxUserInfo {

	/**
	 * 用户openId
	 **/
	private String openId;

	/**
	 * 用户昵称
	 **/
	private String nickName;

	/**
	 * 用户unionId
	 **/
	private String unionId;

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getUnionId() {
		return unionId;
	}

	public void setUnionId(String unionId) {
		this.unionId = unionId;
	}
}
