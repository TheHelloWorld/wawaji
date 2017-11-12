package com.lzg.wawaji.service.impl;

import com.lzg.wawaji.constants.BaseConstant;
import com.lzg.wawaji.dao.UserRechargeRecordDao;
import com.lzg.wawaji.entity.UserRechargeRecord;
import com.lzg.wawaji.entity.UserSpendRecord;
import com.lzg.wawaji.enums.TradeStatus;
import com.lzg.wawaji.enums.TradeType;
import com.lzg.wawaji.service.UserRechargeRecordService;
import com.alibaba.fastjson.JSONObject;
import com.lzg.wawaji.service.UserSpendRecordService;
import com.lzg.wawaji.utils.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service("userRechargeRecordService")
public class UserRechargeRecordServiceImpl implements UserRechargeRecordService {

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
    public void addUserRechargeRecord(UserRechargeRecord userRechargeRecord) {
        try {
            userRechargeRecordDao.addUserRechargeRecord(userRechargeRecord);

            // TODO:待充值功能之后完善
            Date date = new Date();

            UserSpendRecord userSpendRecord = new UserSpendRecord();
            userSpendRecord.setTradeStatus(TradeStatus.SUCCESS.getStatus());
            userSpendRecord.setTradeTime(date);
            userSpendRecord.setTradeDate(DateUtil.getDateByTime(date));
            userSpendRecord.setTradeType(TradeType.RECHARGE.getType());
            userSpendRecord.setUserNo(userRechargeRecord.getUserNo());

            userSpendRecordService.addUserSpendRecord(userSpendRecord);
        } catch (Exception e) {
            logger.error("{} addUserRechargeRecord param{} error:" + e, BaseConstant.LOG_ERR_MSG, userRechargeRecord, e);
        }
    }

    /**
     * 根据用户编号获得用户充值记录数
     * @param userNo 用户编号
     * @return
     */
    @Override
    public Integer countUserRechargeRecordByUserNo(String userNo) {
        try {
            userRechargeRecordDao.countUserRechargeRecordByUserNo(userNo);
        } catch (Exception e) {
            logger.error("{} countUserRechargeRecordByUserNo param{} error:" + e, BaseConstant.LOG_ERR_MSG, userNo, e);
        }
        return null;
    }

    /**
     * 根据用户编号分页获得用户充值记录
     * @param userNo 用户编号
     * @param startPage 开始页
     * @return
     */
    @Override
    public List<UserRechargeRecord> getUserRechargeRecordByUserNo(String userNo, int startPage) {
        try {
            return userRechargeRecordDao.getUserRechargeRecordByUserNo(userNo, startPage, BaseConstant.DEFAULT_PAGE_SIZE);
        } catch (Exception e) {
            JSONObject json = new JSONObject();
            json.put("userNo",userNo);
            json.put("startPage",startPage);
            logger.error("{} getUserRechargeRecordByUserNo param{} error:" + e,
                    BaseConstant.LOG_ERR_MSG, json, e);
        }
        return null;
    }

    /**
     * 根据交易日期和交易状态获得充值记录数量
     * @param tradeDate 交易日期
     * @param tradeStatus 交易状态
     * @return
     */
    @Override
    public Integer countUserRechargeRecordByTradeDateAndTradeStatus(Integer tradeDate, Integer tradeStatus) {
        try {
            return userRechargeRecordDao.countUserRechargeRecordByTradeDateAndTradeStatus(tradeDate, tradeStatus);
        } catch (Exception e) {
            JSONObject json = new JSONObject();
            json.put("tradeDate",tradeDate);
            json.put("tradeStatus",tradeStatus);
            logger.error("{} countUserRechargeRecordByTradeDateAndTradeStatus param{} error:" + e,
                    BaseConstant.LOG_ERR_MSG, json, e);
        }
        return null;
    }

    /**
     * 根据交易日期和交易状态分页获得所有充值记录
     * @param tradeDate 交易日期
     * @param tradeStatus 交易状态
     * @param startPage 开始页
     * @return
     */
    @Override
    public List<UserRechargeRecord> getUserRechargeRecordByTradeDateAndTradeStatus(Integer tradeDate,
                                                                                   Integer tradeStatus,
                                                                                   int startPage) {
        try {
            return userRechargeRecordDao.getUserRechargeRecordByTradeDateAndTradeStatus(tradeDate,
                    tradeStatus, startPage, BaseConstant.DEFAULT_PAGE_SIZE);
        } catch (Exception e) {
            JSONObject json = new JSONObject();
            json.put("tradeDate",tradeDate);
            json.put("tradeStatus",tradeStatus);
            json.put("tradeDate",startPage);
            json.put("pageSize",BaseConstant.DEFAULT_PAGE_SIZE);
            logger.error("{} getUserRechargeRecordByTradeDateAndTradeStatus param{} error:" + e,
                    BaseConstant.LOG_ERR_MSG, json, e);
        }
        return null;
    }

    /**
     * 根据交易日期和交易状态获得汇总信息
     * @param tradeDate 交易日期
     * @param tradeStatus 交易状态
     * @return
     */
    @Override
    public Map<String, Object> getSumRechargeAmountAndCountByTradeDateAndTradeStatus(Integer tradeDate,
                                                                                     Integer tradeStatus) {
        try {
            return userRechargeRecordDao.getSumRechargeAmountAndCountByTradeDateAndTradeStatus(tradeDate, tradeStatus);
        } catch (Exception e) {
            JSONObject json = new JSONObject();
            json.put("tradeDate",tradeDate);
            json.put("tradeStatus",tradeStatus);
            logger.error("{} getSumRechargeAmountAndCountByTradeDateAndTradeStatus param{} error:" + e,
                    BaseConstant.LOG_ERR_MSG, json, e);
        }
        return null;
    }

}
