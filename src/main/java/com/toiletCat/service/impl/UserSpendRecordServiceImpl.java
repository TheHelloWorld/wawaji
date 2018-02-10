package com.toiletCat.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.toiletCat.bean.Callback;
import com.toiletCat.bean.CommonResult;
import com.toiletCat.constants.BaseConstant;
import com.toiletCat.dao.UserSpendRecordDao;
import com.toiletCat.entity.UserSpendRecord;
import com.toiletCat.enums.TradeType;
import com.toiletCat.service.UserSpendRecordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@SuppressWarnings("all")
@Service("userSpendRecordService")
public class UserSpendRecordServiceImpl extends BaseServiceImpl implements UserSpendRecordService {

    private static final Logger logger = LoggerFactory.getLogger(UserSpendRecordServiceImpl.class);

    @Autowired
    private UserSpendRecordDao userSpendRecordDao;

    /**
     * 添加用户消费记录
     * @param userSpendRecord 用户消费记录
     */
    @Override
    public CommonResult addUserSpendRecord(final UserSpendRecord userSpendRecord) {

        return exec(new Callback() {
            @Override
            public void exec() {
                userSpendRecordDao.addUserSpendRecord(userSpendRecord);
            }
        }, false, "addUserSpendRecord", userSpendRecord);
    }

    /**
     * 根据用户编号获得用户消费记录数量
     * @param userNo 用户编号
     * @return
     */
    @Override
    public CommonResult<Integer> countUserSpendRecordByUserNo(final String userNo) {

        JSONObject json = new JSONObject();
        json.put("userNo", userNo);

        return exec(new Callback() {
            @Override
            public void exec() {
                got(userSpendRecordDao.countUserSpendRecordByUserNo(userNo));
            }
        }, true, "countUserSpendRecordByUserNo", json);

    }

    /**
     * 根据用户编号分页获得用户消费记录
     * @param userNo 用户编号
     * @param startPage 开始页
     * @return
     */
    @Override
    public CommonResult<List<UserSpendRecord>> getUserSpendRecordByUserNo(final String userNo, final int startPage) {

        JSONObject json = new JSONObject();
        json.put("userNo", userNo);
        json.put("startPage", startPage);

        return exec(new Callback() {
            @Override
            public void exec() {

                List<UserSpendRecord> list = userSpendRecordDao.getUserSpendRecordByUserNo(userNo,
                        startPage, BaseConstant.DEFAULT_PAGE_SIZE);

                for(UserSpendRecord userSpendRecord : list) {
                    userSpendRecord.setTradeTypeDesc(TradeType.getValueMapByKey(userSpendRecord.getTradeType()).
                            getDesc());
                }

                got(list);
            }
        }, true, "getUserSpendRecordByUserNo", json);
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
                userSpendRecordDao.updateTradeStatusByOrderNo(orderNo, tradeStatus);
            }
        }, true, "updateTradeStatusByOrderNo", json);
    }

    @Override
    protected Logger getLogger() {
        return logger;
    }
}
