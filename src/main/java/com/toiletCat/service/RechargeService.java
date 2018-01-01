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
}
