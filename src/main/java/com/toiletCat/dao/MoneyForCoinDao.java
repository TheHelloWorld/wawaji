package com.toiletCat.dao;

import com.toiletCat.entity.MoneyForCoin;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("moneyForCoinDao")
public interface MoneyForCoinDao {

    /**
     * 获得所有对应关系
     * @return
     */
    List<MoneyForCoin> getAllMoneyForCoin();

    /**
     * 获得可用对应关系数量
     * @return
     */
    Integer countAvailableMoneyForCoin();

    /**
     * 添加对应关系
     * @param moneyForCoin bean
     */
    void addMoneyForCoin(MoneyForCoin moneyForCoin);

    /**
     * 根据id获得对应关系
     * @param id 主键id
     * @return
     */
    MoneyForCoin getMoneyForCoinById(Long id);

    /**
     * 修改对应关系
     * @param moneyForCoin bean
     */
    void updateMoneyForCoin(MoneyForCoin moneyForCoin);

    /**
     * 获得所有可用的对应关系
     * @return
     */
    List<MoneyForCoin> getAllCanSeeMoneyForCoin();

}
