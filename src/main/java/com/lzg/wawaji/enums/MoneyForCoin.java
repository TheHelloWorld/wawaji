package com.lzg.wawaji.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * 钱兑换游戏币
 */
public enum MoneyForCoin {

    EXCHANGE(1, 1);

    /**
     * 状态
     **/
    private int money;

    /**
     * 描述
     **/
    private int coin;

    private static Map<Integer,Integer> valueMap = new HashMap<>();

    MoneyForCoin(int money, int coin) {
        this.money = money;
        this.coin = coin;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public int getCoin() {
        return coin;
    }

    public void setCoin(int coin) {
        this.coin = coin;
    }

    private static void initValueMap() {
        if(valueMap.isEmpty()) {
            MoneyForCoin[] array = MoneyForCoin.values();
            for(MoneyForCoin MoneyForCoin : array) {
                valueMap.put(MoneyForCoin.getMoney(), MoneyForCoin.getMoney());
            }
        }
    }

    public static Map<Integer,Integer> getValueMap() {
        initValueMap();
        return valueMap;
    }

    public static Integer getValueMapByKey(Integer key) {
        initValueMap();
        return valueMap.get(key);
    }
}
