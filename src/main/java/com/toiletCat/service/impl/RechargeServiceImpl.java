package com.toiletCat.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.toiletCat.bean.Callback;
import com.toiletCat.bean.CommonResult;
import com.toiletCat.bean.RechargeResult;
import com.toiletCat.constants.BaseConstant;
import com.toiletCat.dao.UserDao;
import com.toiletCat.enums.MoneyForCoin;
import com.toiletCat.enums.TradeStatus;
import com.toiletCat.service.RechargeService;
import com.toiletCat.service.UserRechargeRecordService;
import com.toiletCat.service.UserSpendRecordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@SuppressWarnings("all")
@Service("rechargeService")
public class RechargeServiceImpl extends BaseServiceImpl implements RechargeService {

    private static final Logger logger = LoggerFactory.getLogger(RechargeServiceImpl.class);

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserSpendRecordService userSpendRecordService;

    @Autowired
    private UserRechargeRecordService userRechargeRecordService;

    /**
     * 充值返回结果
     * @param userNo 用户编号
     * @param rechargeResult 充值返回结果
     */
    @Override
    public CommonResult getRechargeResultByParam(final String userNo, final RechargeResult rechargeResult) {
        JSONObject json = new JSONObject();
        json.put("userNo", userNo);
        json.put("rechargeResult", rechargeResult);

        return exec(new Callback() {
            @Override
            public void exec() {

                // 判断用户编号是否存在
                if(userDao.countUserByUserNo(userNo) == 0) {
                    logger.warn("getRechargeResultByParam userNo not exists param:"+ rechargeResult);
                    return;
                }

                Long money;
                try {
                    money = Long.valueOf(rechargeResult.getMoney());
                } catch (Exception e) {
                    logger.error("getRechargeResultByParam transMoney err param:" + rechargeResult, e);
                    return;
                }

                Integer coin = MoneyForCoin.getValueMapByKey(money);

                if(coin == null) {
                    logger.warn("getRechargeResultByParam money is wrong param:"+ rechargeResult);
                    return;
                }

                // 我方订单号
                String orderNo = rechargeResult.getOrderNo();

                CommonResult<BigDecimal> orderAmount = userRechargeRecordService.getAmountByUserNoAndOrderNo(userNo, orderNo);

                if(!orderAmount.success()) {
                    logger.error("getRechargeResultByParam countNumByUserNoAndOrderNo error param:"+ rechargeResult);
                    return;
                }

                if(orderAmount.getValue() == null) {
                    logger.warn("getRechargeResultByParam order not exists param:"+ rechargeResult);
                    return;
                }

                // 判断返回金额是否正确
                if(orderAmount.getValue().longValue() != money) {
                    logger.warn("getRechargeResultByParam return money not match our amount:" + orderAmount.getValue()
                            + " param:"+ rechargeResult);
                    return;
                }

                // 交易结果
                String resultTradeStatus = rechargeResult.getTradeStatus();

                TradeStatus tradeStatus = TradeStatus.SUCCESS;

                // 判断结果是否成功
                if(!BaseConstant.RECHARGE_RESULT_TRADE_STATUS.equals(tradeStatus)) {
                    tradeStatus = TradeStatus.FAIL;
                }

                // 修改充值记录交易状态
                CommonResult updateRechargeResult = userRechargeRecordService.
                        updateTradeStatusByOrderNo(orderNo, tradeStatus.getStatus());

                if(!updateRechargeResult.success()) {
                    logger.warn("getRechargeResultByParam recharge updateTradeStatusByOrderNo error:" + rechargeResult);
                }

                // 修改用户游戏币记录交易状态
                CommonResult updateSpendResult = userSpendRecordService.
                        updateTradeStatusByOrderNo(orderNo, tradeStatus.getStatus());

                if(!updateSpendResult.success()) {
                    logger.warn("getRechargeResultByParam spend updateTradeStatusByOrderNo error:" + rechargeResult);
                }

                if(tradeStatus == TradeStatus.SUCCESS) {
                    // 添加用户游戏币
                    userDao.updateUserCoinByUserNo(coin, userNo);
                }

            }
        }, "getRechargeResultByParam", json.toJSONString());

    }

    @Override
    protected Logger getLogger() {
        return logger;
    }
}
