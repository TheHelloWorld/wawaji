package com.lzg.wawaji.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * banner类型
 */
public enum BannerType {

    GAME_ROOM(1, "游戏房间"),
    MACHINE_ROOM(0, "娃娃机房间");

    /**
     * 状态
     **/
    private int status;

    /**
     * 描述
     **/
    private String desc;

    private static Map<Integer,BannerType> valueMap = new HashMap<>();

    BannerType(int status, String desc) {
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
            BannerType[] array = BannerType.values();
            for(BannerType choiceType : array) {
                valueMap.put(choiceType.getStatus(), choiceType);
            }
        }
    }

    public static Map<Integer,BannerType> getValueMap() {
        initValueMap();
        return valueMap;
    }

    public static BannerType getValueMapByKey(Integer key) {
        initValueMap();
        return valueMap.get(key);
    }

}
