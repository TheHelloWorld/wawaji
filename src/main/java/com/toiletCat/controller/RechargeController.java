package com.toiletCat.controller;

import com.toiletCat.constants.BaseConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping("/toiletCat/recharge")
@Controller
public class RechargeController {

    private static final Logger logger = LoggerFactory.getLogger(RechargeController.class);

    /**
     * 用户自动登陆
     *
     * @return
     */
    /**
     * 充值回调接口
     * @param money 商品金额
     * @param name 商品名称
     * @param out_trade_no (我方)商户订单号
     * @param pid 商户ID
     * @param trade_no (第三方)易支付订单号
     * @param trade_status 支付状态
     * @param type 支付方式
     * @param sign 签名字符串
     * @param sign_type 签名类型
     * @return
     */
    @RequestMapping(value = "/rechargeResult", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
    public String rechargeResult(String money, String name, String out_trade_no, String pid, String trade_no,
                                 String trade_status, String type, String sign, String sign_type) {

        String userNo = out_trade_no.substring(22);

        logger.info(BaseConstant.LOG_MSG + " rechargeResult userNo:" + userNo + ", orderNo:" + out_trade_no);

        return null;
//        // 添加用户游戏币
//        userDao.updateUserCoinByUserNo(coin, userNo);
//
//        // 获得用户当前游戏币
//        Integer userCoin = userDao.getUserCoinByUserNo(userNo);
//
//        // 添加用户消费记录
//        // 用户消费记录
//        UserSpendRecord userSpendRecord = new UserSpendRecord();
//        //  消费日期
//        userSpendRecord.setTradeDate(tradeDate);
//        // 消费时间
//        userSpendRecord.setTradeTime(tradeTime);
//        // 消费类型(充值)
//        userSpendRecord.setTradeType(TradeType.RECHARGE.getType());
//        // 消费游戏币
//        userSpendRecord.setCoin(coin);
//        // 用户编号
//        userSpendRecord.setUserNo(userNo);
//        // 消费状态
//        userSpendRecord.setTradeStatus(TradeStatus.SUCCESS.getStatus());
//
//        userSpendRecordService.addUserSpendRecord(userSpendRecord);
//
//        JSONObject returnJSON = new JSONObject();
//
//        returnJSON.put("recharge_result", BaseConstant.SUCCESS);
//
//        returnJSON.put("recharge_coin", userCoin);
//
//        got(returnJSON.toJSONString());
    }
}
