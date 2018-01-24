package com.toiletCat.service;

import com.toiletCat.bean.CommonResult;
import com.toiletCat.entity.MoneyForCoin;

import java.util.List;

public interface MoneyForCoinService {

    /**
     * 添加对应关系
     * @param moneyForCoin bean
     */
    CommonResult addMoneyForCoin(MoneyForCoin moneyForCoin);

    /**
     * 获得所有对应关系
     * @return
     */
    CommonResult<List<MoneyForCoin>> getAllMoneyForCoin();

    /**
     * 根据id获得对应关系
     * @param id 主键id
     * @return
     */
    CommonResult<MoneyForCoin> getMoneyForCoinById(Long id);

    /**
     * 修改对应关系
     * @param moneyForCoin bean
     */
    CommonResult updateMoneyForCoin(MoneyForCoin moneyForCoin);

    /**
     * 获得所有可用的对应关系
     * @return
     */
    CommonResult<List<MoneyForCoin>> getAllCanSeeMoneyForCoin();

    /**
     * 根据钱数获得对应的对应关系
     * @param money 钱数
     * @return
     */
    MoneyForCoin getMoneyForCoinByMoney(String money);

}
