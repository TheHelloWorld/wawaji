package com.toiletCat.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * 钱兑换游戏币
 */
public enum MoneyForCoin {

    EXCHANGE_2("2.00", 30),
    EXCHANGE_9("9.99", 100),
    EXCHANGE_19("19.99", 210),
    EXCHANGE_29("29.99", 330),
    EXCHANGE_49("49.99", 550),
    EXCHANGE_99("99.99", 1180);

    /**
     * 状态
     **/
    private String money;

    /**
     * 描述
     **/
    private Integer coin;

    private static Map<String,Integer> valueMap = new HashMap<>();

    MoneyForCoin(String money, int coin) {
        this.money = money;
        this.coin = coin;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
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

    public static Map<String,Integer> getValueMap() {
        initValueMap();
        return valueMap;
    }

    public static Integer getValueMapByKey(String key) {
        initValueMap();
        return valueMap.get(key);
    }
}
