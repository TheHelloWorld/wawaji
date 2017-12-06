package com.lzg.wawaji.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * 操作类型
 */
public enum HandleType {

    CONNECT(1, "添加围观人数"),
    DISCONNECT(0, "减少围观人数");

    /**
     * 状态
     **/
    private int status;

    /**
     * 描述
     **/
    private String desc;

    private static Map<Integer,HandleType> valueMap = new HashMap<>();

    HandleType(int status, String desc) {
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
            HandleType[] array = HandleType.values();
            for(HandleType choiceType : array) {
                valueMap.put(choiceType.getStatus(), choiceType);
            }
        }
    }

    public static Map<Integer,HandleType> getValueMap() {
        initValueMap();
        return valueMap;
    }

    public static HandleType getValueMapByKey(Integer key) {
        initValueMap();
        return valueMap.get(key);
    }

}
