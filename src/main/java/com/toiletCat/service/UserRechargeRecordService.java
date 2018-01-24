package com.toiletCat.service;

import com.toiletCat.bean.CommonResult;
import com.toiletCat.entity.UserRechargeRecord;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface UserRechargeRecordService {

    /**
     * 添加用户充值记录
     * @param userRechargeRecord 用户充值记录
     */
    CommonResult addUserRechargeRecord(UserRechargeRecord userRechargeRecord);

    /**
     * 根据用户编号获得用户充值记录数
     * @param userNo 用户编号
     * @return
     */
    CommonResult<Integer> countUserRechargeRecordByUserNo(String userNo);

    /**
     * 根据用户编号和交易状态获得用户充值记录数
     * @param userNo 用户编号
     * @param tradeStatus 交易状态
     * @return
     */
    CommonResult<Integer> countUserRechargeRecordByUserNoAndTradeStatus(String userNo, Integer tradeStatus);

    /**
     * 根据用户编号分页获得用户充值记录
     * @param userNo 用户编号
     * @param startPage 开始页
     * @return
     */
    CommonResult<List<UserRechargeRecord>> getUserRechargeRecordByUserNo(String userNo, int startPage);

    /**
     * 根据交易日期和交易状态获得充值记录数量
     * @param tradeDate 交易日期
     * @param tradeStatus 交易状态
     * @return
     */
    CommonResult<Integer> countUserRechargeRecordByTradeDateAndTradeStatus(Integer tradeDate, Integer tradeStatus);

    /**
     * 根据交易日期和交易状态分页获得所有充值记录
     * @param tradeDate 交易日期
     * @param tradeStatus 交易状态
     * @param startPage 开始页
     * @return
     */
    CommonResult<List<UserRechargeRecord>> getUserRechargeRecordByTradeDateAndTradeStatus(Integer tradeDate,
                                                                                          Integer tradeStatus,
                                                                                          int startPage);

    /**
     * 根据交易日期和交易状态获得汇总信息
     * @param tradeDate 交易日期
     * @param tradeStatus 交易状态
     * @return
     */
    CommonResult<Map<String, Object>> getSumRechargeAmountAndCountByTradeDateAndTradeStatus(Integer tradeDate,
                                                                                            Integer tradeStatus);

    /**
     * 根据用户编号和订单号获得金额
     * @param userNo 用户编号
     * @param orderNo 订单编号
     * @return
     */
    CommonResult<BigDecimal> getAmountByUserNoAndOrderNo(String userNo, String orderNo);

    /**
     * 根据订单编号修改交易状态
     * @param orderNo 订单编号
     * @param tradeStatus 交易状态
     */
    CommonResult updateTradeStatusByOrderNo(String orderNo, Integer tradeStatus);

    /**
     * 根据用户编号和订单号获得交易结果
     * @param userNo 用户编号
     * @param orderNo 订单编号
     * @return
     */
    CommonResult<Integer> getTradeStatusByOrderNo(String userNo, String orderNo);

    /**
     * 根据用户编号获得所有非终态订单
     * @param userNo 用户编号
     * @return
     */
    CommonResult<List<UserRechargeRecord>> getAllInitRecordByUserNo(String userNo);
}
