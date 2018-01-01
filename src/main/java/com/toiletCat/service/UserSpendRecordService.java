package com.toiletCat.service;

import com.toiletCat.bean.CommonResult;
import com.toiletCat.entity.UserSpendRecord;

import java.util.List;

public interface UserSpendRecordService {

    /**
     * 添加用户消费记录
     * @param userSpendRecord 用户消费记录
     */
    CommonResult addUserSpendRecord(UserSpendRecord userSpendRecord);

    /**
     * 根据用户编号获得所有用户消费记录
     * @param userNo 用户编号
     * @return
     */
    CommonResult<Integer> countUserSpendRecordByUserNo(String userNo);

    /**
     * 根据用户编号分页获得用户消费记录
     * @param userNo 用户编号
     * @param startPage 开始页
     * @return
     */
    CommonResult<List<UserSpendRecord>> getUserSpendRecordByUserNo(String userNo, int startPage);

    /**
     * 根据订单编号修改交易状态
     * @param orderNo 订单编号
     * @param tradeStatus 交易状态
     */
    CommonResult updateTradeStatusByOrderNo(String orderNo, Integer tradeStatus);

}
