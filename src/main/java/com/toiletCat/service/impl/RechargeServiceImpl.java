package com.toiletCat.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.toiletCat.bean.Callback;
import com.toiletCat.bean.CommonResult;
import com.toiletCat.bean.RechargeResult;
import com.toiletCat.constants.BaseConstant;
import com.toiletCat.dao.UserDao;
import com.toiletCat.entity.UserRechargeRecord;
import com.toiletCat.enums.MoneyForCoin;
import com.toiletCat.enums.TradeStatus;
import com.toiletCat.service.RechargeService;
import com.toiletCat.service.UserRechargeRecordService;
import com.toiletCat.service.UserSpendRecordService;
import com.toiletCat.utils.HttpClientUtil;
import com.toiletCat.utils.PropertiesUtil;
import com.toiletCat.utils.RechargeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

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

                // 验签
                if(!checkSign(rechargeResult)) {
                    logger.warn("getRechargeResultByParam sign is wrong param:"+ rechargeResult);
                    return;
                }

                // 判断用户编号是否存在
                if(userDao.countUserByUserNo(userNo) == 0) {
                    logger.warn("getRechargeResultByParam userNo not exists param:"+ rechargeResult);
                    return;
                }

                Long money;
                // 转换钱
                try {
                    money = BigDecimal.valueOf(Double.valueOf(rechargeResult.getMoney())).longValue();
                } catch (Exception e) {
                    logger.error("getRechargeResultByParam transMoney err param:" + rechargeResult, e);
                    return;
                }

                Integer coin = MoneyForCoin.getValueMapByKey(rechargeResult.getMoney());

                if(coin == null) {
                    logger.warn("getRechargeResultByParam money is wrong param:"+ rechargeResult);
                    return;
                }

                logger.info("getRechargeResultByParam userNo:" + userNo + ", coin:" + coin);

                // 我方订单号
                String orderNo = rechargeResult.getOrderNo();

                // 根据用户编号和订单号获得我方记录交易金额
                CommonResult<BigDecimal> orderAmount = userRechargeRecordService.getAmountByUserNoAndOrderNo(userNo,
                        orderNo);

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

                // 防止重复请求 获得充值结果
                CommonResult<Integer> commonRechargeResult = userRechargeRecordService.
                        getTradeStatusByOrderNo(userNo, orderNo);

                // 若已有终态则不执行后续操作
                if(commonRechargeResult.getValue() != TradeStatus.INIT.getStatus()) {
                    logger.info("getRechargeResultByParam request duplicate param:"+ rechargeResult);
                    return;
                }

                // 交易结果
                String resultTradeStatus = rechargeResult.getTradeStatus();

                TradeStatus tradeStatus = TradeStatus.SUCCESS;

                // 判断结果是否成功
                if(!BaseConstant.RECHARGE_RESULT_TRADE_STATUS.equals(resultTradeStatus)) {
                    tradeStatus = TradeStatus.FAIL;
                }

                if(tradeStatus == TradeStatus.SUCCESS) {
                    // 添加用户游戏币
                    userDao.updateUserCoinByUserNo(coin, userNo);
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

            }
        }, "getRechargeResultByParam", json.toJSONString());

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
        }, "getRechargeResultByOrderNo", json.toJSONString());
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
        }, "getInitRechargeResultByOrderInfo", json.toJSONString());
    }

    /**
     * 查询获得交易结果
     * @param orderNo 订单号
     * @param amount 交易金额
     * @return
     */
    private void queryRechargeResult(String orderNo, BigDecimal amount) {
        try {

            String userNo = orderNo.substring(22);

            PropertiesUtil propertiesUtil = PropertiesUtil.getInstance("system");

            String pid = propertiesUtil.getProperty("recharge_pid");

            String key = propertiesUtil.getProperty("recharge_key");

            StringBuilder sb = new StringBuilder("");
            sb.append("http://tx87.cn/api.php?act=order&pid=");
            sb.append(pid);
            sb.append("&key=");
            sb.append(key);
            sb.append("&out_trade_no=");
            sb.append(orderNo);

            logger.info("queryRechargeResult request:" + sb.toString());

            String response = HttpClientUtil.sendHttpGet(sb.toString());

            logger.info("queryRechargeResult response:" + response);

            JSONObject json = JSONObject.parseObject(response);

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
                logger.info("queryRechargeResult request duplicate param:"+ json);
                return;
            }

            Integer coin = MoneyForCoin.getValueMapByKey(json.getString("money"));

            if(tradeStatus == TradeStatus.SUCCESS) {
                // 添加用户游戏币
                userDao.updateUserCoinByUserNo(coin, userNo);
            }

            updateRechargeAndSpendResult(orderNo, tradeStatus);

        } catch (Exception e) {
            logger.error("queryRechargeResult error:" + e, e);
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
     * @param rechargeResult 充值返回结果
     * @return
     */
    private Boolean checkSign(RechargeResult rechargeResult) {
        JSONObject json = new JSONObject();

        PropertiesUtil propertiesUtil = PropertiesUtil.getInstance("system");

        json.put("pid", propertiesUtil.getProperty("recharge_pid"));
        json.put("money", rechargeResult.getMoney());
        json.put("out_trade_no", rechargeResult.getOrderNo());
        json.put("name", rechargeResult.getName());
        json.put("trade_no", rechargeResult.getTradeNo());
        json.put("trade_status", rechargeResult.getTradeStatus());
        json.put("type", rechargeResult.getType());

        return RechargeUtil.checkSign(rechargeResult.getSign(), json);

    }

    @Override
    protected Logger getLogger() {
        return logger;
    }
}
