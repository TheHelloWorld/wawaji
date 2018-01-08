package com.toiletCat.service;

import com.toiletCat.bean.CommonResult;
import com.toiletCat.bean.RechargeResult;

public interface RechargeService {

    /**
     * 充值返回结果
     * @param userNo 用户编号
     * @param rechargeResult 充值返回结果
     */
    CommonResult getRechargeResultByParam(String userNo, RechargeResult rechargeResult);

    /**
     * 根据订单号获得充值结果
     * @param orderNo 订单号
     * @return
     */
    CommonResult<String> getRechargeResultByOrderNo(String orderNo);
}
