package com.toiletCat.bean;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

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
	 * 城市/区
	 */
	private String city;

	/**
	 * 省份
	 */
	private String province;

	/**
	 * 国籍
	 */
	private String country;

	/**
	 * 用户头像
	 **/
	private String headImgUrl;

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

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getHeadImgUrl() {
		return headImgUrl;
	}

	public void setHeadImgUrl(String headImgUrl) {
		this.headImgUrl = headImgUrl;
	}

	/**
	 * 重写toString方法
	 * @return
	 */
	@Override
	public String toString() {
		return JSON.toJSONString(this, SerializerFeature.WriteMapNullValue,
				SerializerFeature.WriteNullListAsEmpty);
	}
}
