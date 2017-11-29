package com.lzg.wawaji.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.lzg.wawaji.bean.Callback;
import com.lzg.wawaji.bean.CommonResult;
import com.lzg.wawaji.bean.UserMachine;
import com.lzg.wawaji.constants.BaseConstant;
import com.lzg.wawaji.dao.CatchRecordDao;
import com.lzg.wawaji.dao.UserDao;
import com.lzg.wawaji.entity.CatchRecord;
import com.lzg.wawaji.entity.User;
import com.lzg.wawaji.entity.UserSpendRecord;
import com.lzg.wawaji.enums.CatchStatus;
import com.lzg.wawaji.enums.TradeStatus;
import com.lzg.wawaji.enums.TradeType;
import com.lzg.wawaji.service.MachineService;
import com.lzg.wawaji.service.UserService;
import com.lzg.wawaji.service.UserSpendRecordService;
import com.lzg.wawaji.utils.*;
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
    private CatchRecordDao catchRecordDao;

    @Autowired
    private MachineService machineService;

    @Autowired
    private UserSpendRecordService userSpendRecordService;

    /**
     * 用户注册或登录
     *
     * @param mobileNo 手机号
     */
    @Override
    public CommonResult<User> registerOrLoginUser(final String mobileNo) {

        JSONObject json = new JSONObject();
        json.put("mobileNo", mobileNo);

        return exec(new Callback() {
            @Override
            public void exec() {

                User user;
                // 若当前用户为新用户则添加用户
                if (userDao.countUserByMobileNo(mobileNo) == 0) {
                    user = new User();

                    PropertiesUtil systemProperties = new PropertiesUtil("system");

                    user.setMobileNo(mobileNo);
                    user.setUserCoin(0);
                    user.setUserImg(systemProperties.getProperty("user_default_img"));
                    String userNo = UUIDUtil.generateUUID();
                    user.setUserNo(userNo);
                    user.setUserName(Random.getRandomString(18));
                    userDao.addUser(user);

                } else {
                    user = userDao.getUserByMobileNo(mobileNo);
                }

                got(user);
                return;
            }
        }, "registerOrLoginUser", json.toJSONString());
    }

    /**
     * 验证短信验证码
     *
     * @param ticket
     * @param mobileNo
     * @return
     */
    @Override
    public CommonResult<String> verifyCode(final String ticket, final String mobileNo) {

        JSONObject json = new JSONObject();
        json.put("ticket", ticket);
        json.put("mobileNo", mobileNo);

        return exec(new Callback() {
            @Override
            public void exec() {

                try (RedisUtil redisUtil = new RedisUtil("redis")) {

                    String verifyCode = redisUtil.get("sms-" + mobileNo);

                    // 若验证码不对
                    if (!ticket.equals(verifyCode)) {
                        got(BaseConstant.VCODE_ERR_MSG);
                        return;
                    }

                    got(BaseConstant.SUCCESS);
                    return;

                } catch (Exception e) {
                    logger.error("{} registerOrLoginUser redis error:" + e, BaseConstant.LOG_ERR_MSG, e);
                    got(BaseConstant.VCODE_ERR_MSG);
                    return;
                }
            }
        }, "verifyCode", json.toJSONString());
    }

    /**
     * 根据用户编号,娃娃机编号判断用户是否可以进行游戏若可以则直接扣除相应游戏币数
     *
     * @param userNo    用户编号
     * @param machineNo 娃娃机编号
     * @return
     */
    @Override
    @Transactional
    public CommonResult<String> userPlay(final String userNo, final String machineNo) {

        JSONObject json = new JSONObject();
        json.put("userNo", userNo);
        json.put("machineNo", machineNo);

        return exec(new Callback() {
            @Override
            public void exec() {

                // 获得用户当前游戏币数
                Integer userCoin = userDao.getUserCoinByUserNo(userNo);
                // 获得机器信息
                CommonResult<Integer> needCoinResult = machineService.getCoinByMachineNo(machineNo);

                Integer needCoin;

                if (needCoinResult.success()) {

                    needCoin = needCoinResult.getValue();
                } else {
                    logger.error("{} getCoinByMachineNo error:", BaseConstant.LOG_ERR_MSG);
                    got(BaseConstant.DEDUCTION_COIN_FAIL);
                    return;
                }

                // 判断用户游戏币是否足够
                if (userCoin < needCoin) {
                    logger.info("{} 用户游戏币不足,用户游戏币数:{},所需游戏币数:{}", BaseConstant.LOG_MSG, userCoin, needCoin);
                    got(BaseConstant.NOT_ENOUGH_COIN);
                    return;
                }

                try (RedisUtil redisUtil = new RedisUtil(BaseConstant.REDIS)) {
                    String key = BaseConstant.MACHINE_IN_USE.replace("#{}", machineNo);
                    // 判断当前机器是否已有用户使用
                    if (redisUtil.setnx(key, userNo) == 0) {
                        // 若当前机器已被占用
                        got(BaseConstant.MARCHINE_ALREADY_IN_UES);
                        return;
                    }
                } catch (Exception e) {
                    logger.error("{} userPlay redis error:" + e, BaseConstant.LOG_ERR_MSG, e);
                    got(BaseConstant.MARCHINE_ALREADY_IN_UES);
                    return;
                }

                // 获得玩具编号和玩具图片地址
                CommonResult<UserMachine> userMachineCommonResult =
                        machineService.getToyNoAndToyImgByMachineNo(machineNo);

                if(!userMachineCommonResult.success()) {
                    got(BaseConstant.DEDUCTION_COIN_FAIL);
                    return;
                }

                UserMachine userMachine = userMachineCommonResult.getValue();

                // 添加用户消费记录
                UserSpendRecord userSpendRecord = new UserSpendRecord();

                Date date = new Date();
                // 用户编号
                userSpendRecord.setUserNo(userNo);
                // 消费游戏币数
                userSpendRecord.setCoin(needCoin);
                // 消费类型
                userSpendRecord.setTradeType(TradeType.SPEND.getType());
                // 消费日期
                userSpendRecord.setTradeDate(DateUtil.getDateByTime(date));
                // 消费时间
                userSpendRecord.setTradeTime(date);

                try {
                    // 扣除用户游戏币
                    userDao.updateUserCoinByUserNo(-needCoin, userNo);

                    userSpendRecord.setTradeStatus(TradeStatus.SUCCESS.getStatus());
                    userSpendRecordService.addUserSpendRecord(userSpendRecord);

                    // 添加娃娃抓取记录
                    CatchRecord catchRecord = new CatchRecord();
                    // 抓取id
                    String catchId = UUIDUtil.generateUUID();
                    catchRecord.setCatchId(catchId);
                    // 用户编号
                    catchRecord.setUserNo(userNo);
                    // 玩具编号
                    catchRecord.setToyNo(userMachine.getToyNo());
                    // 玩具图片
                    catchRecord.setToyImg(userMachine.getToyImg());
                    // 抓取状态 默认为等待
                    catchRecord.setCatchStatus(CatchStatus.CATCH_WAIT.getStatus());

                    catchRecordDao.addCatchRecord(catchRecord);

                    // 构建返回字符串
                    JSONObject returnJson = new JSONObject();

                    returnJson.put("catchId", catchId);
                    returnJson.put("result", BaseConstant.SUCCESS);

                    logger.info("{} 成功扣除用户:{} 游戏币数:{} 结果{}", BaseConstant.LOG_MSG, userNo, needCoin,
                            returnJson.toJSONString());

                    got(returnJson.toJSONString());
                    return;
                } catch (Exception e) {
                    logger.error("{} 扣除用户游戏币失败" + e, BaseConstant.LOG_ERR_MSG, e);
                    userSpendRecord.setTradeStatus(TradeStatus.FAIL.getStatus());
                    userSpendRecordService.addUserSpendRecord(userSpendRecord);
                    got(BaseConstant.DEDUCTION_COIN_FAIL);
                    return;
                }
            }
        }, "userPlay", json.toJSONString());
    }

    /**
     * 发送短信验证码方法
     *
     * @param mobileNo 手机号
     * @return
     */
    @Override
    public CommonResult<String> sendMobileVerificationCode(final String mobileNo) {

        JSONObject json = new JSONObject();
        json.put("mobileNo", mobileNo);

        return exec(new Callback() {
            @Override
            public void exec() {

                // 通过配置文件获取参数
                PropertiesUtil systemProperties = new PropertiesUtil("system");

                // 获取短信超时参数
                String timeout = systemProperties.getProperty("sms_time_out");

                // 获取redis链接
                try (RedisUtil redisUtil = new RedisUtil(BaseConstant.REDIS)) {

                    String random = Random.getRandom();

                    if (SDKTestSendTemplateSMS.sendMobileVerificationCode(mobileNo, random, timeout)) {

                        redisUtil.set(Integer.valueOf(timeout), "sms-" + mobileNo, random);

                        got(BaseConstant.SUCCESS);

                        return;
                    }

                } catch (Exception e) {
                    logger.error("{} sendMobileVerificationCode redis error:" + e, BaseConstant.LOG_ERR_MSG, e);
                }

                got(BaseConstant.FAIL);

                return;
            }
        }, "userRegisterOrLogin", json.toJSONString());
    }

    /**
     * 根据用户编号获得用户信息
     *
     * @param userNo 用户编号
     * @return
     */
    @Override
    public CommonResult<User> getUserByUserNo(final String userNo) {
        JSONObject json = new JSONObject();
        json.put("userNo", userNo);

        return exec(new Callback() {
            @Override
            public void exec() {
                got(userDao.getUserByUserNo(userNo));

            }
        }, "getUserByUserNo", json.toJSONString());
    }

    @Override
    protected Logger getLogger() {
        return logger;
    }
}
