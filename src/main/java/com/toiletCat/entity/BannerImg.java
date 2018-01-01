package com.toiletCat.entity;

import java.io.Serializable;

/**
 * banner图片表
 */
public class BannerImg extends BaseEntity implements Serializable {

    private static final long serialVersionUID = -4954366162493302680L;

    /**
     * 图片地址
     */
    private String imgUrl;

    /**
     * 轮播顺序
     */
    private Integer bannerOrder;

    /**
     * 点击url
     */
    private String clickUrl;

    /**
     * banner类型 娃娃机房间/游戏房间
     */
    private Integer bannerType;


    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public Integer getBannerOrder() {
        return bannerOrder;
    }

    public void setBannerOrder(Integer bannerOrder) {
        this.bannerOrder = bannerOrder;
    }

    public String getClickUrl() {
        return clickUrl;
    }

    public void setClickUrl(String clickUrl) {
        this.clickUrl = clickUrl;
    }

    public Integer getBannerType() {
        return bannerType;
    }

    public void setBannerType(Integer bannerType) {
        this.bannerType = bannerType;
    }
}
