package com.toiletCat.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * 抓取状态
 */
public enum CatchStatus {

    NORMAL(1, "正常"),
    APPEAL(2, "申诉");

    /**
     * 状态
     **/
    private int status;

    /**
     * 描述
     **/
    private String desc;

    private static Map<Integer,CatchStatus> valueMap = new HashMap<>();

    CatchStatus(int status, String desc) {
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
            CatchStatus[] array = CatchStatus.values();
            for(CatchStatus choiceType : array) {
                valueMap.put(choiceType.getStatus(), choiceType);
            }
        }
    }

    public static Map<Integer,CatchStatus> getValueMap() {
        initValueMap();
        return valueMap;
    }

    public static CatchStatus getValueMapByKey(Integer key) {
        initValueMap();
        return valueMap.get(key);
    }

}
