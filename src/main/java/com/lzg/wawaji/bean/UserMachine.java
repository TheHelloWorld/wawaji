package com.lzg.wawaji.bean;

import com.lzg.wawaji.entity.BaseEntity;

import java.io.Serializable;

/**
 * 机器表
 */
public class UserMachine extends BaseEntity implements Serializable {

    private static final long serialVersionUID = -4954366162493302680L;

    /**
     * 机器编号
     */
    private String machineNo;

    /**
     * 娃娃编号
     */
    private String toyNo;

    /**
     * 正面直播地址
     */
    private String frontUrl;

    /**
     * 侧面直播地址
     */
    private String sideUrl;

    /**
     * 占用端口号
     */
    private Integer usePort;

    /**
     * 是否可用
     */
    private Integer currentState;


    /*********** 链表查询用 toy表***************/
    /**
     * 娃娃当前价格
     */
    private Integer toyNowCoin;

    /**
     * 娃娃原本价格
     */
    private Integer toyOriginCoin;

    /**
     * 娃娃图片
     */
    private String toyImg;

    /**
     * 娃娃名称
     */
    private String toyName;

    /**
     * 观看人数
     */
    private Integer viewer;

    /**
     * 是否可用
     */
    private Boolean available;

    public String getMachineNo() {
        return machineNo;
    }

    public void setMachineNo(String machineNo) {
        this.machineNo = machineNo;
    }

    public String getToyNo() {
        return toyNo;
    }

    public void setToyNo(String toyNo) {
        this.toyNo = toyNo;
    }

    public String getFrontUrl() {
        return frontUrl;
    }

    public void setFrontUrl(String frontUrl) {
        this.frontUrl = frontUrl;
    }

    public String getSideUrl() {
        return sideUrl;
    }

    public void setSideUrl(String sideUrl) {
        this.sideUrl = sideUrl;
    }

    public Integer getUsePort() {
        return usePort;
    }

    public void setUsePort(Integer usePort) {
        this.usePort = usePort;
    }

    public Integer getCurrentState() {
        return currentState;
    }

    public void setCurrentState(Integer currentState) {
        this.currentState = currentState;
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

    public String getToyImg() {
        return toyImg;
    }

    public void setToyImg(String toyImg) {
        this.toyImg = toyImg;
    }

    public String getToyName() {
        return toyName;
    }

    public void setToyName(String toyName) {
        this.toyName = toyName;
    }

    public Integer getViewer() {
        return viewer;
    }

    public void setViewer(Integer viewer) {
        this.viewer = viewer;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }
}
