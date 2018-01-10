package com.toiletCat.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * 交易类型
 */
public enum TradeType {

    RECHARGE(0, "充值"),
    REFUND(1, "退款"),
    TOY_FOR_COIN(2, "玩具兑换游戏币"),
    SPEND(3, "消费"),
    DELIVER_COIN(4, "邮寄费用"),
    INVITE(5, "邀请码");

    /**
     * 类型
     **/
    private int type;

    /**
     * 描述
     **/
    private String desc;

    private static Map<Integer,TradeType> valueMap = new HashMap<>();

    TradeType(int type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    private static void initValueMap() {
        if(valueMap.isEmpty()) {
            TradeType[] array = TradeType.values();
            for(TradeType tradeType : array) {
                valueMap.put(tradeType.getType(), tradeType);
            }
        }
    }

    public static Map<Integer,TradeType> getValueMap() {
        initValueMap();
        return valueMap;
    }

    public static TradeType getValueMapByKey(Integer key) {
        initValueMap();
        return valueMap.get(key);
    }
}
