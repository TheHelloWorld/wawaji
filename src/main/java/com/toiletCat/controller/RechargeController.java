package com.toiletCat.controller;

import com.toiletCat.bean.RechargeResult;
import com.toiletCat.constants.BaseConstant;
import com.toiletCat.service.RechargeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RequestMapping("/toiletCat/recharge")
@Controller
public class RechargeController {

    private static final Logger logger = LoggerFactory.getLogger(RechargeController.class);

    @Autowired
    private RechargeService rechargeService;

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
    public void rechargeResult(String money, String name, String out_trade_no, String pid, String trade_no,
                                 String trade_status, String type, String sign, String sign_type) {

        // 充值结果bean
        RechargeResult rechargeResult = new RechargeResult();

        // 交易金额
        rechargeResult.setMoney(money);

        // 交易商品名称
        rechargeResult.setName(name);

        // 我方订单号
        rechargeResult.setOrderNo(out_trade_no);

        // 我方在支付机构id
        rechargeResult.setpId(pid);

        // 签名
        rechargeResult.setSign(sign);

        // 支付类型(微信/支付宝)
        rechargeResult.setType(type);

        // 签名类型
        rechargeResult.setSignType(sign_type);

        // 交易状态
        rechargeResult.setTradeStatus(trade_status);

        // 支付机构订单号
        rechargeResult.setTradeNo(trade_no);

        // 判断支付机构返回我方订单号是否正确
        if(out_trade_no.length() != 54) {
            logger.warn("rechargeResult return result orderNo error rechargeResult:" + rechargeResult);
            return;
        }

        String userNo = out_trade_no.substring(22);

        logger.info(BaseConstant.LOG_MSG + " rechargeResult userNo:" + userNo + ", rechargeResult:" + rechargeResult);

        rechargeService.getRechargeResultByParam(userNo, rechargeResult);
    }


}
