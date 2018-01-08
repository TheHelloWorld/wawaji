package com.toiletCat.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * 钱兑换游戏币
 */
public enum MoneyForCoin {

    EXCHANGE_10(10.00, 100),
    EXCHANGE_20(20.00, 210),
    EXCHANGE_30(30.00, 330),
    EXCHANGE_50(50.00, 550),
    EXCHANGE_100(100.00, 1180),
    EXCHANGE_200(200.00, 2400);

    /**
     * 状态
     **/
    private Double money;

    /**
     * 描述
     **/
    private Integer coin;

    private static Map<Double,Integer> valueMap = new HashMap<>();

    MoneyForCoin(Double money, int coin) {
        this.money = money;
        this.coin = coin;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
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

    public static Map<Double,Integer> getValueMap() {
        initValueMap();
        return valueMap;
    }

    public static Integer getValueMapByKey(Double key) {
        initValueMap();
        return valueMap.get(key);
    }
}
