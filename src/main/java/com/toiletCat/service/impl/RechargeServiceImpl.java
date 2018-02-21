package com.toiletCat.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.toiletCat.bean.Callback;
import com.toiletCat.bean.CommonResult;
import com.toiletCat.constants.BaseConstant;
import com.toiletCat.constants.RechargeConstant;
import com.toiletCat.constants.RedisConstant;
import com.toiletCat.dao.UserDao;
import com.toiletCat.entity.MoneyForCoin;
import com.toiletCat.entity.User;
import com.toiletCat.entity.UserRechargeRecord;
import com.toiletCat.entity.UserSpendRecord;
import com.toiletCat.enums.TradeStatus;
import com.toiletCat.enums.TradeType;
import com.toiletCat.service.MoneyForCoinService;
import com.toiletCat.service.RechargeService;
import com.toiletCat.service.UserRechargeRecordService;
import com.toiletCat.service.UserSpendRecordService;
import com.toiletCat.utils.*;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

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

    @Autowired
    private MoneyForCoinService moneyForCoinService;

    /**
     * 用户充值
     * @param userNo 用户编号
     * @param amount 金额
     * @param rechargeType 充值类型
     * @param ip 用户ip
     * @return
     */
    @Override
    public CommonResult<String> userRecharge(final String userNo, final String amount, final String rechargeType,
                                             final String ip) {
        JSONObject json = new JSONObject();
        json.put("userNo", userNo);
        json.put("amount", amount);
        json.put("rechargeType", rechargeType);
        json.put("ip", ip);

        return exec(new Callback() {
            @Override
            public void exec() {

                if(StringUtils.isBlank(userNo) || StringUtils.isBlank(amount) ||
                        StringUtils.isBlank(rechargeType) || StringUtils.isBlank(ip)) {

                    logger.info("userRecharge u");

                }

                logger.info("userRecharge userNo:" + userNo + ", amount:" + amount);

                MoneyForCoin coin = moneyForCoinService.getMoneyForCoinByMoney(amount);

                if(coin == null) {

                    setOtherMsg();

                    got("请重新选择金额");

                    return;
                }

                logger.info("userRecharge userNo:" + userNo + ", amount:" + amount + ", coin:" + coin);

                // 如果有充值限制次数(不为0)
                if(coin.getRechargeLimit() != 0) {

                    // 获得当前用户限充次数
                    Integer userLimitNum = getLimitRechargeByUserNo(userNo, coin);

                    // 如果达到上限
                    if(userLimitNum == coin.getRechargeLimit()) {

                        setOtherMsg();

                        got("每天只能充" + coin.getRechargeLimit() + "次哦");

                        return;
                    }
                }

                User user = userDao.getUserByUserNo(userNo);

                if(user == null) {

                    logger.warn("userRecharge user not exists userNo:" + userNo);

                    setOtherMsg();

                    got("请登录");

                    return;
                }

                // 判断openId是否为空
                if(StringUtils.isBlank(user.getOpenId())) {

                    logger.warn("userRecharge user openId is null:" + userNo);

                    setOtherMsg();

                    got("请登录");

                    return;
                }

                Integer tradeDate = DateUtil.getDate();

                Date tradeTime = new Date(System.currentTimeMillis());

                // 添加用户充值记录
                // 用户充值记录
                UserRechargeRecord userRechargeRecord = new UserRechargeRecord();

                // 金额
                userRechargeRecord.setAmount(BigDecimal.valueOf(Double.valueOf(amount)));

                // 充值类型(支付宝/微信)
                userRechargeRecord.setRechargeType(rechargeType);

                // 用户编号
                userRechargeRecord.setUserNo(userNo);

                // 交易日期
                userRechargeRecord.setTradeDate(tradeDate);

                // 交易时间
                userRechargeRecord.setTradeTime(DateUtil.getFullDateByTime(tradeTime));

                // 交易状态
                userRechargeRecord.setTradeStatus(TradeStatus.INIT.getStatus());

                // 订单号(ToiletCat + 时间戳)
                String orderNo = BaseConstant.TOILER_CAT + tradeTime.getTime();

                // 订单号
                userRechargeRecord.setOrderNo(orderNo);

                CommonResult addRechargeResult = userRechargeRecordService.addUserRechargeRecord(userRechargeRecord);

                if(!addRechargeResult.success()) {

                    respondSysError();

                    return;
                }

                // 获得请求url并返回
                got(RechargeUtil.getWxPayRequestInfo(orderNo, String.valueOf(amount), user.getOpenId(), ip));

            }
        }, true, "userRecharge", json);
    }

    /**
     * 充值返回结果
     *
     * @param rechargeResultMap 充值结果Map
     */
    @Override
    public CommonResult<String> getRechargeResultByParam(final Map<String, String> rechargeResultMap) {

        JSONObject json = new JSONObject();
        json.put("rechargeResultMap", rechargeResultMap);

        return exec(new Callback() {
            @Override
            public void exec() {

                // 判断返回是否成功
                if(!RechargeConstant.SUCCESS_RETURN_CODE.equals(rechargeResultMap.get("return_code"))) {

                    setOtherMsg();

                    got(RechargeConstant.FAIL_RETURN_MSG);
                    
                    return;
                }

                // 验签
                if(!checkSign(rechargeResultMap)) {
                    
                    logger.warn("getRechargeResultByParam sign is wrong param:" + rechargeResultMap);
                    
                    setOtherMsg();
                    
                    got("签名失败");
                    
                    return;
                }

                if(!WeChatUtil.checkAppIdAndMerchantId(rechargeResultMap.get("appid"),
                        rechargeResultMap.get("mch_id"))) {

                    logger.warn("getRechargeResultByParam appId or merchantId is wrong param:" + rechargeResultMap);

                    setOtherMsg();

                    got("appid或mch_id错误");

                    return;
                }

                String openId = rechargeResultMap.get("openid");

                if(userDao.countUserByOpenId(openId) == 0) {

                    logger.warn("getRechargeResultByParam openId not exists param:" + rechargeResultMap);

                    setOtherMsg();

                    got("openId错误,用户不存在");

                    return;
                }

                // 根据openId获得用户编号
                String userNo = userDao.getUserNoByOpenId(openId);

                // 我方订单号
                String orderNo = rechargeResultMap.get("out_trade_no");

                String lockKey = RedisConstant.RECHARGE_RESULT_LOCK.replace(RedisConstant.PLACEHOLDER, orderNo);

                // 获取锁
                if(!getLockByKey(lockKey)) {

                    logger.info("getRechargeResultByParam orderNo:" + orderNo + " is running");

                    return;
                }

                // 防止重复请求 获得充值结果
                CommonResult<Integer> commonRechargeResult = userRechargeRecordService.
                        getTradeStatusByOrderNo(userNo, orderNo);

                // 若已有终态则不执行后续操作
                if(commonRechargeResult.getValue() != TradeStatus.INIT.getStatus()) {

                    logger.info("getRechargeResultByParam request duplicate param:" +  rechargeResultMap);

                    return;
                }

                // 获得订单金额(单位:分)
                String amount = rechargeResultMap.get("total_fee");

                CommonResult<String> result = updateRechargeResult(orderNo, userNo, amount,
                        rechargeResultMap.get("result_code"));

                // 释放锁
                relaseLockByKey(lockKey);

                if(!result.success()) {

                    setOtherMsg();

                    got(result.getValue());

                    return;
                }

            }
        }, true, "getRechargeResultByParam", json);
    }

    /**
     * 修改充值结果
     * @param orderNo 我方订单号
     * @param userNo 用户编号
     * @param amount 金额
     * @param resultCode 充值结果
     * @return
     */
    private CommonResult<String> updateRechargeResult(final String orderNo, final String userNo, final String amount,
                                                      final String resultCode) {

        JSONObject json = new JSONObject();
        json.put("orderNo", orderNo);
        json.put("userNo", userNo);
        json.put("amount", amount);
        json.put("resultCode", resultCode);

        return exec(new Callback() {
            @Override
            public void exec() {

                // 如果金额为空
                if(StringUtils.isBlank(amount)) {

                    logger.info("updateRechargeResult resultCode: " + resultCode);

                    // 根据用户编号和订单号获得我方记录交易金额
                    CommonResult<BigDecimal> orderAmount = userRechargeRecordService.getAmountByUserNoAndOrderNo(userNo,
                            orderNo);

                    DecimalFormat df = new DecimalFormat("######0.00");

                    MoneyForCoin coin = moneyForCoinService.getMoneyForCoinByMoney(
                            df.format(orderAmount.getValue().doubleValue()));

                    // 修改充值记录交易状态
                    CommonResult updateRechargeResult = userRechargeRecordService.
                            updateTradeStatusByOrderNo(orderNo, TradeStatus.CANCEL.getStatus());

                    if(!updateRechargeResult.success()) {

                        logger.warn("updateRechargeResult recharge updateRechargeResult error orderNo:"
                                + orderNo);

                        setOtherMsg();

                        got(RechargeConstant.FAIL_RETURN_MSG);

                        return;
                    }

                    got(String.valueOf(TradeStatus.CANCEL.getStatus()));

                    return;
                }

                Double money;
                // 转换钱
                try {
                    money = Double.valueOf(amount) / 100;

                } catch (Exception e) {

                    logger.error("updateRechargeResult transMoney err param:" + amount, e);

                    setOtherMsg();

                    got(RechargeConstant.FAIL_RETURN_MSG);

                    return;
                }

                DecimalFormat df = new DecimalFormat("######0.00");

                MoneyForCoin coin = moneyForCoinService.getMoneyForCoinByMoney(df.format(money));

                if(coin == null) {

                    logger.warn("updateRechargeResult money is wrong param:" + money);

                    setOtherMsg();

                    got("交易金额错误");

                    return;
                }

                logger.info("updateRechargeResult userNo:" + userNo + ", coin:" + coin);

                // 根据用户编号和订单号获得我方记录交易金额
                CommonResult<BigDecimal> orderAmount = userRechargeRecordService.getAmountByUserNoAndOrderNo(userNo,
                        orderNo);

                if(!orderAmount.success()) {

                    logger.error("updateRechargeResult countNumByUserNoAndOrderNo error userNo:"+ userNo +
                            ", orderNo:" + orderNo);

                    setOtherMsg();

                    got(RechargeConstant.FAIL_RETURN_MSG);

                    return;
                }

                if(orderAmount.getValue() == null) {

                    logger.warn("updateRechargeResult order not exists param orderNo:"+ orderNo);

                    setOtherMsg();

                    got(RechargeConstant.FAIL_RETURN_MSG);

                    return;
                }

                TradeStatus tradeStatus = TradeStatus.SUCCESS;

                // 判断结果是否成功
                if(!RechargeConstant.SUCCESS_RESULT_CODE.equals(resultCode)) {

                    tradeStatus = TradeStatus.FAIL;
                }

                if(tradeStatus == TradeStatus.SUCCESS) {

                    // 判断返回金额是否正确
                    if(orderAmount.getValue().doubleValue() != money) {

                        logger.warn("updateRechargeResult return money not match our amount:" + orderAmount.getValue()
                                + " param money:"+ money);

                        setOtherMsg();

                        got("交易金额错误");

                        return;
                    }

                    // 添加用户游戏币
                    userDao.updateUserCoinByUserNo(coin.getCoin(), userNo);

                    // 设置限充
                    setLimitRechargeByUserNo(userNo, coin);

                    // 将用户首充标志位置为非首充
                    setUserNotFirstRechargeFlag(userNo);
                }

                // 修改充值记录交易状态
                CommonResult updateRechargeResult = userRechargeRecordService.
                        updateTradeStatusByOrderNo(orderNo, tradeStatus.getStatus());

                if(!updateRechargeResult.success()) {

                    logger.warn("updateRechargeResult recharge updateRechargeResult error orderNo:"
                            + orderNo);

                    setOtherMsg();

                    got(RechargeConstant.FAIL_RETURN_MSG);

                    return;
                }

                Date tradeTime = new Date();

                // 添加用户消费记录
                // 用户消费记录
                UserSpendRecord userSpendRecord = new UserSpendRecord();

                //  消费日期
                userSpendRecord.setTradeDate(DateUtil.getDate());

                // 订单号
                userSpendRecord.setOrderNo(orderNo);

                // 消费时间
                userSpendRecord.setTradeTime(DateUtil.getFullDateByTime(tradeTime));

                // 消费类型(充值)
                userSpendRecord.setTradeType(TradeType.RECHARGE.getType());

                Integer rechargeCoin = getCoinByMoneyForCoin(userNo, coin);

                // 消费游戏币
                userSpendRecord.setCoin(rechargeCoin);

                // 用户编号
                userSpendRecord.setUserNo(userNo);

                // 消费状态
                userSpendRecord.setTradeStatus(tradeStatus.getStatus());

                CommonResult addSpendResult = userSpendRecordService.addUserSpendRecord(userSpendRecord);

                if(!addSpendResult.success()) {

                    logger.warn("updateRechargeResult spend addUserSpendRecord error:" + userSpendRecord);

                    setOtherMsg();

                    got(RechargeConstant.FAIL_RETURN_MSG);

                }

                got(String.valueOf(tradeStatus.getStatus()));

            }
        }, false, "updateRechargeResult", json);
    }

    /**
     * 根据订单号获得充值结果
     * @param userNo 用户编号
     * @param orderNo 订单号
     * @return
     */
    @Override
    public CommonResult<String> getRechargeResultByOrderNo(final String userNo, final String orderNo) {
        JSONObject json = new JSONObject();
        json.put("orderNo", orderNo);
        json.put("userNo", userNo);

        return exec(new Callback() {
            @Override
            public void exec() {

                JSONObject returnJSON = new JSONObject();

                // 判断用户编号是否存在
                if(userDao.countUserByUserNo(userNo) == 0) {

                    logger.warn("getRechargeResultByOrderNo userNo not exists userNo:"+ userNo);

                    returnJSON.put("result", "fail");

                    got(returnJSON.toJSONString());

                    return;
                }

                String lockKey = RedisConstant.RECHARGE_QUERY_LOCK.replace(RedisConstant.PLACEHOLDER, orderNo);

                if(!getLockByKey(lockKey)) {

                    logger.info("getRechargeResultByOrderNo orderNo:" + orderNo + " is running");

                    returnJSON.put("result", "wait");

                    got(returnJSON.toJSONString());

                    return;
                }

                // 获得充值结果
                CommonResult<Integer> rechargeResult = userRechargeRecordService.
                        getTradeStatusByOrderNo(userNo, orderNo);

                if(rechargeResult.getValue() == TradeStatus.FAIL.getStatus()) {

                    returnJSON.put("result", "fail");

                    got(returnJSON.toJSONString());

                    // 释放锁
                    relaseLockByKey(lockKey);

                    return;
                }

                if(rechargeResult.getValue() == TradeStatus.SUCCESS.getStatus()) {

                    Integer userCoin = userDao.getUserCoinByUserNo(userNo);

                    returnJSON.put("result", "success");

                    returnJSON.put("userCoin", userCoin);

                    got(returnJSON.toJSONString());

                    // 释放锁
                    relaseLockByKey(lockKey);

                    return;
                }

                // 查询交易结果
                Map<String, String> responseMap = RechargeUtil.getWxPayQueryRequestInfo(orderNo);

                CommonResult<String> result = updateRechargeResult(orderNo, userNo, responseMap.get("total_fee"),
                        responseMap.get("trade_state"));

                if(Integer.valueOf(result.getValue()) == TradeStatus.SUCCESS.getStatus()) {

                    Integer userCoin = userDao.getUserCoinByUserNo(userNo);

                    returnJSON.put("result", "success");

                    returnJSON.put("userCoin", userCoin);

                    got(returnJSON.toJSONString());

                    // 释放锁
                    relaseLockByKey(lockKey);

                    return;
                }

                if(Integer.valueOf(result.getValue()) == TradeStatus.FAIL.getStatus()) {

                    returnJSON.put("result", "fail");

                    got(returnJSON.toJSONString());

                    // 释放锁
                    relaseLockByKey(lockKey);

                    return;
                }

                if(Integer.valueOf(result.getValue()) == TradeStatus.CANCEL.getStatus()) {

                    returnJSON.put("result", "cancel");

                    got(returnJSON.toJSONString());

                    // 释放锁
                    relaseLockByKey(lockKey);

                    return;
                }

                // 释放锁
                relaseLockByKey(lockKey);

            }
        }, true, "getRechargeResultByOrderNo", json);
    }

    /**
     * 根据key获取锁
     * @param key
     * @return
     */
    private Boolean getLockByKey(String key) {
        // 锁机制
        try(RedisUtil redisUtil = new RedisUtil(RedisConstant.REDIS)) {

            // 尝试获取锁
            if(redisUtil.setnx(key, "1") == 0L) {

                return false;
            }

            return true;

        } catch (Exception e) {

            logger.error("getLockByKey redis get lock error: " + e.getMessage(), e);

            return false;
        }
    }

    /**
     * 释放锁
     * @param key key
     */
    private void relaseLockByKey(String key) {

        // 锁机制
        try(RedisUtil redisUtil = new RedisUtil(RedisConstant.REDIS)) {

            // 释放锁
            redisUtil.del(key);

        } catch (Exception e) {

            logger.error("relaseLockByKey redis relase lock error: " + e.getMessage(), e);

            return;
        }
    }

    /**
     * 根据订单号取消充值操作
     * @param userNo 用户编号
     * @param orderNo 订单号
     * @return
     */
    @Override
    public CommonResult cancelRechargeByOrderNo(final String userNo, final String orderNo) {

        JSONObject json = new JSONObject();
        json.put("userNo", userNo);
        json.put("orderNo", orderNo);

        return exec(new Callback() {
            @Override
            public void exec() {

                JSONObject returnJSON = new JSONObject();

                // 判断用户编号是否存在
                if(userDao.countUserByUserNo(userNo) == 0) {

                    logger.warn("cancelRechargeByOrderNo userNo not exists userNo:"+ userNo);

                    return;
                }

                // 将订单状态置为取消
                CommonResult result = userRechargeRecordService.updateTradeStatusByOrderNo(orderNo,
                        TradeStatus.CANCEL.getStatus());

                if(!result.success()) {

                    respondSysError();

                    return;
                }

            }
        }, true, "cancelRechargeByOrderNo", json);
    }

    /**
     * 获得所有状态不为终态的交易的结果
     * @param userNo 用户编号
     * @return
     */
    @Override
    public CommonResult getInitRechargeResultByOrderInfo(final String userNo) {

        JSONObject json = new JSONObject();
        json.put("userNo", userNo);

        return exec(new Callback() {
            @Override
            public void exec() {

                JSONObject returnJSON = new JSONObject();

                // 判断用户编号是否存在
                if(userDao.countUserByUserNo(userNo) == 0) {
                    logger.warn("getInitRechargeResultByOrderInfo userNo not exists userNo:"+ userNo);
                    return;
                }

                // 获得所有当前用户非终态订单
                CommonResult<List<UserRechargeRecord>> result = userRechargeRecordService.
                        getAllInitRecordByUserNo(userNo);

                List<UserRechargeRecord> list = result.getValue();

                if(list == null || list.size() == 0) {
                    logger.info("getInitRechargeResultByOrderInfo userNo:" + userNo + " no init order");
                    return;
                }

                for(UserRechargeRecord userRechargeRecord : list) {
                    // 查询交易结果
                    queryRechargeResult(userRechargeRecord.getOrderNo(), userNo);
                }

            }
        }, true, "getInitRechargeResultByOrderInfo", json);
    }

    /**
     * 将用户首充标志位置为"非首充"
     * @param userNo 用户编号
     */
    private void setUserNotFirstRechargeFlag(String userNo) {

        logger.info("setUserNotFirstRechargeFlag start userNo: " + userNo);

        try(RedisUtil redisUtil = new RedisUtil(RedisConstant.REDIS)) {
            String key = RedisConstant.FIRST_RECHARGE_FLAG.replace(RedisConstant.PLACEHOLDER, userNo);

            String userFirstFlag = redisUtil.get(key);

            if(userFirstFlag == null || RedisConstant.IS_FIRST.equals(userFirstFlag)) {
                redisUtil.set(key, RedisConstant.IS_NOT_FIRST);
            }

        } catch (Exception e) {
            logger.error("setUserNotFirstRechargeFlag error: " + e, e);
        }
    }

    /**
     * 查询获得交易结果
     * @param orderNo 订单号
     * @param userNo 用户编号
     * @return
     */
    private void queryRechargeResult(String orderNo, String userNo) {

        logger.info("queryRechargeResult start orderNo: " + orderNo + ", userNo:" + userNo);

        try {

            Map<String, String> responseMap = RechargeUtil.getWxPayQueryRequestInfo(orderNo);

            CommonResult<String> result = updateRechargeResult(orderNo, userNo, responseMap.get("total_fee"),
                    responseMap.get("trade_state"));

        } catch (Exception e) {
            logger.error("queryRechargeResult error:" + e, e);
        }
    }


    /**
     * 获得应充游戏币数
     * @param userNo 用户编号
     * @param moneyForCoin 对应关系bean
     * @return
     */
    @Override
    public Integer getCoinByMoneyForCoin(String userNo, MoneyForCoin moneyForCoin) {

        logger.info("getCoinByMoneyForCoin start userNo: " + userNo + ", moneyForCoin: " + moneyForCoin);

        Integer rechargeCoin = moneyForCoin.getCoin();

        // 若非首充活动
        if(moneyForCoin.getFirstFlag() == 0) {
            return rechargeCoin;
        }

        try(RedisUtil redisUtil = new RedisUtil(RedisConstant.REDIS)) {

            String key = RedisConstant.FIRST_RECHARGE_FLAG.replace(RedisConstant.PLACEHOLDER, userNo);

            String userFirstRecharge = redisUtil.get(key);

            if(userFirstRecharge == null || RedisConstant.IS_FIRST.equals(userFirstRecharge)) {

                // 获得用户之前所有成功充值记录数量
                CommonResult<Integer> rechargeCount = userRechargeRecordService.
                            countUserRechargeRecordByUserNoAndTradeStatus(userNo, TradeStatus.SUCCESS.getStatus());

                if(rechargeCount.getValue() == 0) {
                    // 添加赠送的游戏币数
                    rechargeCoin += moneyForCoin.getGiveCoin();
                } else {
                    redisUtil.set(key, RedisConstant.IS_NOT_FIRST);
                }
            }

        } catch(Exception e) {
            logger.error("getCoinByMoneyForCoin redis error:" + e, e);
        }

        return rechargeCoin;
    }

    /**
     * 获得用户首充标志位
     * @param userNo 用户编号
     * @return
     */
    @Override
    public String getFirstFlag(String userNo) {

        logger.info("getFirstFlag start userNo: " + userNo);

        try(RedisUtil redisUtil = new RedisUtil(RedisConstant.REDIS)) {

            String key = RedisConstant.FIRST_RECHARGE_FLAG.replace(RedisConstant.PLACEHOLDER, userNo);

            String userFirstRecharge = redisUtil.get(key);

            if(userFirstRecharge == null || RedisConstant.IS_FIRST.equals(userFirstRecharge)) {

                // 获得用户之前所有成功充值记录数量
                CommonResult<Integer> rechargeCount = userRechargeRecordService.
                        countUserRechargeRecordByUserNoAndTradeStatus(userNo, TradeStatus.SUCCESS.getStatus());

                if(rechargeCount.getValue() == 0) {
                    userFirstRecharge = RedisConstant.IS_FIRST;
                } else {
                    userFirstRecharge = RedisConstant.IS_NOT_FIRST;
                }

                redisUtil.set(key, userFirstRecharge);
            }

            return userFirstRecharge;

        } catch(Exception e) {
            logger.error("getFirstFlag redis error:" + e, e);
        }

        return null;
    }

    /**
     * 获得用户限充当前次数
     * @param userNo 用户编号
     * @param moneyForCoin 对应关系bean
     * @return
     */
    @Override
    public Integer getLimitRechargeByUserNo(String userNo, MoneyForCoin moneyForCoin) {

        logger.info("getLimitRechargeByUserNo start userNo: " + userNo + ", moneyForCoin: " + moneyForCoin);

        if(moneyForCoin.getRechargeLimit() == 0) {

            return 0;
        }

        Integer userLimitNum = 0;

        try(RedisUtil redisUtil = new RedisUtil(RedisConstant.REDIS)) {

            String key = RedisConstant.RECHARGE_LIMIT_NUM_BY_USER.replace(RedisConstant.PLACEHOLDER, userNo);

            String nowNum = redisUtil.get(key);

            if(nowNum == null) {
                nowNum = "0";
            }

            return Integer.valueOf(nowNum);

        } catch (Exception e) {
            logger.error("getLimitRechargeByUserNo redis error: " + e, e);
        }

        return 0;
    }

    /**
     * 设置用户限充当前次数
     * @param userNo 用户编号
     * @param moneyForCoin 对应关系bean
     * @return
     */
    private void setLimitRechargeByUserNo(String userNo, MoneyForCoin moneyForCoin) {
        JSONObject json = new JSONObject();
        json.put("userNo", userNo);
        json.put("moneyForCoin", moneyForCoin);

        if(moneyForCoin.getRechargeLimit() == 0) {
            return;
        }

        try(RedisUtil redisUtil = new RedisUtil(RedisConstant.REDIS)) {

            String key = RedisConstant.RECHARGE_LIMIT_NUM_BY_USER.replace(RedisConstant.PLACEHOLDER, userNo);

            String nowNum = redisUtil.get(key);

            if(nowNum == null) {
                nowNum = "0";
            }

            // 如果达到上限
            if(Integer.valueOf(nowNum) >= moneyForCoin.getRechargeLimit()) {
                logger.info("setLimitRechargeByUserNo today is up the limit userNo:" + userNo + ", limitNum:" + nowNum);
                return;
            }

            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DAY_OF_YEAR, 1);
            cal.set(Calendar.HOUR_OF_DAY, 0);
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.MILLISECOND, 0);

            Integer second = (int)(cal.getTimeInMillis() - System.currentTimeMillis()) / 1000;

            // 设置超时时间为当前时间到第二天0点的时间并累加数量
            redisUtil.incr(second, key);
        } catch (Exception e) {
            logger.error("setLimitRechargeByUserNo redis error:" + e, e);
        }
    }

    /**
     * 修改充值结果
     * @param orderNo
     * @param tradeStatus
     */
    private void updateRechargeAndSpendResult(String orderNo, TradeStatus tradeStatus) {
        // 修改充值记录交易状态
        CommonResult updateRechargeResult = userRechargeRecordService.
                updateTradeStatusByOrderNo(orderNo, tradeStatus.getStatus());

        if(!updateRechargeResult.success()) {
            logger.warn("queryRechargeResult recharge updateTradeStatusByOrderNo error orderNo:" + orderNo +
                    ", tradeStatus" + tradeStatus.name());
        }

        // 修改用户游戏币记录交易状态
        CommonResult updateSpendResult = userSpendRecordService.
                updateTradeStatusByOrderNo(orderNo, tradeStatus.getStatus());

        if(!updateSpendResult.success()) {
            logger.warn("queryRechargeResult spend updateTradeStatusByOrderNo error orderNo:" + orderNo +
                    ", tradeStatus" + tradeStatus.name());
        }
    }

    /**
     * 验签
     * @param rechargeResultMap 充值返回结果
     * @return
     */
    private Boolean checkSign(Map<String, String> rechargeResultMap) {
        
        JSONObject json = new JSONObject();

        PropertiesUtil propertiesUtil = PropertiesUtil.getInstance("system");

        for(String key : rechargeResultMap.keySet()) {

            if(RechargeConstant.SIGN.equals(key)) {
                
                continue;
            }

            if(rechargeResultMap.get(key)  == null) {

                continue;
            }

            json.put(key, rechargeResultMap.get(key));

        }

        return RechargeUtil.checkSign(rechargeResultMap.get(RechargeConstant.SIGN), json);

    }

    @Override
    protected Logger getLogger() {
        return logger;
    }
}
