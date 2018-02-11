package com.toiletCat.service.impl;

import com.toiletCat.bean.Callback;
import com.toiletCat.bean.CommonResult;
import com.toiletCat.constants.BaseConstant;
import com.toiletCat.dao.UserRechargeRecordDao;
import com.toiletCat.entity.UserRechargeRecord;
import com.toiletCat.service.UserRechargeRecordService;
import com.alibaba.fastjson.JSONObject;
import com.toiletCat.service.UserSpendRecordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@SuppressWarnings("all")
@Service("userRechargeRecordService")
public class UserRechargeRecordServiceImpl extends BaseServiceImpl implements UserRechargeRecordService {

    private static final Logger logger = LoggerFactory.getLogger(UserRechargeRecordServiceImpl.class);

    @Autowired
    private UserRechargeRecordDao userRechargeRecordDao;

    @Autowired
    private UserSpendRecordService userSpendRecordService;

    /**
     * 添加用户充值记录
     * @param userRechargeRecord 用户充值记录
     */
    @Override
    public CommonResult addUserRechargeRecord(final UserRechargeRecord userRechargeRecord) {

        return exec(new Callback() {
            @Override
            public void exec() {
                userRechargeRecordDao.addUserRechargeRecord(userRechargeRecord);

            }
        }, false, "addUserRechargeRecord", userRechargeRecord);
    }

    /**
     * 根据用户编号获得用户充值记录数
     * @param userNo 用户编号
     * @return
     */
    @Override
    public CommonResult<Integer> countUserRechargeRecordByUserNo(final String userNo) {

        JSONObject json = new JSONObject();
        json.put("userNo",userNo);

        return exec(new Callback() {
            @Override
            public void exec() {
                got(userRechargeRecordDao.countUserRechargeRecordByUserNo(userNo));
            }
        }, true, "countUserRechargeRecordByUserNo", json);
    }

    /**
     * 根据用户编号和交易状态获得用户充值记录数
     * @param userNo 用户编号
     * @param tradeStatus 交易状态
     * @return
     */
    @Override
    public CommonResult<Integer> countUserRechargeRecordByUserNoAndTradeStatus(final String userNo,
                                                                               final Integer tradeStatus) {

        JSONObject json = new JSONObject();
        json.put("userNo",userNo);
        json.put("tradeStatus",tradeStatus);

        return exec(new Callback() {
            @Override
            public void exec() {
                got(userRechargeRecordDao.countUserRechargeRecordByUserNoAndTradeStatus(userNo, tradeStatus));
            }
        }, true, "countUserRechargeRecordByUserNoAndTradeStatus", json);
    }

    /**
     * 根据用户编号分页获得用户充值记录
     * @param userNo 用户编号
     * @param startPage 开始页
     * @return
     */
    @Override
    public CommonResult<List<UserRechargeRecord>> getUserRechargeRecordByUserNo(final String userNo,
                                                                                final int startPage) {

        JSONObject json = new JSONObject();
        json.put("userNo",userNo);
        json.put("startPage",startPage);

        return exec(new Callback() {
            @Override
            public void exec() {
                got(userRechargeRecordDao.getUserRechargeRecordByUserNo(userNo, startPage, BaseConstant.DEFAULT_PAGE_SIZE));
            }
        }, true, "getUserRechargeRecordByUserNo", json);
    }

    /**
     * 根据交易日期和交易状态获得充值记录数量
     * @param tradeDate 交易日期
     * @param tradeStatus 交易状态
     * @return
     */
    @Override
    public CommonResult<Integer> countUserRechargeRecordByTradeDateAndTradeStatus(final Integer tradeDate,
                                                                                  final Integer tradeStatus) {

        JSONObject json = new JSONObject();
        json.put("tradeDate",tradeDate);
        json.put("tradeStatus",tradeStatus);

        return exec(new Callback() {
            @Override
            public void exec() {
                got(userRechargeRecordDao.countUserRechargeRecordByTradeDateAndTradeStatus(tradeDate, tradeStatus));
            }
        }, true, "countUserRechargeRecordByTradeDateAndTradeStatus", json);
    }

    /**
     * 根据交易日期和交易状态分页获得所有充值记录
     * @param tradeDate 交易日期
     * @param tradeStatus 交易状态
     * @param startPage 开始页
     * @return
     */
    @Override
    public CommonResult<List<UserRechargeRecord>> getUserRechargeRecordByTradeDateAndTradeStatus(final Integer tradeDate,
                                                                                   final Integer tradeStatus,
                                                                                   final int startPage) {
        JSONObject json = new JSONObject();
        json.put("tradeDate",tradeDate);
        json.put("tradeStatus",tradeStatus);
        json.put("tradeDate",startPage);
        json.put("pageSize",BaseConstant.DEFAULT_PAGE_SIZE);

        return exec(new Callback() {
            @Override
            public void exec() {
                got(userRechargeRecordDao.getUserRechargeRecordByTradeDateAndTradeStatus(tradeDate,
                        tradeStatus, startPage, BaseConstant.DEFAULT_PAGE_SIZE));
            }
        }, true, "getUserRechargeRecordByTradeDateAndTradeStatus", json);

    }

    /**
     * 根据交易日期和交易状态获得汇总信息
     * @param tradeDate 交易日期
     * @param tradeStatus 交易状态
     * @return
     */
    @Override
    public CommonResult<Map<String, Object>> getSumRechargeAmountAndCountByTradeDateAndTradeStatus(final Integer tradeDate,
                                                                                     final Integer tradeStatus) {

        JSONObject json = new JSONObject();
        json.put("tradeDate", tradeDate);
        json.put("tradeStatus", tradeStatus);

        return exec(new Callback() {
            @Override
            public void exec() {
                got(userRechargeRecordDao.getSumRechargeAmountAndCountByTradeDateAndTradeStatus(tradeDate, tradeStatus));
            }
        }, true, "getSumRechargeAmountAndCountByTradeDateAndTradeStatus", json);

    }

    /**
     * 根据用户编号和订单号获得金额
     * @param userNo 用户编号
     * @param orderNo 订单编号
     * @return
     */
    @Override
    public CommonResult<BigDecimal> getAmountByUserNoAndOrderNo(final String userNo, final String orderNo) {
        JSONObject json = new JSONObject();
        json.put("userNo", userNo);
        json.put("orderNo", orderNo);

        return exec(new Callback() {
            @Override
            public void exec() {
                got(userRechargeRecordDao.getAmountByUserNoAndOrderNo(userNo, orderNo));
            }
        }, true, "countNumByUserNoAndOrderNo", json);

    }

    /**
     * 根据订单编号修改交易状态
     * @param orderNo 订单编号
     * @param tradeStatus 交易状态
     */
    @Override
    public CommonResult updateTradeStatusByOrderNo(final String orderNo, final Integer tradeStatus) {

        JSONObject json = new JSONObject();
        json.put("orderNo", orderNo);
        json.put("tradeStatus", tradeStatus);

        return exec(new Callback() {
            @Override
            public void exec() {
                userRechargeRecordDao.updateTradeStatusByOrderNo(orderNo, tradeStatus);
            }
        }, true, "updateTradeStatusByOrderNo", json);
    }

    /**
     * 根据用户编号和订单号获得交易结果
     * @param userNo 用户编号
     * @param orderNo 订单编号
     * @return
     */
    @Override
    public CommonResult<Integer> getTradeStatusByOrderNo(final String userNo, final String orderNo) {
        JSONObject json = new JSONObject();
        json.put("orderNo", orderNo);
        json.put("userNo", userNo);

        return exec(new Callback() {
            @Override
            public void exec() {
                got(userRechargeRecordDao.getTradeStatusByOrderNo(userNo, orderNo));
            }
        }, true, "getTradeStatusByOrderNo", json);
    }

    /**
     * 根据用户编号获得所有非终态订单
     * @param userNo 用户编号
     * @return
     */
    @Override
    public CommonResult<List<UserRechargeRecord>> getAllInitRecordByUserNo(final String userNo) {
        JSONObject json = new JSONObject();
        json.put("userNo", userNo);

        return exec(new Callback() {
            @Override
            public void exec() {
                got(userRechargeRecordDao.getAllInitRecordByUserNo(userNo));
            }
        }, true, "getAllInitRecordByUserNo", json);
    }

    @Override
    protected Logger getLogger() {
        return logger;
    }
}
