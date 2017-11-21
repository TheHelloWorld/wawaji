package com.lzg.wawaji.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.lzg.wawaji.constants.BaseConstant;
import com.lzg.wawaji.dao.UserDao;
import com.lzg.wawaji.entity.User;
import com.lzg.wawaji.entity.UserSpendRecord;
import com.lzg.wawaji.enums.TradeStatus;
import com.lzg.wawaji.enums.TradeType;
import com.lzg.wawaji.service.MachineService;
import com.lzg.wawaji.service.UserService;
import com.lzg.wawaji.service.UserSpendRecordService;
import com.lzg.wawaji.utils.DateUtil;
import com.lzg.wawaji.utils.RedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;

@Service("userService")
public class UserServiceImpl extends BaseServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserDao userDao;

    @Autowired
    private MachineService machineService;

    @Autowired
    private UserSpendRecordService userSpendRecordService;

    /**
     * 添加用户
     * @param user 用户Bean
     */
    @Override
    public void addUser(User user) {
        try {
            userDao.addUser(user);
        } catch (Exception e) {
            JSONObject json = new JSONObject();
            json.put("user",user);
            logger.error("{} addUser param:{} error"+ e, BaseConstant.LOG_ERR_MSG, json, e);
        }
    }

    /**
     * 根据用户编号,娃娃机编号判断用户是否可以进行游戏若可以则直接扣除相应游戏币数
     * @param userNo 用户编号
     * @param machineNo 娃娃机编号
     * @return
     */
    @Override
    @Transactional
    public String userPlay(String userNo, String machineNo) {
        try {
            // 获得用户当前游戏币数
            Integer userCoin = userDao.getUserCoinByUserNo(userNo);
            // 获得机器信息
            Integer needCoin = machineService.getCoinByMachineNo(machineNo);


            // 判断用户游戏币是否足够
            if(userCoin >= needCoin) {

                try(RedisUtil redisUtil = new RedisUtil(BaseConstant.REDIS)) {
                    // 判断当前机器是否已有用户使用
                    if(redisUtil.setnx(machineNo, userNo) == 0) {
                        // 若当前机器已被占用
                        return BaseConstant.MARCHINE_ALREADY_IN_UES;
                    }
                } catch (Exception e) {
                    logger.error("{} redis error:" + e, BaseConstant.LOG_ERR_MSG, e);
                }

                // 添加用户消费记录
                Date date = new Date();
                UserSpendRecord userSpendRecord = new UserSpendRecord();

                userSpendRecord.setUserNo(userNo);
                userSpendRecord.setCoin(needCoin);
                userSpendRecord.setTradeType(TradeType.SPEND.getType());
                userSpendRecord.setTradeDate(DateUtil.getDateByTime(date));
                userSpendRecord.setTradeTime(date);

                try {
                    // 扣除用户游戏币
                    userDao.updateUserCoinByUserNo( -needCoin, userNo);

                    userSpendRecord.setTradeStatus(TradeStatus.SUCCESS.getStatus());
                    userSpendRecordService.addUserSpendRecord(userSpendRecord);
                    logger.info("{} 成功扣除用户:{} 游戏币数:{}",BaseConstant.LOG_MSG, userNo, needCoin);

                    return BaseConstant.SUCCESS;
                } catch(Exception e) {
                    logger.error("{} 扣除用户游戏币失败" + e,BaseConstant.LOG_ERR_MSG,e);
                    userSpendRecord.setTradeStatus(TradeStatus.FAIL.getStatus());
                    userSpendRecordService.addUserSpendRecord(userSpendRecord);

                    return BaseConstant.DEDUCTION_COIN_FAIL;

                }
            }

            logger.info("{} 用户游戏币不足,用户游戏币数:{},所需游戏币数:{}", BaseConstant.LOG_MSG, userCoin, needCoin);

            return BaseConstant.NOT_ENOUGH_COIN;
        } catch (Exception e) {
            JSONObject json = new JSONObject();
            json.put("userNo",userNo);
            json.put("machineNo",machineNo);
            logger.error("{} userPlay param:{} error"+ e, BaseConstant.LOG_ERR_MSG, json, e);

            return BaseConstant.SYSTEM_ERROR;
        }
    }

    @Override
    protected Logger getLogger() {
        return logger;
    }
}
