package com.toiletCat.controller;

import com.toiletCat.bean.CommonResult;
import com.toiletCat.constants.RechargeConstant;
import com.toiletCat.service.RechargeService;
import com.toiletCat.utils.CommonHandle;
import com.toiletCat.utils.JSONUtil;
import com.toiletCat.utils.WeChatUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.util.Map;

@RequestMapping("/toiletCat/api/recharge")
@Controller
public class RechargeController {

    private static final Logger logger = LoggerFactory.getLogger(RechargeController.class);

    @Autowired
    private RechargeService rechargeService;

    /**
     * 用户充值
     * @param userNo 用户编号
     * @param amount 金额
     * @param rechargeType 充值类型
     * @return
     */
    @RequestMapping(value = "/userRecharge", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String userRecharge(HttpServletRequest request,String userNo, String amount, String rechargeType) {

        String ip = CommonHandle.getIpAddr(request);

        CommonResult<String> result = rechargeService.userRecharge(userNo, amount, rechargeType, ip);

        return JSONUtil.getReturnBeanString(result);
    }

    /**
     * 充值回调接口
     * @return
     */
    @RequestMapping(value = "/rechargeResult", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
    public String rechargeResult(HttpServletRequest request) {

        try {

            BufferedReader reader;

            reader = request.getReader();

            String line;

            String xmlString;

            StringBuffer inputString = new StringBuffer();

            while ((line = reader.readLine()) != null) {

                inputString.append(line);
            }

            xmlString = inputString.toString();

            // 关闭reader
            request.getReader().close();

            Map<String, String> requestMap = WeChatUtil.xmlToMap(xmlString);

            logger.info("rechargeResult requestMap:" + requestMap);

            return returnXML(RechargeConstant.SUCCESS_RETURN_CODE, RechargeConstant.SUCCESS_RETURN_MSG);

//            CommonResult<String> result = rechargeService.getRechargeResultByParam(requestMap);
//
//            if(result.success()) {
//
//                return returnXML(RechargeConstant.SUCCESS_RETURN_CODE, RechargeConstant.SUCCESS_RETURN_MSG);
//            }
//
//            return returnXML(RechargeConstant.FAIL_RETURN_CODE, result.getValue());

        } catch(Exception e) {

            logger.error("rechargeResult error: " + e.getMessage(), e);

            return returnXML(RechargeConstant.FAIL_RETURN_CODE, RechargeConstant.FAIL_RETURN_MSG);

        }

    }

    /**
     * 返回微信xml
     * @param returnCode
     * @param returnMsg
     * @return
     */
    private String returnXML(String returnCode, String returnMsg) {

        String returnXML = "<xml><return_code><![CDATA[%s]]></return_code><return_msg><![CDATA[%s]]></return_msg></xml>";

        return String.format(returnXML, returnCode, returnMsg);
    }

    /**
     * 根据订单号获得充值结果
     * @param orderNo 订单号
     * @return
     */
    @RequestMapping(value = "/getRechargeResultByOrderNo", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String getRechargeResultByOrderNo(String orderNo) {

        CommonResult<String> result = rechargeService.getRechargeResultByOrderNo(orderNo);

        return JSONUtil.getReturnBeanString(result);
    }

}
