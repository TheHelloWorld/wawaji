package com.toiletCat.service;

import com.toiletCat.bean.CommonResult;
import com.toiletCat.bean.RechargeResult;
import com.toiletCat.entity.MoneyForCoin;

import java.math.BigDecimal;

public interface RechargeService {

    /**
     * 用户充值
     * @param userNo 用户编号
     * @param amount 金额
     * @param rechargeType 充值类型
     * @return
     */
    CommonResult<String> userRecharge(String userNo, String amount, String rechargeType);


    /**
     * 充值返回结果
     *
     * @param userNo         用户编号
     * @param rechargeResult 充值返回结果
     */
    CommonResult getRechargeResultByParam(String userNo, RechargeResult rechargeResult);

    /**
     * 根据订单号获得充值结果
     *
     * @param orderNo 订单号
     * @return
     */
    CommonResult<String> getRechargeResultByOrderNo(String orderNo);

    /**
     * 获得所有状态不为终态的交易的结果
     *
     * @param userNo 用户编号
     * @return
     */
    CommonResult getInitRechargeResultByOrderInfo(String userNo);

    /**
     * 获得应充游戏币数(判断首充用)
     *
     * @param userNo       用户编号
     * @param moneyForCoin 对应关系bean
     * @return
     */
    Integer getCoinByMoneyForCoin(String userNo, MoneyForCoin moneyForCoin);

    /**
     * 获得用户首充标志位
     *
     * @param userNo 用户编号
     * @return
     */
    String getFirstFlag(String userNo);

    /**
     * 获得用户限充当前次数
     *
     * @param userNo       用户编号
     * @param moneyForCoin 对应关系bean
     * @return
     */
    Integer getLimitRechargeByUserNo(String userNo, MoneyForCoin moneyForCoin);

}
