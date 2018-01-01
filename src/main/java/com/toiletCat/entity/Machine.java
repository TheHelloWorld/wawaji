package com.toiletCat.entity;

import java.io.Serializable;

/**
 * 机器表
 */
public class Machine extends BaseEntity implements Serializable {

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

}
