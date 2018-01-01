package com.toiletCat.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * 抓取结果
 */
public enum CatchResult {

    CATCH_WAIT(0, "等待"),
    CATCH_SUCCESS(1, "抓取成功"),
    CATCH_FAIL(2,"抓取失败");

    /**
     * 状态
     **/
    private int status;

    /**
     * 描述
     **/
    private String desc;

    private static Map<Integer,CatchResult> valueMap = new HashMap<>();

    CatchResult(int status, String desc) {
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
            CatchResult[] array = CatchResult.values();
            for(CatchResult choiceType : array) {
                valueMap.put(choiceType.getStatus(), choiceType);
            }
        }
    }

    public static Map<Integer,CatchResult> getValueMap() {
        initValueMap();
        return valueMap;
    }

    public static CatchResult getValueMapByKey(Integer key) {
        initValueMap();
        return valueMap.get(key);
    }

}
