package com.lzg.wawaji.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * 处理状态
 */
public enum ChoiceType {

    INIT(0, "未选择"),
    FOR_COIN(1, "兑换成游戏币"),
    FOR_DELIVER(2,"寄送");

    /**
     * 状态
     **/
    private int status;

    /**
     * 描述
     **/
    private String desc;

    private static Map<Integer,ChoiceType> valueMap = new HashMap<>();

    ChoiceType(int status, String desc) {
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
