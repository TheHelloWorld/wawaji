package com.lzg.wawaji.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * 发货状态
 */
public enum DeliverStatus {

    INIT(0, "代发货"),
    DELIVERED(1, "已发货"),
    SIGN(2,"已签收");

    /**
     * 状态
     **/
    private int status;

    /**
     * 描述
     **/
    private String desc;

    private static Map<Integer,DeliverStatus> valueMap = new HashMap<>();

    DeliverStatus(int status, String desc) {
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
            DeliverStatus[] array = DeliverStatus.values();
            for(DeliverStatus choiceType : array) {
                valueMap.put(choiceType.getStatus(), choiceType);
            }
        }
    }

    public static Map<Integer,DeliverStatus> getValueMap() {
        initValueMap();
        return valueMap;
    }

    public static DeliverStatus getValueMapByKey(Integer key) {
        initValueMap();
        return valueMap.get(key);
    }
}
