package com.lzg.wawaji.enums;

import java.util.HashMap;
import java.util.Map;

public enum CurrentState {


    AVAILABLE(1, "可用"),
    UNAVAILABLE(0, "不可用");

    /**
     * 状态
     **/
    private int status;

    /**
     * 描述
     **/
    private String desc;

    private static Map<Integer,ChoiceType> valueMap = new HashMap<>();

    CurrentState(int status, String desc) {
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
            ChoiceType[] array = ChoiceType.values();
            for(ChoiceType choiceType : array) {
                valueMap.put(choiceType.getStatus(), choiceType);
            }
        }
    }

    public static Map<Integer,ChoiceType> getValueMap() {
        initValueMap();
        return valueMap;
    }

    public static ChoiceType getValueMapByKey(Integer key) {
        initValueMap();
        return valueMap.get(key);
    }
}
