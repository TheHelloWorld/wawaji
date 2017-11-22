package com.lzg.wawaji.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lzg.wawaji.bean.Callback;
import com.lzg.wawaji.bean.CommonResult;
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

@SuppressWarnings("all")
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
    public CommonResult addUser(final User user) {

        return exec(new Callback() {
            @Override
            public void exec() {
                userDao.addUser(user);
            }
        }, "addUserToy", JSON.toJSONString(user));
    }

    /**
     * 根据用户编号,娃娃机编号判断用户是否可以进行游戏若可以则直接扣除相应游戏币数
     * @param userNo 用户编号
     * @param machineNo 娃娃机编号
     * @return
     */
    @Override
    @Transactional
    public CommonResult<String> userPlay(final String userNo, final String machineNo) {

        JSONObject json = new JSONObject();
        json.put("userNo",userNo);
        json.put("machineNo",machineNo);

        return exec(new Callback() {
            @Override
            public void exec() {

                // 获得用户当前游戏币数
                Integer userCoin = userDao.getUserCoinByUserNo(userNo);
                // 获得机器信息
                CommonResult<Integer> needCoinResult = machineService.getCoinByMachineNo(machineNo);

                Integer needCoin;

                if(needCoinResult.success()) {

                    needCoin = needCoinResult.getValue();
                } else {
                    logger.error("{} getCoinByMachineNo error:", BaseConstant.LOG_ERR_MSG);
                    got(BaseConstant.FAIL);
                    return;
                }


                // 判断用户游戏币是否足够
                if(userCoin >= needCoin) {

                    try(RedisUtil redisUtil = new RedisUtil(BaseConstant.REDIS)) {
                        // 判断当前机器是否已有用户使用
                        if(redisUtil.setnx(machineNo, userNo) == 0) {
                            // 若当前机器已被占用
                            got(BaseConstant.MARCHINE_ALREADY_IN_UES);
                            return;
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

                        got(BaseConstant.SUCCESS);
                        return;
                    } catch(Exception e) {
                        logger.error("{} 扣除用户游戏币失败" + e,BaseConstant.LOG_ERR_MSG,e);
                        userSpendRecord.setTradeStatus(TradeStatus.FAIL.getStatus());
                        userSpendRecordService.addUserSpendRecord(userSpendRecord);

                        got(BaseConstant.DEDUCTION_COIN_FAIL);
                        return;

                    }
                }

                logger.info("{} 用户游戏币不足,用户游戏币数:{},所需游戏币数:{}", BaseConstant.LOG_MSG, userCoin, needCoin);

                got(BaseConstant.NOT_ENOUGH_COIN);
                return;
            }
        }, "userPlay", json.toJSONString());
    }

    @Override
    protected Logger getLogger() {
        return logger;
    }
}
