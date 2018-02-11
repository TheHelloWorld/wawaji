package com.toiletCat.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.toiletCat.bean.Callback;
import com.toiletCat.bean.CommonResult;
import com.toiletCat.bean.RechargeResult;
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

                Date tradeTime = new Date();

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
                userRechargeRecord.setTradeTime(tradeTime);

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
                got(RechargeUtil.getWxPayRequestInfo(orderNo, String.valueOf(amount), user.getOpenId(), ""));

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

                String openId = rechargeResultMap.get("openid");

                if(userDao.countUserByOpenId(openId) == 0) {

                    logger.warn("getRechargeResultByParam openId not exists param:" + rechargeResultMap);

                    setOtherMsg();

                    got("openId错误,用户不存在");

                    return;
                }

                // 根据openId获得用户编号
                String userNo = userDao.getUserNoByOpenId(openId);

                // 获得订单金额(单位:分)
                String amount = rechargeResultMap.get("total_fee");

                Double money;
                // 转换钱
                try {
                    money = Double.valueOf(amount) / 100;

                } catch (Exception e) {

                    logger.error("getRechargeResultByParam transMoney err param:" + rechargeResultMap, e);

                    setOtherMsg();

                    got(RechargeConstant.FAIL_RETURN_MSG);

                    return;
                }

                DecimalFormat df = new DecimalFormat("######0.00");

                MoneyForCoin coin = moneyForCoinService.getMoneyForCoinByMoney(df.format(money));

                if(coin == null) {

                    logger.warn("getRechargeResultByParam money is wrong param:" + rechargeResultMap);

                    setOtherMsg();

                    got("交易金额错误");

                    return;
                }

                logger.info("getRechargeResultByParam openId:" + openId + ", coin:" + coin);

                // 我方订单号
                String orderNo = rechargeResultMap.get("out_trade_no");

                // 根据用户编号和订单号获得我方记录交易金额
                CommonResult<BigDecimal> orderAmount = userRechargeRecordService.getAmountByUserNoAndOrderNo(userNo,
                        orderNo);

                if(!orderAmount.success()) {

                    logger.error("getRechargeResultByParam countNumByUserNoAndOrderNo error param:"+ rechargeResultMap);

                    setOtherMsg();

                    got(RechargeConstant.FAIL_RETURN_MSG);

                    return;
                }

                if(orderAmount.getValue() == null) {

                    logger.warn("getRechargeResultByParam order not exists param:"+ rechargeResultMap);

                    setOtherMsg();

                    got(RechargeConstant.FAIL_RETURN_MSG);

                    return;
                }

                // 判断返回金额是否正确
                if(orderAmount.getValue().doubleValue() != money) {

                    logger.warn("getRechargeResultByParam return money not match our amount:" + orderAmount.getValue()
                            + " param:"+ rechargeResultMap);

                    setOtherMsg();

                    got("交易金额错误");

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

                // 交易结果
                String resultTradeStatus = rechargeResultMap.get("result_code");

                TradeStatus tradeStatus = TradeStatus.SUCCESS;

                // 判断结果是否成功
                if(!RechargeConstant.FAIL_RESULT_CODE.equals(resultTradeStatus)) {

                    tradeStatus = TradeStatus.FAIL;
                }

                if(tradeStatus == TradeStatus.SUCCESS) {
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

                    logger.warn("getRechargeResultByParam recharge updateTradeStatusByOrderNo error:" + rechargeResultMap);

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
                userSpendRecord.setTradeTime(tradeTime);

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

                    logger.warn("getRechargeResultByParam spend updateTradeStatusByOrderNo error:" + rechargeResultMap);

                    setOtherMsg();

                    got(RechargeConstant.FAIL_RETURN_MSG);

                }

            }
        }, true, "getRechargeResultByParam", json);

    }

    /**
     * 根据订单号获得充值结果
     * @param orderNo 订单号
     * @return
     */
    @Override
    public CommonResult<String> getRechargeResultByOrderNo(final String orderNo) {
        JSONObject json = new JSONObject();
        json.put("orderNo", orderNo);

        return exec(new Callback() {
            @Override
            public void exec() {

                JSONObject returnJSON = new JSONObject();

                // 判断支付机构返回我方订单号是否正确
                if(orderNo.length() != 54) {
                    logger.warn("getRechargeResultByOrderNo orderNo is wrong orderNo:" + orderNo);
                    returnJSON.put("result", "fail");
                    got(returnJSON.toJSONString());
                    return;
                }

                String userNo = orderNo.substring(22);

                // 判断用户编号是否存在
                if(userDao.countUserByUserNo(userNo) == 0) {
                    logger.warn("getRechargeResultByOrderNo userNo not exists userNo:"+ userNo);
                    returnJSON.put("result", "fail");
                    got(returnJSON.toJSONString());
                    return;
                }

                // 根据用户编号和订单号获得我方记录交易金额
                CommonResult<BigDecimal> orderAmount = userRechargeRecordService.getAmountByUserNoAndOrderNo(userNo,
                        orderNo);


                if(orderAmount.getValue() == null) {
                    logger.warn("getRechargeResultByOrderNo order not exists userNo:"+ userNo + ",  orderNo:"
                            + orderNo);
                    returnJSON.put("result", "fail");
                    got(returnJSON.toJSONString());
                    return;
                }

                // 查询交易结果
                queryRechargeResult(orderNo, orderAmount.getValue());

                // 获得充值结果
                CommonResult<Integer> rechargeResult = userRechargeRecordService.
                        getTradeStatusByOrderNo(userNo, orderNo);

                // 若还为初始化状态表示还为收到结果
                if(rechargeResult.getValue() == TradeStatus.INIT.getStatus()) {
                    returnJSON.put("result", "wait");
                    got(returnJSON.toJSONString());
                    return;
                }

                if(rechargeResult.getValue() == TradeStatus.FAIL.getStatus()) {
                    returnJSON.put("result", "fail");
                    got(returnJSON.toJSONString());
                    return;
                }

                Integer userCoin = userDao.getUserCoinByUserNo(userNo);

                returnJSON.put("result", "success");
                returnJSON.put("userCoin", userCoin);
                got(returnJSON.toJSONString());

            }
        }, true, "getRechargeResultByOrderNo", json);
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
                    queryRechargeResult(userRechargeRecord.getOrderNo(), userRechargeRecord.getAmount());
                }

            }
        }, true, "getInitRechargeResultByOrderInfo", json);
    }

    /**
     * 将用户首充标志位置为 非首充
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
     * @param amount 交易金额
     * @return
     */
    private void queryRechargeResult(String orderNo, BigDecimal amount) {

        logger.info("queryRechargeResult start orderNo: " + orderNo + ", amount: " + amount.doubleValue());

        try {

            String userNo = orderNo.substring(22);

            PropertiesUtil propertiesUtil = PropertiesUtil.getInstance("system");

            String queryUrl = propertiesUtil.getProperty("recharge_query_url");

            String pid = propertiesUtil.getProperty("recharge_pid");

            String key = propertiesUtil.getProperty("recharge_key");

            StringBuilder sb = new StringBuilder("");
            sb.append(queryUrl);
            sb.append(pid);
            sb.append("&key=");
            sb.append(key);
            sb.append("&out_trade_no=");
            sb.append(orderNo);

            logger.info("queryRechargeResult request:" + sb.toString());

            String response = HttpClientUtil.sendHttpGet(sb.toString());

//            String response = HttpClientUtil.httpsRequest(sb.toString(), "GET", null);

            logger.info("queryRechargeResult response:" + response);

            JSONObject json = JSONObject.parseObject(response);

            MoneyForCoin myCoin = moneyForCoinService.getMoneyForCoinByMoney(String.valueOf(amount.doubleValue()));

            // 若返回code不为1
            if(!"1".equals(json.getString("code"))) {
                // 将充值结果置为失败
                updateRechargeAndSpendResult(orderNo, TradeStatus.FAIL);

                return;
            }

            if(!orderNo.equals(json.getString("out_trade_no"))) {
                logger.warn("queryRechargeResult wrong orderNo:" + orderNo + ", response:" + json);
                return;
            }

            if(Double.valueOf(json.getString("money")) != amount.doubleValue()) {
                logger.warn("queryRechargeResult wrong amount:" + amount + ", response:" + json);
                return;
            }

            TradeStatus tradeStatus = TradeStatus.SUCCESS;

            if(!"1".equals(json.getString("status"))) {
                tradeStatus = TradeStatus.FAIL;

            }

            // 防止重复请求 获得充值结果
            CommonResult<Integer> commonRechargeResult = userRechargeRecordService.
                    getTradeStatusByOrderNo(userNo, orderNo);

            // 若已有终态则不执行后续操作
            if(commonRechargeResult.getValue() != TradeStatus.INIT.getStatus()) {
                logger.info("queryRechargeResult request duplicate param: "+ json);
                return;
            }

            MoneyForCoin coin = moneyForCoinService.getMoneyForCoinByMoney(json.getString("money"));

            if(tradeStatus == TradeStatus.SUCCESS) {

                Integer rechargeCoin = getCoinByMoneyForCoin(userNo, coin);

                // 添加用户游戏币
                userDao.updateUserCoinByUserNo(rechargeCoin, userNo);

                // 如果有充值限制次数
                setLimitRechargeByUserNo(userNo, myCoin);

                // 将用户首充标志位置为 非首充
                setUserNotFirstRechargeFlag(userNo);
            }

            updateRechargeAndSpendResult(orderNo, tradeStatus);

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
