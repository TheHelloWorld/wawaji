package com.lzg.wawaji.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * 交易状态
 */
public enum TradeStatus {


    SUCCESS(0, "成功"),
    FAIL(1, "失败"),
    PROCESSING(2,"处理中");

    /**
     * 状态
     **/
    private int status;

    /**
     * 描述
     **/
    private String desc;

    private static Map<Integer,TradeStatus> valueMap = new HashMap<>();

    TradeStatus(int status, String desc) {
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
            TradeStatus[] array = TradeStatus.values();
            for(TradeStatus tradeStatus : array) {
                valueMap.put(tradeStatus.getStatus(), tradeStatus);
            }
        }
    }

    public static Map<Integer,TradeStatus> getValueMap() {
        initValueMap();
        return valueMap;
    }

    public static TradeStatus getValueMapByKey(Integer key) {
        initValueMap();
        return valueMap.get(key);
    }
}
