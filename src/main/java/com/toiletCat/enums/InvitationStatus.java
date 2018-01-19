package com.toiletCat.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * 用户邀请状态类型
 */
public enum InvitationStatus {

    UN_INVITE(0, "未邀请"),
    INVITED(1, "已邀请");

    /**
     * 状态
     **/
    private int status;

    /**
     * 描述
     **/
    private String desc;

    private static Map<Integer,InvitationStatus> valueMap = new HashMap<>();

    InvitationStatus(int status, String desc) {
        this.status = status;
        this.desc = desc;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    private static void initValueMap() {
        if(valueMap.isEmpty()) {
            InvitationStatus[] array = InvitationStatus.values();
            for(InvitationStatus choiceType : array) {
                valueMap.put(choiceType.getStatus(), choiceType);
            }
        }
    }

    public static Map<Integer,InvitationStatus> getValueMap() {
        initValueMap();
        return valueMap;
    }

    public static InvitationStatus getValueMapByKey(Integer key) {
        initValueMap();
        return valueMap.get(key);
    }

}
