package com.lzg.wawaji.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * 处理状态
 */
public enum HandleStatus {


    INIT(0, "未处理"),
    COIN(1, "已兑换"),
    DELIVER(2,"已发货");

    /**
     * 状态
     **/
    private int status;

    /**
     * 描述
     **/
    private String desc;

    private static Map<Integer,HandleStatus> valueMap = new HashMap<>();

    HandleStatus(int status, String desc) {
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
            HandleStatus[] array = HandleStatus.values();
            for(HandleStatus handleStatus : array) {
                valueMap.put(handleStatus.getStatus(), handleStatus);
            }
        }
    }

    public static Map<Integer,HandleStatus> getValueMap() {
        initValueMap();
        return valueMap;
    }

    public static HandleStatus getValueMapByKey(Integer key) {
        initValueMap();
        return valueMap.get(key);
    }
}
