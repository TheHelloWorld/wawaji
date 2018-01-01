package com.toiletCat.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * 钱兑换游戏币
 */
public enum MoneyForCoin {


    EXCHANGE_10(10L, 100),
    EXCHANGE_20(20L, 210),
    EXCHANGE_30(30L, 330),
    EXCHANGE_50(50L, 550),
    EXCHANGE_100(100L, 1180),
    EXCHANGE_200(200L, 2400);

    /**
     * 状态
     **/
    private Long money;

    /**
     * 描述
     **/
    private Integer coin;

    private static Map<Long,Integer> valueMap = new HashMap<>();

    MoneyForCoin(Long money, int coin) {
        this.money = money;
        this.coin = coin;
    }

    public Long getMoney() {
        return money;
    }

    public void setMoney(Long money) {
        this.money = money;
    }

    public Integer getCoin() {
        return coin;
    }

    public void setCoin(Integer coin) {
        this.coin = coin;
    }

    private static void initValueMap() {
        if(valueMap.isEmpty()) {
            MoneyForCoin[] array = MoneyForCoin.values();
            for(MoneyForCoin MoneyForCoin : array) {
                valueMap.put(MoneyForCoin.getMoney(), MoneyForCoin.getCoin());
            }
        }
    }

    public static Map<Long,Integer> getValueMap() {
        initValueMap();
        return valueMap;
    }

    public static Integer getValueMapByKey(Long key) {
        initValueMap();
        return valueMap.get(key);
    }
}
